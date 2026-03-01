package org.example.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitUntilState;
import io.github.kihdev.playwright.stealth4j.Stealth4j;
import io.github.kihdev.playwright.stealth4j.Stealth4jConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.browser.BrowserContextOptions;
import org.example.model.browser.BrowserLaunchOptions;
import org.example.properties.BrowserProperties;
import org.example.properties.ParserProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SkinsPages {

    private final BrowserProperties browserProperties;
    private final ParserProperties properties;

    private void parseWithStealth() {
        try (Playwright playwright = Playwright.create()) {

            // Создаем настройки для запуска браузера
            BrowserLaunchOptions browserLaunchOptions = new BrowserLaunchOptions(browserProperties);
            BrowserType.LaunchOptions launchOptions = browserLaunchOptions.getLaunchOptions();

            // Запускаем браузер
            Browser browser = playwright.chromium().launch(launchOptions);
            BrowserContextOptions browserContextOptions = new BrowserContextOptions(browserProperties);
            BrowserContext context = browser.newContext(browserContextOptions.getContext());

            Page.NavigateOptions navigateOptions = new Page.NavigateOptions()
                    .setTimeout(properties.getTimeout())
                    .setWaitUntil(WaitUntilState.NETWORKIDLE);
            // Создаем stealth конфигурацию
            Stealth4jConfig config = Stealth4jConfig.builder()
                    .build();

            // Создаем stealth страницу
            Page page = Stealth4j.newStealthPage(context, config);

            log.info("Navigating to lis-skins.com in headless mode...");

            // Переходим на сайт с таймаутом
            List<String> urls = properties.getUrls();

            goToSite(urls, page, navigateOptions);

            // Ждем загрузки контента
            try {
                page.waitForSelector("body", new Page.WaitForSelectorOptions().setTimeout(10000));
                System.out.println("Page loaded successfully!");
            } catch (TimeoutError e) {
                System.err.println("Warning: Page load timeout");
            }

            // Сохраняем HTML
            saveHtmlWithLocalDateTime(page);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveHtmlWithLocalDateTime(Page page) throws IOException {
        String htmlContent = page.content();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm");
        String formatedDateAndTimeForWindowsTemplate = localDateTime.format(dateTimeFormatter).replaceAll(":", "-");
        String name = "page-content" + formatedDateAndTimeForWindowsTemplate + ".html";
        Files.write(Paths.get("target", name), htmlContent.getBytes());
        System.out.println("HTML content saved to:" + name);
    }

    private void goToSite(List<String> urls, Page page, Page.NavigateOptions navigateOptions) {
        urls.forEach((url) -> page.navigate(url, navigateOptions));
    }

    @Scheduled(fixedRate = 100000)
    private void run() {
        parseWithStealth();
    }

}
