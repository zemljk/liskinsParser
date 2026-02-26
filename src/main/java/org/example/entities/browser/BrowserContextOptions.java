package org.example.entities.browser;

import com.microsoft.playwright.Browser;

public class BrowserContextOptions {
    public Browser.NewContextOptions getContext() {
        return new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .setLocale("ru-RU")
                .setTimezoneId("Europe/Moscow");
    }
}
