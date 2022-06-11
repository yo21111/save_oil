package com.project.save_oil.board;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.project.save_oil.comment.Comment;
import com.project.save_oil.member.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity(name = "board")
@Table(schema = "saveoil")
public class Board {
	// 게시글 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wNo;
	// 게시글 제목

	@Column(nullable = false)
	private String title;

	// 게시글 내용
	@Column
	private String content;

	// 작성자(아이디) - reference Member
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "member_id")
	private Member member;

	// 작성일자
	@CreatedDate
	private Date writeDate = new Date();

	// 수정일자
	@LastModifiedDate
	private Date reWriteDate;

	// 조회수
	private Integer viewCnt = 0;

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Comment> comments;

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
		this.reWriteDate = new Date();
	}

	public void increaseViewCnt() {
		this.viewCnt += 1;
	}

	@Builder
	public Board(String title, String content, Member member) {
		this.title = title;
		this.content = content;
		this.member = member;
	}

}
