package com.project.save_oil.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.save_oil.domain.MemberDto;
import com.project.save_oil.member.Member;
import com.project.save_oil.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {
	private final MemberService memberService;

	@GetMapping("/login")
	public String login() {
		return "index";
	}

	@PostMapping("/login")
	public String loginProc(MemberDto memberDto, HttpSession session) throws Exception {
		String errorMsg = URLEncoder.encode("아이디 또는 비밀번호를 확인해주세요.", "UTF-8");
		try {
			Member mem = memberService.findById(memberDto.getId());
			
			if (mem.getPassword().equals(memberDto.getPassword())) {
				session.setAttribute("uId_Session", mem.getId());
				return "mainPage";
			} else {
				return "redirect:/login?errorMsg="+errorMsg;
			}
		} catch (Exception e) {
			return "redirect:/login?errorMsg="+errorMsg;
		}

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String insertMember(MemberDto memberDto) {
		memberService.save(memberDto);
		return "redirect:/login";
	}
}
