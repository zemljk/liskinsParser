package org.example.datasource;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
public class Skin {
    private Integer id;
    private String name;
    private String exterior;
    private String price;
    private String itemUrl;
    private LocalDateTime lastUpdated;
}
