package org.example.insertData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SqlExecutor {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public int insert(String sql, Object... params) {
        log.debug("SQL: {}", sql);
        log.debug("Params: {}", Arrays.toString(params));
        return jdbcTemplate.update(sql, params);
    }
}
