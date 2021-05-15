package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "jS*Po}{@2";
    private static  String tableName ;

    public static String getTableName() {
        return tableName;
    }

    public void createUsersTable() {

        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); Statement st = connection.createStatement()){

          st.executeUpdate("create table user\n" +
                    "(\n" +
                    "\tid int auto_increment,\n" +
                    "\tname varchar(30) not null,\n" +
                    "\tlastname varchar(45) not null,\n" +
                    "\tage int not null,\n"  +
                    "\tconstraint user_pk\n" +
                    "\t\tprimary key (id)\n" +
                    ");\n") ;

                System.err.println("Таблица создана");
            } catch (SQLException e) {
            System.err.println("Таблица с таким именнем уже существует " + e);
        }

    }

    public void dropUsersTable() {
        String sql =  "DROP TABLE user;";
        try( Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql) ) {
            statement.executeUpdate();
            System.err.println("Таблица удалена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user (name, lastname, age) VALUES (?,?,?)";
        try( Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();



            System.out.println("Юзер с именем - " + name +" " +  lastName + " добавлен в базу данных");
        } catch (SQLException throwables) {
            System.err.println("  Такой юзер уже существует " + throwables);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id= ?";
        try( Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);  PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.err.println("Пользователь под номером "+ id + " удалён");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT id, name, lastname, age FROM user";
      List<User > userList = new ArrayList<>();
      try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
          while(resultSet.next()){

              String name = resultSet.getString(2);
              String lastName = resultSet.getString(3);
              byte age = (byte) resultSet.getInt(4);
              User user = new User(name,lastName,age);
              userList.add(user);
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }


        return userList;
    }

    public void cleanUsersTable() {
        try( Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM user;");
            System.err.println("Таблица очищена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
