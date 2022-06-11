package com.project.save_oil.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.save_oil.comment.Comment;
import com.project.save_oil.comment.CommentDto;
import com.project.save_oil.comment.CommentRepository;
import com.project.save_oil.comment.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentCotroller {
	private final CommentService cmntService;
	private final CommentRepository cmntRepo;

	@PostMapping("/write")
	public String writeComment(@SessionAttribute("uId_Session")String id, Integer wNo, CommentDto cmntDto) {
		cmntService.save(id, wNo, cmntDto);
		return "redirect:/board/read/"+wNo;
	}
	
	@GetMapping("/delete")
	public String deleteComment(Integer cNo) {
		Comment cmnt = cmntRepo.findById(cNo).orElseThrow(IllegalArgumentException::new);
		cmntRepo.deleteById(cNo);
		return "redirect:/board/read/"+cmnt.getBoard().getWNo();
	}
}
