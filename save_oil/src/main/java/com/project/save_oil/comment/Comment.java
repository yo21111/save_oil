package com.project.save_oil.comment;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.project.save_oil.board.Board;
import com.project.save_oil.member.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity(name = "comment")
@Table(schema = "saveoil")
public class Comment {
	// 댓글 번호 (pk, ai)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cNo;

	// 댓글 내용 : not null
	@Column(nullable = false)
	private String comment;

	// 작성자 아이디
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "member_id")
	private Member member;

	// 작성일자
	@CreatedDate
	private Date writeDate = new Date();

	// 수정일자
	@LastModifiedDate
	private Date reWriteDate;

	// 게시글 번호
	@ManyToOne
	@JoinColumn(name="board_wNo")
	private Board board;

	public void update(String comment) {
		this.comment = comment;
		this.reWriteDate = new Date();
	}
	
	@Builder
	public Comment(String comment, Member member, Board board) {
		this.comment = comment;
		this.member = member;
		this.board = board;
	}
}
