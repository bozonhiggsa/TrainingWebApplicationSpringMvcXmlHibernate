package training.web.application.service;

import training.web.application.model.Admin;

import java.util.List;

public interface AdminService {

    Admin getAdminByLoginAndPassword(String login, String password);

    List<Admin> listAdmins();
}
