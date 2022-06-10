package com.project.save_oil.board;

import java.util.List;

public interface BoardService {
	//1. 게시글 읽기 -> 게시글 번호로 BoardDto 받기 + 조회수 증가
	public Board getBoard(Integer wNo) throws Exception;
	
	//2. 게시글 리스트 가져오기 -> 검색어를 바탕으로 List 받기
	public List<Board> getBoardList(String search);
	
	//3. 게시글 작성하기
	public void writeBoard(String id, BoardDto boardDto)  throws Exception;

	//4. 게시글 수정하기
	public void reWriteBoard(BoardDto boardDto);

	//5. 게시글 삭제하기 -> 게시글 번호에 해당하는 댓글 전부 삭제 + 게시글 삭제
	public void deleteBoard(Integer wNo);
}