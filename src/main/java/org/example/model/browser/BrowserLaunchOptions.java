package org.example.model.browser;

import com.microsoft.playwright.BrowserType;
import org.example.properties.BrowserProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class BrowserLaunchOptions {

    private final BrowserProperties properties;

    @Autowired
    public BrowserLaunchOptions(BrowserProperties properties) {
        this.properties = properties;
    }

    public BrowserType.LaunchOptions getLaunchOptions() {
        return new BrowserType.LaunchOptions()
                .setHeadless(properties.getLaunch().getHeadless())
                .setArgs(properties.getLaunch().getArgs());
    }
}
