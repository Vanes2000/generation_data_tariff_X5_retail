package org.example.insertData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SqlExecutor {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public int insert(String sql, Object... params) {
        log.debug("SQL: {}", sql);
        log.debug("Params: {}", Arrays.toString(params));
        return jdbcTemplate.update(sql, params);
    }

    public int insert(String sql, MapSqlParameterSource params) {
        log.debug("SQL: {}", sql);
        log.debug("Params: {}", params.getValues());
        return namedJdbc.update(sql, params);
    }

    public int[] insertBatch(String sql, List<Object[]> batchParams) {
        log.debug("SQL: {}", sql);
        log.debug("Batch size: {}", batchParams.size());
        return jdbcTemplate.batchUpdate(sql, batchParams);
    }
}