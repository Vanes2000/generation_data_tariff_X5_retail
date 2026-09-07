package org.example.getData;

import org.example.dataBase.DatabaseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetDoljnost {

    private final DatabaseService db;

    public GetDoljnost( DatabaseService db ) {
        this.db = db;
    }

    private static final String getDoljsnot = "SELECT id FROM `doljnost`" +
            "    WHERE JSON_CONTAINS(direction_json, '2')" +
            "    AND JSON_CONTAINS(type_json, '1');";

    public void getDoljnost(){
        List<Integer> ids = db.queryForList ( getDoljsnot );
        System.out.println("Найденные ID: " + ids);
    }

}