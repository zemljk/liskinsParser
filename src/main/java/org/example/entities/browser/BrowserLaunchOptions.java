package org.example.entities.browser;

import com.microsoft.playwright.BrowserType;

public class BrowserLaunchOptions {
    public BrowserType.LaunchOptions getLaunchOptions() {
        return new BrowserType.LaunchOptions()
                .setHeadless(true)  // true для скриншота без открытия окна
                .setArgs(java.util.List.of(
                        "--disable-blink-features=AutomationControlled",
                        "--disable-automation",
                        "--no-sandbox"
                ));
    }
}
