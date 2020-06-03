package training.web.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import training.web.application.model.Admin;
import training.web.application.model.User;
import training.web.application.service.AdminService;
import training.web.application.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller for requests handling
 * @author Ihor Savchenko
 * @version 1.0
 */
@SessionAttributes("session_attribute")
@Controller
public class MvcController {

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

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession httpSession;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/index";
    }

    @PostMapping("/login")
    public String verifyLogin(@RequestParam(value = "login") String login,
                                          @RequestParam(value = "password") String password) {
        User user = null;
        Admin admin = null;

        user = userService.getUserByLoginAndPassword(login, password);

        if(user != null){
            httpSession.setAttribute("user", user);
            return "redirect:/enter";
        }
        else {
            admin = adminService.getAdminByLoginAndPassword(login, password);
            if (admin != null) {
                httpSession.setAttribute("admin", admin);
                return "redirect:/enterAdmin";
            } else {
                request.setAttribute("incorrectLogin", true);
                return "login";
            }
        }
    }

    @GetMapping("/enter")
    public String enterUser() {
        if(httpSession != null && httpSession.getAttribute("user") != null){
            return "PersonalCabinet";
        } else{
            return "accessError";
        }
    }

    @GetMapping("/enterAdmin")
    public String enterAdmin() {
        if(httpSession != null && httpSession.getAttribute("admin") != null){
            List<User> users= userService.listUsers();
            if(users != null) httpSession.setAttribute("allUsers", users);
            return "AdminCabinet";
        } else{
            return "accessError";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        if(httpSession != null && httpSession.getAttribute("user") != null){
            httpSession.setAttribute("user", null);
            httpSession.invalidate();
        } else if(httpSession != null && httpSession.getAttribute("admin") != null){
            httpSession.setAttribute("admin", null);
            httpSession.invalidate();
        }
        return "index";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestParam(value = "login") String login,
                              @RequestParam(value = "password") String password,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "lastname") String lastname,
                              @RequestParam(value = "email") String email) {

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setAccess(true);
        userService.addUser(user);

        return "redirect:/afterCreate";
    }

    @GetMapping("/afterCreate")
    public String afterCreateUser() {
        return "index";
    }

    @GetMapping("/blockUser")
    public String blockUser(@RequestParam(value = "access") boolean access,
                            @RequestParam(value = "id_user") int id_user) {

        if(httpSession != null && httpSession.getAttribute("admin") != null){
            userService.updateUserAccessById(id_user, access);
            return "forward:/enterAdmin";
        } else{
            return "accessError";
        }
    }
}
