package com.project.save_oil.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.save_oil.board.Board;
import com.project.save_oil.board.BoardDto;
import com.project.save_oil.board.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;

	//	1. 전체 게시글 리스트를 출력하는 메서드 : /list
	@GetMapping("/list/{search}")
	public String getBoardList(@PathVariable String search, Model m) {
		List<Board> list = boardService.getBoardList(search);
		m.addAttribute("boardList", list);
		return "";
	}
	
	//	2. 게시글 페이지를 출력하는 메서드 : /read
	@GetMapping("/read/{wNo}")
	public String getBoardPage(@PathVariable Integer wNo, Model m) throws Exception {
		Board board = boardService.getBoard(wNo);
		m.addAttribute("board", board);
		// 게시글 페이지는 수정, 작성이 불가능 하도록
		m.addAttribute("readonly", "readonly");
		return "";
	}
	
	//	3. 게시글을 작성 페이지를 출력하는 메서드 : /write
	@GetMapping("/write")
	public String writeBoardPage(Model m) {
		m.addAttribute("board", new BoardDto());
		return "";
	}
	
	//	4. 작성 내용을 서버로 전송하는 메서드 : /write
	@PostMapping("/write")
	public String writeBoard(@SessionAttribute("uId_Session") String id, @ModelAttribute("board") BoardDto boardDto, Model m) throws Exception {
		// 제목에 대한 유효성 검사 -> 다시 /write 로 돌아가기
		
		
		Board board = boardService.writeBoard(id, boardDto);
		return "redirect:/board/read/"+board.getWNo();
	}
	
	//	5. 게시글 수정 페이지를 출력하는 메서드 : /rewrite
	@GetMapping("/rewrite/{wNo}")
	public String reWriteBoardPage(@PathVariable Integer wNo, Model m) throws Exception {
		Board board = boardService.getBoard(wNo);
		m.addAttribute("board", board);
		return "";
	}
	//	6. 수정 내용을 서버로 전송하는 메서드 : /rewrite
	@PostMapping("/rewrite/{wNo}")
	public String reWriteBoard(@PathVariable Integer wNo, BoardDto boardDto) throws Exception {
		// 제목에 대한 유효성 검사 -> 다시 /rewrite 로 돌아가기
		
		boardService.reWriteBoard(boardDto);
		return "redirect:/board/read/"+wNo;
	}
	
	//	7. 게시글을 삭제하는 메서드 : /delete
	@GetMapping("/delete/{wNo}")
	public String deleteBoard(@PathVariable Integer wNo) {
		boardService.deleteBoard(wNo);
		return "redirect:/board/list/";
	}
}
