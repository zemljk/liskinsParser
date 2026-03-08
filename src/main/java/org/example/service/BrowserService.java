package org.example.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import lombok.RequiredArgsConstructor;
import org.example.model.browser.BrowserContextOptions;
import org.example.model.browser.BrowserLaunchOptions;
import org.example.properties.BrowserProperties;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrowserService {
    private final BrowserProperties browserProperties;

    public BrowserContext createBrowserContext() {
        Playwright playwright = Playwright.create();
        // Создаем настройки для запуска браузера
        BrowserLaunchOptions browserLaunchOptions = new BrowserLaunchOptions(browserProperties);
        BrowserType.LaunchOptions launchOptions = browserLaunchOptions.getLaunchOptions();

        // Запускаем браузер
        Browser browser = playwright.chromium().launch(launchOptions);
        BrowserContextOptions browserContextOptions = new BrowserContextOptions(browserProperties);
        BrowserContext context = browser.newContext(browserContextOptions.getContext());
        return context;
    }
}
