package com.project.save_oil.Controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.save_oil.domain.MemberDto;
import com.project.save_oil.member.Member;
import com.project.save_oil.member.MemberService;
import com.project.save_oil.validation.MemberValidator;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class LoginController {
	private final MemberService memberService;
	private final MemberValidator memberValidator;
	
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
				return "redirect:/";
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
	public String register(Model m) {
		m.addAttribute("member", new MemberDto());
		return "register";
	}

	@PostMapping("/register")
	public String insertMember(@ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult) {
		memberValidator.validate(memberDto, bindingResult);
		if (bindingResult.hasErrors()) {
			return "register";
		}
		memberService.save(memberDto);
		return "redirect:/login";
	}
	
	@GetMapping("/update")
	public String update() {
		return "update";
	}
	
	@PostMapping("/update")
	public String updateMember(HttpSession session, MemberDto memberDto) {
		String id = (String)session.getAttribute("uId_Session");
		Member member = Member.builder().password(memberDto.getPassword())
				.carName(memberDto.getCarName())
				.company(memberDto.getCompany())
				.oilType(memberDto.getOilType())
				.fuelEffi(memberDto.getFuelEffi()).build();
		try {
			memberService.update(id, member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
}
