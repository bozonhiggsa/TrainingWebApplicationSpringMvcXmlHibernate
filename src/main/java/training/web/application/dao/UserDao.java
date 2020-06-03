package training.web.application.dao;

import training.web.application.model.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    void updateUserAccessById(int id, boolean access);

    void removeUserById(int id);

    User getUserByLoginAndPassword(String login, String password);

    List<User> listUsers();
}
