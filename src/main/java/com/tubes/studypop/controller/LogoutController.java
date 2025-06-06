package com.tubes.studypop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout() {
        // Menghapus session atau menggunakan Spring Security untuk logout
        // Mengarahkan pengguna kembali ke halaman login setelah logout
        return "redirect:/login";  // Arahkan kembali ke halaman login
    }
}

