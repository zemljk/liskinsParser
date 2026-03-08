package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.TimeoutError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.SkinDto;
import org.example.model.skins.ProductJson;
import org.example.properties.ParserProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SkinsPages {
    private final ProductJsonService productJsonService;
    private final BrowserService browserService;
    private final NavigateOptionsService navigateOptionsService;
    private final ParserProperties properties;

    private final ObjectMapper mapper= new ObjectMapper();

    private void parseWithStealth(String url) {
        try (Playwright playwright = Playwright.create()) {
            Page.NavigateOptions navigateOptions = navigateOptionsService.createNavigateOptions();
            StealthPageService stealthPageService = new StealthPageService(browserService);
            Page page = stealthPageService.createStealthPage();

            log.info("Navigating to your URL in headless mode...");

            page.navigate(url, navigateOptions);

            try {
                page.waitForSelector("body", new Page.WaitForSelectorOptions().setTimeout(10000));
                log.info("Page loaded successfully!");
            } catch (TimeoutError e) {
                log.error("Warning: Page load timeout");
            }

            String stringProductJson = productJsonService.findProductJson(page);
            ProductJson productJson = productJsonService.parseProductJson(stringProductJson, mapper);

            ProductJsonToDtoMapper productJsonToDtoMapper = new ProductJsonToDtoMapper();
            SkinDto skinDto = productJsonToDtoMapper.productJsonToDto(productJson);

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Async
    public void parseUrlAsync(String url) {
        parseWithStealth(url);
    }

    @Scheduled(fixedRateString = "${scheduler.interval}")
    public void run() {
        long startTime = System.currentTimeMillis();
        properties.getUrls().forEach(this::parseUrlAsync);
    }
}
