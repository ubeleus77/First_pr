package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }




    public void createUsersTable() {
        String sql = "create table IF NOT EXISTS User\n" +
                "(\n" +
                "\tid int auto_increment,\n" +
                "\tname varchar(30) not null,\n" +
                "\tlastname varchar(45) not null,\n" +
                "\tage int not null,\n"  +
                "\tconstraint user_pk\n" +
                "\t\tprimary key (id)\n" +
                ");\n";
        try( Statement st = Util.getConnection().createStatement()){

            st.executeUpdate(sql) ;

            System.err.println("Таблица создана");
        } catch (SQLException e) {
            System.err.println("Таблица с таким именнем уже существует " + e);
        }

    }

    public void dropUsersTable() {
        String sql =  "DROP TABLE IF EXISTS User;";
        try(  PreparedStatement statement = Util.getConnection().prepareStatement(sql) ) {
            statement.executeUpdate();
            System.err.println("Таблица удалена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO User (name, lastname, age) VALUES (?,?,?)";
        try( PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql);) {

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
        String sql = "DELETE FROM User WHERE id= ?";
        try(   PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.err.println("Пользователь под номером "+ id + " удалён");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT id, name, lastname, age FROM User";
        List<User > userList = new ArrayList<>();
        try( PreparedStatement statement = Util.getConnection().prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
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
        try(  Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM User ;");
            System.err.println("Таблица очищена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
