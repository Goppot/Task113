package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Super", "Man", (byte) 25);
        userService.saveUser("Bat", "Man", (byte) 30);
        userService.saveUser("Robin", "Good", (byte) 28);
        userService.saveUser("Spider", "Man", (byte) 22);

        for (User u: userService.getAllUsers()){
            System.out.println(u);
        }


//        userService.cleanUsersTable();
//
//        userService.dropUsersTable();
        }
}
