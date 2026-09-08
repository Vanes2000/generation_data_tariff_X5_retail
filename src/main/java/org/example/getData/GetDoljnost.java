package org.example.getData;

import org.example.dataBase.DatabaseService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetDoljnost {

    private final DatabaseService db;

    public GetDoljnost( DatabaseService db ) {
        this.db = db;
    }

    private static final String DOLJNOST_IDS_QUERY = "SELECT id FROM `doljnost`" +
            "    WHERE JSON_CONTAINS(direction_json, '2')" +
            "    AND JSON_CONTAINS(type_json, '1');";

    public void getDoljnostIds(){
        List<Integer> ids = db.queryForList ( DOLJNOST_IDS_QUERY );
        if(ids.isEmpty ( )){
            throw new IllegalStateException("Должностей с типом 'Пятёрочка' не найдено");
        }
        System.out.println("Найденные должности с типом 'Пятёрочка': " + ids );
    }

}