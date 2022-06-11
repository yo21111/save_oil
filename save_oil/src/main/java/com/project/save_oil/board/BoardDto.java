package com.project.save_oil.board;

import java.util.Date;
import java.util.List;

import com.project.save_oil.comment.CommentDto;
import com.project.save_oil.member.Member;

import lombok.Data;

@Data
public class BoardDto {
	// 게시글 번호
	private Integer wNo;
	// 게시글 제목
	private String title;
	// 게시글 내용
	private String content;
	// 작성자(아이디) - reference Member
	private Member member;
	// 작성일자
	private Date writeDate;
	// 수정일자
	private Date reWriteDate;
	// 조회수
	private Integer viewCnt;
	
	private List<CommentDto> comments;
	
	/* Dto -> Entity */
	public Board toEntity() {
		Board board = Board.builder().title(title).content(content).member(member).build();
		return board;
	}
}
