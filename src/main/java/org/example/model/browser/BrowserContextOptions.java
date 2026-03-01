package org.example.model.browser;

import com.microsoft.playwright.Browser;
import org.example.properties.BrowserProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class BrowserContextOptions {
    private final BrowserProperties properties;

    @Autowired
    public BrowserContextOptions(BrowserProperties properties) {
        this.properties = properties;
    }

    public Browser.NewContextOptions getContext() {
        return new Browser.NewContextOptions()
                .setViewportSize(properties.getContext().getViewport().getWidth(), properties.getContext().getViewport().getHeight())
                .setUserAgent(properties.getContext().getUser().getAgent())
                .setLocale(properties.getContext().getLocale())
                .setTimezoneId(properties.getContext().getTimezone());
    }
}
