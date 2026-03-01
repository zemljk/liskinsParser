package org.example.model.skins;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductJsonLd {
    private String name;
    private String image;
    @JsonProperty("offers")
    private Offers offers;
}

