package com.example.crud_spring_security_fetch_3_1_4.config;

import com.example.crud_spring_security_fetch_3_1_4.service.RoleService;
import com.example.crud_spring_security_fetch_3_1_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class AdminUserCreationMaster {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminUserCreationMaster(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUsers(){
        System.out.println("-----------------------------CREATING ADMIN USER----------------------------");
//        User admin = new User();
//        Role adminRole = new Role();
//        admin.setId(1L);
//        admin.setUsername("admin");
//        admin.setPassword("1234");
//        adminRole.setId(1L);
//        adminRole.setName("ROLE_ADMIN");
//        admin.setRoles(Collections.singleton(adminRole));
//        adminRole.setUsers(Collections.singleton(admin));
//        userService.save(admin,Collections.singleton(adminRole));

        try (Statement statement = connect().createStatement()) {
            statement.executeUpdate("INSERT INTO mysql.users (username, password)\n" +
                    "VALUES ('admin', '$2a$12$mZFk.U6XZSRzWkMSLhRN4e0um2BUi2KOTZTWsoN4oTYXsFjFq.geG');");
            statement.executeUpdate("INSERT INTO mysql.roles (id, name)\n" +
                    "VALUES (1, 'ROLE_ADMIN');");
            statement.executeUpdate("INSERT INTO mysql.roles (id, name)\n" +
                    "VALUES (2, 'ROLE_USER');");
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
