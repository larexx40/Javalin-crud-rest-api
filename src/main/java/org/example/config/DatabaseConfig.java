package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;

import javax.sql.DataSource;

public class DatabaseConfig {
    private static HikariDataSource dataSource;
    private static FluentJdbc fluentJdbc;

    static {
        // Load environment variables
        Dotenv dotenv = Dotenv.load();

        // Configure HikariCP
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dotenv.get("DB_URL"));
        config.setUsername(dotenv.get("DB_USER"));
        config.setPassword(dotenv.get("DB_PASSWORD"));
        config.setMaximumPoolSize(Integer.parseInt(dotenv.get("DB_POOL_SIZE")));

        dataSource = new HikariDataSource(config);

        // Initialize FluentJdbc with the DataSource
        fluentJdbc = new FluentJdbcBuilder().connectionProvider(dataSource).build();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static FluentJdbc getFluentJdbc() {
        return fluentJdbc;
    }
}
