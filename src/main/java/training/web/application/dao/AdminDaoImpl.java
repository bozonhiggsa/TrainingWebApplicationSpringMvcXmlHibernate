package training.web.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import training.web.application.model.Admin;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AdminDaoImpl implements AdminDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Admin getAdminByLoginAndPassword(String login, String password) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery(String.format("from Admin A where A.login='%s' and A.password='%s'", login, password));
        try{
            Object singleResult = query.getSingleResult();
            return (Admin) singleResult;
        } catch(NoResultException e){
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Admin> listAdmins() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Admin> adminsList = session.createQuery("from Admin").list();

        return adminsList;
    }
}
