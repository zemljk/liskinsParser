package org.example.service;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import lombok.RequiredArgsConstructor;
import org.example.properties.ParserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateOptionsService {
    @Autowired
    ParserProperties properties;

    public Page.NavigateOptions createNavigateOptions() {
        return new Page.NavigateOptions()
                .setTimeout(properties.getTimeout())
                .setWaitUntil(WaitUntilState.NETWORKIDLE);
    }
}
