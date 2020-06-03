package training.web.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.web.application.dao.UserDao;
import training.web.application.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void addUser(User user) {
        this.userDao.addUser(user);
    }

    @Transactional
    public void updateUserAccessById(int id, boolean access) {
        this.userDao.updateUserAccessById(id, access);
    }

    @Transactional
    public void removeUserById(int id) {
        this.userDao.removeUserById(id);
    }

    @Transactional
    public User getUserByLoginAndPassword(String login, String password) {
        return this.userDao.getUserByLoginAndPassword(login, password);
    }

    @Transactional
    public List<User> listUsers() {
        return this.userDao.listUsers();
    }

}
