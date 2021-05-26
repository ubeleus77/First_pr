package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main extends UserServiceImpl {
    public static void main(String[] args) {
        Main user = new Main();

       user.createUsersTable();
       user.saveUser("Оскольд", "Запашный",(byte)69);
       user.saveUser("Людовик 16", "Король",(byte)16);
       user.saveUser("Иван", "Пушкин",(byte)34);
       user.saveUser("Николай", "Мишлен",(byte)56);

       System.out.println(user.getAllUsers());

       user.cleanUsersTable();
       user.dropUsersTable();





    }
}
