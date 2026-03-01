package org.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "browser")
public class BrowserProperties {
    private Context context = new Context();
    private Launch launch = new Launch();

    @Data
    public static class Context {
        private Viewport viewport = new Viewport();
        private User user = new User();
        private String locale;
        private String timezone;

        @Data
        public static class Viewport {
            private Integer width;
            private Integer height;
        }

        @Data
        public static class User {
            private String agent;
        }
    }

    @Data
    public static class Launch {
        private Boolean headless;
        private List<String> args;
    }
}