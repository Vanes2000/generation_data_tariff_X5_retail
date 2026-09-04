package org.example.connectionDataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CheckDataBaseConnection {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void CheckConnectionDataBase() {
        jdbcTemplate.queryForList("select id from object");
    }

}
