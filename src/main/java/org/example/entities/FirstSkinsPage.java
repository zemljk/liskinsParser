package org.example.entities;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.WaitUntilState;
import io.github.kihdev.playwright.stealth4j.Stealth4j;
import io.github.kihdev.playwright.stealth4j.Stealth4jConfig;
import org.example.entities.browser.BrowserContextOptions;
import org.example.entities.browser.BrowserLaunchOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class FirstSkinsPage {

    private static final Random random = new Random();

    public void parseWithStealth() {
        try (Playwright playwright = Playwright.create()) {

            // Создаем настройки для запуска браузера
            BrowserLaunchOptions browserLaunchOptions = new BrowserLaunchOptions();
            BrowserType.LaunchOptions launchOptions= browserLaunchOptions.getLaunchOptions();

            // Запускаем браузер
            Browser browser = playwright.chromium().launch(launchOptions);
            BrowserContextOptions browserContextOptions = new BrowserContextOptions();
            BrowserContext context = browser.newContext(browserContextOptions.getContext());

            // Создаем stealth конфигурацию
            Stealth4jConfig config = Stealth4jConfig.builder()
                    .build();

            // Создаем stealth страницу
            Page page = Stealth4j.newStealthPage(context, config);

            // эмулируем действия пользователя
            emulateHumanAction(page, launchOptions);

            System.out.println("Navigating to lis-skins.com in headless mode...");

            // Переходим на сайт с таймаутом
            page.navigate("https://lis-skins.com/ru/market/cs2/",
                    new Page.NavigateOptions()
                            .setTimeout(80000)
                            .setWaitUntil(WaitUntilState.NETWORKIDLE));

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

    private void emulateHumanAction(Page page, BrowserType.LaunchOptions launchOptions) throws InterruptedException {
        if (!launchOptions.headless) {
            page.mouse().move(random.nextInt(500), random.nextInt(500));
            Thread.sleep(random.nextInt(1000) + 500);
            page.evaluate("window.scrollTo(0, 200)");
            Thread.sleep(random.nextInt(1000) + 500);
        }
    }
    private void saveHtmlWithLocalDateTime(Page page) throws IOException {
        String htmlContent = page.content();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm");
        String formatedDateAndTimeForWindowsTemplate = localDateTime.format(dateTimeFormatter).replaceAll(":","-");
        Files.write(Paths.get("page-content"+formatedDateAndTimeForWindowsTemplate+".html"), htmlContent.getBytes());
        System.out.println("HTML content saved to: page-content.html");
    }
}
