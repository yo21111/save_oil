package com.project.save_oil.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.save_oil.board.Board;
import com.project.save_oil.board.BoardRepository;
import com.project.save_oil.member.Member;
import com.project.save_oil.member.MemberRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository cmntRepo;
	private final MemberRepo memRepo;
	private final BoardRepository boardRepo;
	
	@Transactional
	public Comment save(String id, Integer wNo, CommentDto cmntDto) {
		Member member = memRepo.findById(id).orElseThrow(IllegalArgumentException::new);
		Board board = boardRepo.findById(wNo).orElseThrow(IllegalArgumentException::new);
		
		cmntDto.setMember(member);
		cmntDto.setBoard(board);
		
		return cmntRepo.save(cmntDto.toEntity());
	}
}
