package training.web.application.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import training.web.application.model.Admin;
import training.web.application.model.User;
import training.web.application.service.AdminService;
import training.web.application.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MatchUserInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value="userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private AdminService adminService;

    @Autowired(required = true)
    @Qualifier(value="adminService")
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        request.setCharacterEncoding("utf-8");

        String login = request.getParameter("login");
        String email = request.getParameter("email");
        boolean match = false;

        List<User> users = userService.listUsers();
        for (User user: users) {
            if(user.getLogin().equals(login) || user.getEmail().equals(email)){
                match = true;
                break;
            }
        }
        List<Admin> admins = adminService.listAdmins();
        for (Admin admin: admins) {
            if(admin.getLogin().equals(login)){
                match = true;
                break;
            }
        }

        if(match){
            request.setAttribute("matchUser", true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/registration.jsp");
            dispatcher.forward(request, response);
            return false;
        }
        else return true;
    }
}
