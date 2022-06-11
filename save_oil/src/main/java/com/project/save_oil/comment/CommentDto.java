package com.project.save_oil.comment;

import java.util.Date;

import com.project.save_oil.board.Board;
import com.project.save_oil.member.Member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {
	private Integer cNo;

	private String comment;

	private Member member;

	private Date writeDate = new Date();

	private Date reWriteDate;

	private Board board;
	
	public Comment toEntity() {
		Comment comments = Comment.builder()
				.comment(comment)
				.member(member)
				.board(board)
				.build();
		return comments;
	}
	
	public CommentDto(Comment cmnt) {
		super();
		this.cNo = cmnt.getCNo();
		this.comment = cmnt.getComment();
		this.member = cmnt.getMember();
		this.writeDate = cmnt.getWriteDate();
		this.reWriteDate = cmnt.getReWriteDate();
		this.board = cmnt.getBoard();
	}
	
}
