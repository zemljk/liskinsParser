package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.TimeoutError;
import io.github.kihdev.playwright.stealth4j.Stealth4j;
import io.github.kihdev.playwright.stealth4j.Stealth4jConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.SkinDto;
import org.example.exceptions.JsonLdNotFoundException;
import org.example.model.skins.ProductJsonLd;
import org.example.properties.BrowserProperties;
import org.example.properties.ParserProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SkinsPages {
    private final BrowserService browserService;
    private final NavigateOptionsService navigateOptionsService;
    private final BrowserProperties browserProperties;
    private final ParserProperties properties;

    private void parseWithStealth(String url) {
        try (Playwright playwright = Playwright.create()) {
            BrowserContext context = browserService.createBrowserContext();
            Page.NavigateOptions navigateOptions = navigateOptionsService.createNavigateOptions();

            // Создаем stealth конфигурацию
            Stealth4jConfig config = Stealth4jConfig.builder()
                    .build();

            // Создаем stealth страницу
            Page page = Stealth4j.newStealthPage(context, config);

            log.info("Navigating to your URL in headless mode...");

            page.navigate(url, navigateOptions);

            try {
                page.waitForSelector("body", new Page.WaitForSelectorOptions().setTimeout(10000));
                log.info("Page loaded successfully!");
            } catch (TimeoutError e) {
                log.error("Warning: Page load timeout");
            }

            ProductJsonLd productJsonLd = extractAndParseJsonTyped(page);
            SkinDto skinDto = productJsonLdToDto(productJsonLd);

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private SkinDto productJsonLdToDto(ProductJsonLd productJsonLd) {
        return SkinDto.builder()
                .name(productJsonLd.getName())
                .image(productJsonLd.getImage())
                .url(productJsonLd.getOffers().getUrl())
                .priceCurrency(productJsonLd.getOffers().getPriceCurrency())
                .lowPrice(productJsonLd.getOffers().getLowPrice())
                .highPrice(productJsonLd.getOffers().getHighPrice())
                .build();
    }

    private ProductJsonLd extractAndParseJsonTyped(Page page) {
        try {
            var jsonScripts = page.locator("script[type='application/ld+json']").all();
            ObjectMapper mapper = new ObjectMapper();

            for (var script : jsonScripts) {
                String jsonText = script.textContent();
                if (jsonText == null || jsonText.isBlank())
                    continue;

                return mapper.readValue(jsonText, ProductJsonLd.class);
            }
        } catch (Exception e) {
            log.error("Error parsing JSON-LD: {}", e.getMessage(), e);
        }
        throw new JsonLdNotFoundException("Product JSON-LD not found on page: " + page.url());
    }

    @Async
    public void parseUrlAsync(String url) {
        parseWithStealth(url);
    }

    @Scheduled(fixedRate = 100000)
    public void run() {
        properties.getUrls().forEach(this::parseUrlAsync);
    }
}
