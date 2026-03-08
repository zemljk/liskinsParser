package org.example.service;

import org.example.dto.SkinDto;
import org.example.model.skins.ProductJson;

public class ProductJsonToDtoMapper {
    public SkinDto productJsonToDto(ProductJson productJsonLd) {
        return SkinDto.builder()
                .name(productJsonLd.getName())
                .image(productJsonLd.getImage())
                .url(productJsonLd.getOffers().getUrl())
                .priceCurrency(productJsonLd.getOffers().getPriceCurrency())
                .lowPrice(productJsonLd.getOffers().getLowPrice())
                .highPrice(productJsonLd.getOffers().getHighPrice())
                .build();
    }
}
