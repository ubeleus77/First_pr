package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "create table if not exists  user\n" +
                "(\n" +
                "\tid int auto_increment,\n" +
                "\tname varchar(30) not null,\n" +
                "\tlastname varchar(45) not null,\n" +
                "\tage int not null,\n"  +
                "\tconstraint user_pk\n" +
                "\t\tprimary key (id)\n" +
                ");\n";
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
           session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
           session.createSQLQuery(sql).executeUpdate();
           transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try(Session session = Util.getSessionFactory().openSession())  {

            transaction = session.beginTransaction();
            session.save(new User(name,lastName,age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction  = null;
        String sql = "DELETE FROM User WHERE id = :id1";
        try(Session session = Util.getSessionFactory().openSession()){
           transaction = session.beginTransaction();
            Query query = session.createQuery(sql);
            query.setParameter("id1", id);
            query.executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if(transaction !=null){
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;
        String sql = "From " + User.class.getSimpleName();
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
             users = session.createQuery(sql).list();
            for (Iterator<User> it = users.iterator(); it.hasNext(); ) {
                User user = (User) it.next();
                System.out.println(user.toString());
            }
            transaction.commit();
        }catch (Exception e ){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM User";
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery(sql);
            query.executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }
}
