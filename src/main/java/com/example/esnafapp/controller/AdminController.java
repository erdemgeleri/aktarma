package com.example.esnafapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.esnafapp.Model.User;
import com.example.esnafapp.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
    private UserService userService;

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("ADMIN")) {
            return "redirect:/auth/login";
        }
        //List<User> users = userService.findAll();  

        //model.addAttribute("users", users);  
        model.addAttribute("message", "Admin Paneli - Ana Sayfa");

        return "dashboard";  
    }
	
    // Kullanıcıları Listeleme
	@GetMapping("/users")
    public String listUsers(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        
        if (role == null || !role.equals("ADMIN")) {
            return "redirect:/auth/login";
        }
        
        model.addAttribute("users", userService.findAllUsers());
        return "admin/user-list";
    }

    
	// Kullanıcı Silme
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
    
 // Kullanıcı Düzenleme Formu
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        System.out.println("Editing user with ID: " + id); // Debug log

        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/admin/users"; // Kullanıcı bulunamazsa listeye yönlendir
        }
        model.addAttribute("user", user); // user nesnesini model'e ekle
        return "admin/user-edit"; // Düzenleme formu sayfası
    }

    
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User updatedUser) {
    	System.out.println("Received User for update: " + updatedUser); // Formdan gelen kullanıcıyı logla
        System.out.println("Updating user with ID: " + updatedUser.getId()); // Debug log
    	if (updatedUser.getId() == null) {
            return "redirect:/admin/users";  // Eğer ID null gelirse, listeye yönlendir.
        }
        User existingUser = userService.findById(updatedUser.getId());

        if (existingUser != null) {
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(updatedUser.getPassword());
            } else {
                existingUser.setPassword(existingUser.getPassword());
            }

            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());

            userService.saveUser(existingUser);
        }

        return "redirect:/admin/users";
    }
    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User()); // Kullanıcı objesini form için model'e ekleyelim
        return "admin/user-add"; // Bu sayfa kullanıcı ekleme formunu içerecek
    }
    @PostMapping("/add")
    public String addUser(@RequestParam String username,
    					  @RequestParam	String email,
    					  @RequestParam String password,
    					  @RequestParam String role,
    					  Model model
    									) {
    	if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Bu kullanıcı adı zaten alınmış.");
            return "register";
        }
    	User user = new User();
    	user.setUsername(username);
    	user.setEmail(email);
    	user.setPassword(password);
    	user.setRole(role);
    	userService.saveUser(user);
    	
    	model.addAttribute("message", "Kullanıcı başarıyla eklendi");
    	return "redirect:/admin/users";
    }
    
}
