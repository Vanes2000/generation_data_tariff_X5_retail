package org.example.getData;

import org.springframework.stereotype.Component;

@Component
public class GetDoljnost {


    //в этом классе мне нужно будет использовать конструктор, чтобы отправить sql запрос

    private static final String getDoljsnot = "SELECT id FROM `doljnost`" +
            "    WHERE JSON_CONTAINS(direction_json, '2')" +
            "    AND JSON_CONTAINS(type_json, '1');";

    public void getDoljnost(){
    }

}