package org.example.datasource;


//
//@Component
//public class DataSourceProvider {
//    private static HikariDataSource dataSource;
//
//    private final DataBaseProperties properties;
//
//    @Autowired
//    public DataSourceProvider(DataBaseProperties dataSourceProperties) {
//        this.properties = dataSourceProperties;
//    }
//
//    private void initDataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(properties.getUrl());
//        config.setUsername(properties.getUsername());
//        config.setPassword(properties.getPassword());
//        config.setMaximumPoolSize(20);
//        config.setMinimumIdle(5);
//        config.setConnectionTimeout(30000);
//        config.setIdleTimeout(600000);
//        config.setMaxLifetime(1800000);
//        config.setDataSourceClassName(properties.getDriverClassName());
//        dataSource = new HikariDataSource(config);
//    }
//
//    public static DataSource getDataSource() {
//        return dataSource;
//    }
//}