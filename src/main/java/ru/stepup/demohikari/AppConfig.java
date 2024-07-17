package ru.stepup.demohikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class AppConfig {
    @Bean("DataSource")
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        HikariDataSource dataSource;

        config.setJdbcUrl("jdbc:postgresql://localhost:5432/hikari");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean("JdbcConnection")
    @DependsOn({"DataSource"})
    public Connection jdbcConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
