package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.JsonLdNotFoundException;
import org.example.model.skins.ProductJson;
import org.example.properties.ProductJsonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductJsonService {

    @Autowired
    ProductJsonProperties productJsonProperties;

    public String findProductJson(Page page) {
        var jsonScripts = page.locator(productJsonProperties.getSelector()).all();

        for (var script : jsonScripts) {
            String jsonText = script.textContent();
            if (jsonText != null && !jsonText.isBlank()) {
                return jsonText;
            }
        }
        throw new JsonLdNotFoundException("Product JSON not found on page: " + page.url());
    }

    public ProductJson parseProductJson(String jsonText, ObjectMapper mapper) {
        try {
            return mapper.readValue(jsonText, ProductJson.class);
        } catch (Exception e) {
            throw new JsonLdNotFoundException("Failed to parse Product JSON on page");
        }
    }
}
