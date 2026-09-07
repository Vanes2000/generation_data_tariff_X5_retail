package org.example.dataBase;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseService ( JdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //метод для получения id должности
    public List<Integer> queryForList(String sql, Object... params) {
        return jdbcTemplate.queryForList (sql, Integer.class, params);
    }

    // метод для создания тарифа в таблице
    public int insert(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    //метод для удаления (пусть будет)

}
