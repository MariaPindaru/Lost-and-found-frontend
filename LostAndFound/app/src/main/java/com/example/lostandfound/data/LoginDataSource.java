package com.example.lostandfound.data;

import com.example.lostandfound.data.model.LoggedInUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import database.SQLConnection;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    Connection connection;
    String res = "";

    public Result<LoggedInUser> login(String username, String password) {

        try {
            SQLConnection sqlConnection = new SQLConnection();
            connection = sqlConnection.connect();
            System.out.println(connection.toString());
            if (connection != null) {
                String query = "select * from [user]";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    if (resultSet.getString(2).equals(username) && resultSet.getString(3).equals(password)){
                        LoggedInUser fakeUser =
                                new LoggedInUser(
                                        resultSet.getString(1),
                                        username);
                        return new Result.Success<>(fakeUser);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return new Result.Error(new IOException("Error logging in", e));
        }

        return new Result.Error(new IOException("Invalid username or password"));
    }

    public void logout() {
        // TODO: revoke authentication
    }
}