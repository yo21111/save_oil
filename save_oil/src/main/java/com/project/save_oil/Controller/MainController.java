package com.project.save_oil.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.save_oil.domain.GasStationDto;
import com.project.save_oil.domain.MainSearchDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	
	@GetMapping("/")
	public String main(HttpSession session) {
		// 세션에 저장된 값이 남아있지 않도록 메인페이지에서는 null값 입력
		if(session.getAttribute("MainSearchDto") != null) {
			session.setAttribute("MainSearchDto", null);
		}
		return "mainPage";
	}
	
	@PostMapping("/")
	public String searchPage(HttpSession session, Model m, @ModelAttribute(name = "mainDto") MainSearchDto mainDto) {
		// 세부페이지에서 리스트로 돌아오는 경우 제외
		if(session.getAttribute("MainSearchDto") == null) {
			session.setAttribute("MainSearchDto", mainDto);
			m.addAttribute("mainDto", mainDto);
		}
		
		List<GasStationDto> list = new ArrayList<>();
		
		// 반경에 주유소가 없는 경우도 view에 표기!!!! - search
		
		//1. mainDto 내용을 바탕으로 반경내 주유소 결과 가져오기
		
		//2. 해당 내용을 반복문을 통해 List로 넣을 때 손익계산 로직 실행 후 Dto에 담기
		
		//3. List<MainSearchDto> 를 모델에 담기
		
		
		m.addAttribute("list", list);
		return "search";
	}
	
	@GetMapping("/detail")
	public String resultPage(Model m, String UNI_ID, Double save, HttpSession session) {
		MainSearchDto mainDto = (MainSearchDto)session.getAttribute("MainSearchDto");
		if(mainDto == null) {
			return "redirect:/";
		}
		
		GasStationDto gasDto = null;
		
		// UNI_ID 값을 바탕으로 주유소 세부 정보 가져오기
		// gasDto에 해당 값들 넣어주기
		
		gasDto.setSave(save);
		
		
		m.addAttribute("mainDto", mainDto);
		m.addAttribute("gasDto", gasDto);
		
		return "detail";
	}
}
