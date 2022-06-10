package com.project.save_oil.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	// findByXXX: XXX 컬럼을 키워드로 검색
	// Containing: 특정 키워드 포함 여부
	// 검색어를 바탕으로 해당 검색어가 제목에 있는지 확인
	List<Board> findByTitleContainsOrderByViewCntDesc(String title);
	// 검색어를 바탕으로 해당 검색어가 내용에 있는지 확인
	List<Board> findByContentContainsOrderByViewCntDesc(String content);
}
