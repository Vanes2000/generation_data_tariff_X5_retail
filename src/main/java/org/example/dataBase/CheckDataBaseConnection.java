package org.example.dataBase;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CheckDataBaseConnection {

    private final JdbcTemplate jdbcTemplate;

    public void checkConnection() {
        jdbcTemplate.queryForList("SELECT 1");
    }

    public CheckDataBaseConnection ( JdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
