package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;


public class Main  {
    public static void main(String[] args) {

       UserService user = new UserServiceImpl();

       user.createUsersTable();
       user.saveUser("Оскольд", "Запашный",(byte)69);
       user.saveUser("Людовик 16", "Король",(byte)16);
       user.saveUser("Иван", "Пушкин",(byte)34);
       user.saveUser("Николай", "Мишлен",(byte)56);
       System.out.println(user.getAllUsers());

       user.cleanUsersTable();
       user.dropUsersTable();

       try {
          Util.getConnection().close();
       } catch (SQLException ex) {
          ex.printStackTrace();
       }
    }
}
