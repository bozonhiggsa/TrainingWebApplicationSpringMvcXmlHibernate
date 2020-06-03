package training.web.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.web.application.dao.AdminDao;
import training.web.application.model.Admin;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao;

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Transactional
    public Admin getAdminByLoginAndPassword(String login, String password) {
        return this.adminDao.getAdminByLoginAndPassword(login, password);
    }

    @Transactional
    public List<Admin> listAdmins() {
        return this.adminDao.listAdmins();
    }

}

