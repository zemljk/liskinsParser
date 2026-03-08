package org.example.model.skins;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductJson {
    private String name;
    private String image;
    @JsonProperty("offers")
    private Offers offers;
}

