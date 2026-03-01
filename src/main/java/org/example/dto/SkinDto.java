package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkinDto {
    private String name;
    private String image;
    private String url;
    private String priceCurrency;
    private String lowPrice;
    private String highPrice;
}
