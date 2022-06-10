package com.project.save_oil.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.save_oil.domain.MemberDto;
import com.project.save_oil.member.Member;
import com.project.save_oil.member.MemberRepo;
import com.project.save_oil.member.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {
	@Autowired
	private BoardService boardService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private MemberRepo memberRepo;
	
	@Before
	public void createTest() throws Exception {
		MemberDto member = new MemberDto();
		BoardDto boardDto = new BoardDto();
		
		if(memberRepo.findAll().size() < 10)
		for(int i = 1; i <= 10; i++) {
			member.setId("tester" + i);
			member.setPassword("1234"+i);
			memberService.save(member);
			
			boardDto.setTitle("title" + i);
			boardDto.setContent("content" + i);
			boardService.writeBoard(member.getId(), boardDto);
		}
		
	}
	
	@After
	public void finishTest() {
//		boardRepo.deleteAll();
//		memberRepo.deleteAll();
	}


	@Test
	public void getBoard() throws Exception{
		List<Board> list = boardRepo.findAll();
		assertTrue(list.size() >= 1);
		
		Board board = list.get(0);
		Integer wNo = board.getWNo();
		
		Board boardGet = boardService.getBoard(wNo);
		
		assertEquals(board.getWNo(), boardGet.getWNo());
		assertTrue(boardGet.getViewCnt() >= 1);
	}
	
	@Test
	public void getBoardList() {
		List<Board> list = boardRepo.findAll();
		
		String search = "title";
		List<Board> list1 = boardService.getBoardList(search);
		
		assertTrue(list.size() == list1.size());
		
		search = "cont";
		List<Board> list2 = boardService.getBoardList(search);
		assertTrue(list.size() == list2.size());
		
	}
	
	
}
