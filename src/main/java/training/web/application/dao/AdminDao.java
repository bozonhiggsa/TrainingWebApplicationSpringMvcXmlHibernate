package training.web.application.dao;

import training.web.application.model.Admin;

import java.util.List;

public interface AdminDao {

    Admin getAdminByLoginAndPassword(String login, String password);

    List<Admin> listAdmins();
}
