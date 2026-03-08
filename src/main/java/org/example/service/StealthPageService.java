package org.example.service;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import io.github.kihdev.playwright.stealth4j.Stealth4j;
import io.github.kihdev.playwright.stealth4j.Stealth4jConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StealthPageService {

    private final BrowserService browserService;

    public Page createStealthPage() {
        BrowserContext context = browserService.createBrowserContext();
        Stealth4jConfig config = Stealth4jConfig.builder()
                .build();
        return Stealth4j.newStealthPage(context, config);
    }
}
