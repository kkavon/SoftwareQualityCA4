package net.codejava;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
    @GetMapping("/homepage")
    public String showHomePage() {
        return "homepage";
    }
    
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user_admin";
    }

    @GetMapping("/customer/dashboard")
    public String showCustomerDashboard() {
        return "homepage";
    }
    
    @GetMapping("/register_success")
    public String showRegistrationSuccessPage() {
        return "register_success";
    }
    
  
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            if (user.getRole().equals("admin")) {
                // Logged-in user is an admin, redirect to the admin dashboard
                return "redirect:/admin/dashboard";
            } else {
                // Logged-in user is a customer, redirect to the customer dashboard
                return "redirect:/customer/dashboard";
            }
        } else {
            // Login failed, redirect back to the login page with an error message
            return "redirect:/login?error=true";
        }
    }
    
    @GetMapping("/users/display")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users_display";
    }
    

    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users_create";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute User user) {
    	userRepository.save(user);
        return "redirect:/register_success";
    }

    // Other controller methods for handling customer requests
}