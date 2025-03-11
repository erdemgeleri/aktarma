package com.example.esnafapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title","Ana Sayfa");
		return "index";
	}
	@GetMapping("/hakkinda")
	public String about(Model model) {
		model.addAttribute("title","Hakkında");
		return "hakkinda";
	}
	@GetMapping("/iletisim")
	public String contact(Model model) {
		model.addAttribute("title", "iletisim");
		return "iletisim";
	}
}
