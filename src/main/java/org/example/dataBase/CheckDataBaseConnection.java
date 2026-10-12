package org.example.dataBase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import java.sql.*;


@Slf4j
@Component
public class CheckDataBaseConnection {

    private final DatabaseService db;

    private static final String CHECK_QUERY = "SELECT 1";

    public CheckDataBaseConnection ( DatabaseService db ) {
        this.db = db;
    }

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public boolean checkConnection() {
        try {
            db.queryForList(CHECK_QUERY);
            System.out.println ("Подключение к БД успешно установлено");
            return true;
        } catch (DataAccessException e) {
            System.out.println ("Ошибка подключения к БД: " + e.getMessage());
        return false;
        }
    }

    public void validateConnection() {
        try(Connection connect = DriverManager.getConnection ( url,username,password )){

            int timeoutInSeconds = 5;

            if (connect.isValid ( timeoutInSeconds ))
           {
               System.out.println ("Подключение установлено");
           }
            else {
                System.out.println ("Подключение не установлено" );
            }
        } catch (SQLException e) {
            throw new RuntimeException ( "Подключение не установлено: " + e.getMessage() );
        }

    }

}