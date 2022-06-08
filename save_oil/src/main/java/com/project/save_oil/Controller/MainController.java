package com.project.save_oil.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.save_oil.api.ApiKey;
import com.project.save_oil.domain.GasStationDto;
import com.project.save_oil.domain.MainSearchDto;
import com.project.save_oil.service.MainService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
public class MainController {
	@Autowired
	MainService mainService;
	@Autowired
	private ApiKey API_KEY;
	
	@GetMapping("/")
	public String main(HttpSession session) {
		// 세션에 저장된 값이 남아있지 않도록 메인페이지에서는 null값 입력
		if (session.getAttribute("MainSearchDto") != null) {
			session.setAttribute("MainSearchDto", null);
		}
		return "mainPage";
	}

	@PostMapping("/")
	public String searchPage(HttpSession session, Model m, @ModelAttribute(name = "mainDto") MainSearchDto mainDto) throws Exception {
		String id = (String)session.getAttribute("uId_Session");
		
		// 세부페이지에서 리스트로 돌아오는 경우 제외
		if(session.getAttribute("MainSearchDto") == null) {
			session.setAttribute("MainSearchDto", mainDto);
			m.addAttribute("mainDto", mainDto);
		} else {
			mainDto = (MainSearchDto)session.getAttribute("MainSearchDto");
		}
		
		Integer money = mainDto.getMoney();
		String jsonString = mainService.searchPageProc(mainDto);
		List<GasStationDto> list = mainService.parseToDto(id, money, jsonString);
		
		//3. List<MainSearchDto> 를 모델에 담기
		m.addAttribute("list", list);

		return "search";
	}

	

	@GetMapping("/detail")
	public String resultPage(Model m, Double distance, String UNI_ID, Integer oilPrice, Double save, HttpSession session) throws Exception {
		MainSearchDto mainDto = (MainSearchDto) session.getAttribute("MainSearchDto");
		if (mainDto == null) {
			return "redirect:/";
		}

		// gasDto에 해당 값들 넣어주기
		String jsonString = mainService.resultPageProc(UNI_ID);
		GasStationDto gasDto = mainService.parseToDto(jsonString);
		gasDto.setSave(save);
		gasDto.setOilPrice(oilPrice);
		gasDto.setDistance(distance);

		m.addAttribute("mainDto", mainDto);
		m.addAttribute("gasDto", gasDto);
		m.addAttribute("t_mapAppKey", API_KEY.getTMAP_API());
		return "detail";
	}
	
	
}
