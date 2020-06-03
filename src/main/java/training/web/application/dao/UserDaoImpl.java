package training.web.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import training.web.application.model.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(user);
    }

    public void updateUserAccessById(int id, boolean access) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = session.load(User.class, new Integer(id));
        user.setAccess(access);
        session.update(user);
    }

    public void removeUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = session.load(User.class, new Integer(id));
        if(user != null){
            session.delete(user);
        }
    }

    public User getUserByLoginAndPassword(String login, String password) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(String.format("from User U where U.login='%s' and U.password='%s'", login, password));
        try{
            Object singleResult = query.getSingleResult();
            return (User) singleResult;
        } catch(NoResultException e){
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> usersList = session.createQuery("from User").list();

        return usersList;
    }
}
