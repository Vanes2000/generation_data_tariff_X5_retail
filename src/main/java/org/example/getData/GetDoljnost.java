package org.example.getData;

import org.example.dataBase.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetDoljnost {

    private static final String DOLJNOST_IDS_QUERY = "SELECT id FROM `doljnost`" +
            "    WHERE JSON_CONTAINS(direction_json, '2')" +
            "    AND JSON_CONTAINS(type_json, '1');";


    private List<Integer> doljnostIds;
    private final DatabaseService db;

    public GetDoljnost(DatabaseService db) {
        this.db = db;
    }

    public List<Integer> getDoljnostIds() {
        if (doljnostIds == null) {
            doljnostIds = loadDoljnostIds();
        }
        return doljnostIds;
    }

    public List<Integer> loadDoljnostIds ( ) {
        List<Integer> ids = db.queryForList(DOLJNOST_IDS_QUERY);
        if (ids.isEmpty()) {
            throw new IllegalStateException(
                    "Должностей с типом 'Пятёрочка' не найдено");
        }
        return List.copyOf(ids); // immutable
    }
}