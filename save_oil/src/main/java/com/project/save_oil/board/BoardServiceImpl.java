package com.project.save_oil.board;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.save_oil.member.Member;
import com.project.save_oil.member.MemberRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	private final BoardRepository boardRepository;
	private final MemberRepo memberRepository;
	
	//1. 게시글 읽기 -> 게시글 번호로 BoardDto 받기 + 조회수 증가
	@Override
	@Transactional
	public Board getBoard(Integer wNo)  throws Exception {
		Board board = boardRepository.findById(wNo).orElseThrow(NullPointerException::new);
		board.increaseViewCnt();
		return board;
	}
	
	//2. 게시글 리스트 가져오기 -> 검색어를 바탕으로 List 받기
	@Override
	@Transactional
	public List<Board> getBoardList(String search) {
		if(search == null || search.equals("")) {
			Sort sort = Sort.by(Sort.Order.desc("wNo"), Sort.Order.desc("viewCnt"));
			List<Board> list = boardRepository.findAll(sort);
			return list;
		}
		
		List<Board> listByTitle = boardRepository.findByTitleContainsOrderByViewCntDesc(search);
		List<Board> listByContent = boardRepository.findByContentContainsOrderByViewCntDesc(search);
		return Stream.of(listByTitle, listByContent).flatMap(x -> x.stream()).collect(Collectors.toList());
	}
	
	//3. 게시글 작성하기
	@Override
	@Transactional
	public Board writeBoard(String id, BoardDto boardDto) throws Exception {
		Member member = memberRepository.findById(id).orElseThrow(NullPointerException::new);
		boardDto.setMember(member);
		Board board = boardRepository.save(boardDto.toEntity());
		return board;
	}

	//4. 게시글 수정하기
	@Override
	@Transactional	
	public void reWriteBoard(BoardDto boardDto) {
		Board board = boardRepository.findById(boardDto.getWNo()).orElseThrow(NullPointerException::new);
		board.update(boardDto.getTitle(), boardDto.getContent());
	}
	@Override
	@Transactional
	//5. 게시글 삭제하기 -> 게시글 번호에 해당하는 댓글 전부 삭제 + 게시글 삭제
	public void deleteBoard(Integer wNo) {
		Board board = boardRepository.findById(wNo).orElseThrow(NullPointerException::new);
		boardRepository.delete(board);
	}
	
}