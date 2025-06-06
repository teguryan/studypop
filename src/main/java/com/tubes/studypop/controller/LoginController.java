package com.tubes.studypop.controller;

import com.tubes.studypop.model.User;
import com.tubes.studypop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // Menampilkan halaman login
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        // Menggunakan AuthService untuk memverifikasi login
        Optional<User> userOpt = authService.login(username, password);

        // Memeriksa apakah user ditemukan dan login berhasil
        if (userOpt.isPresent()) {
            User user = userOpt.get();  // Mengambil user dari Optional jika ada
            model.addAttribute("user", user);

            // Berdasarkan role, arahkan ke tampilan yang sesuai
            if ("admin".equalsIgnoreCase(user.getRole())) {
                // Jika role admin, tampilkan tampilan admin
                return "admin_dashboard";  // Ganti dengan halaman dashboard admin
            } else if ("student".equalsIgnoreCase(user.getRole())) {
                // Jika role student, tampilkan tampilan student
                return "student_dashboard";  // Ganti dengan halaman dashboard student
            } else {
                // Default: Jika role tidak sesuai, kembali ke login dengan error
                model.addAttribute("error", "Invalid role.");
                return "login";
            }
        } else {
            // Jika login gagal, tampilkan pesan error
            model.addAttribute("error", "Invalid credentials, please try again.");
            return "login";  // Kembali ke halaman login
        }
    }

}
