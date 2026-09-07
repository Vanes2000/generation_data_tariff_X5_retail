package org.example;

import org.example.dataBase.CheckDataBaseConnection;
import org.example.getData.GetDoljnost;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DataGeneratorApplication {

    public static void main ( String[] args ) {

        ApplicationContext context = SpringApplication.run(DataGeneratorApplication.class, args);

        CheckDataBaseConnection connection =  context.getBean ( CheckDataBaseConnection.class );

        GetDoljnost getDoljnost = context.getBean ( GetDoljnost.class );

        getDoljnost.getDoljnost ( );

        connection.checkConnection ();

    }
}