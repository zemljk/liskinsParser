package org.example.model.skins;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Offers {
    private String url;
    private String priceCurrency;
    private String lowPrice;
    private String highPrice;
}
