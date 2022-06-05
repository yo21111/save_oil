package com.project.save_oil.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/so")
public class MainController {
	
	@GetMapping("/")
	public String main() {
		return "mainPage";
	}
}
