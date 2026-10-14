package org.example.tariff;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DoljnostProvider {

    private static final String SQL = """
            SELECT id FROM doljnost
            WHERE JSON_CONTAINS(direction_json, '2')
              AND JSON_CONTAINS(type_json, '1')
            """;

    private final NamedParameterJdbcTemplate jdbc;

    public List<Integer> loadDoljnostIds() {
        List<Integer> ids = jdbc.getJdbcTemplate()
                .queryForList(SQL, Integer.class);

        if (ids.isEmpty()) {
            throw new IllegalStateException(
                    "Должностей с типом 'Пятёрочка' не найдено");
        }
        return List.copyOf(ids);
    }
}
