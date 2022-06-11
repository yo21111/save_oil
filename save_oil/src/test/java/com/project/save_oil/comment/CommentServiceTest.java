package com.project.save_oil.comment;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.save_oil.board.Board;
import com.project.save_oil.board.BoardRepository;
import com.project.save_oil.member.Member;
import com.project.save_oil.member.MemberRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {
	@Autowired
	CommentService cmntService;
	@Autowired
	MemberRepo memRepo;
	@Autowired
	BoardRepository boardRepo;
	@Autowired
	CommentRepository cmntRepo;
	
	@Test
	public void commentTest() {
		List<Member> members = memRepo.findAll();
		List<Board> boards = boardRepo.findAll();
		List<Comment> comments = cmntRepo.findAll();
		
		int beforeSize = comments.size();
		int count = 0;
		
		for (Board b : boards) {
			for(Member m : members) {
				CommentDto cmntDto = new CommentDto();
				cmntDto.setComment(count + "댓글 내용입니다.");
				cmntDto.setMember(m);
				cmntDto.setBoard(b);
				cmntService.save(m.getId(), b.getWNo(), cmntDto);
				count++;
			}
		}
		
		comments = cmntRepo.findAll();
		int afterSize = comments.size();
		
		assertTrue((afterSize - beforeSize) == count);
	}
}
