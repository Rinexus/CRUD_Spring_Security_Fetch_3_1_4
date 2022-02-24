package com.example.crud_spring_security_3_1_2.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class AdminUserCreationMaster {

    @PostConstruct
    public void createUsers(){
        System.out.println("-----------------------------CREATING ADMIN USER----------------------------");
        try (Statement statement = connect().createStatement()) {
            statement.executeUpdate("INSERT INTO mysql.users (username, password)\n" +
                    "VALUES ('admin', '$2a$12$mZFk.U6XZSRzWkMSLhRN4e0um2BUi2KOTZTWsoN4oTYXsFjFq.geG');");
            statement.executeUpdate("INSERT INTO mysql.roles (id, name)\n" +
                    "VALUES (1, 'ROLE_ADMIN');");
            statement.executeUpdate("INSERT INTO mysql.users_roles (users_id, roles_id)\n" +
                    "VALUES (1, 1);");
            System.out.println("-----------------------------ADMIN USER CREATED-----------------------------");
            System.out.println("-----------------------------username:admin---------------------------------");
            System.out.println("-----------------------------password:1234----------------------------------");
        } catch (SQLException e) {
            System.out.println("При создании пользователя возникла ошибка");
            e.printStackTrace();
        }
    }

    public static Connection connect() {
        String userName = "root";
        String password = "1234";
        String connectionUrl = "jdbc:mysql://localhost:3306/mysql";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection =  DriverManager.getConnection(connectionUrl, userName, password);
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
