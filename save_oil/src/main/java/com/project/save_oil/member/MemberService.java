package com.project.save_oil.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.save_oil.domain.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepo memberRepo;

	@Transactional
	public void save(MemberDto memberDto) {
		memberRepo.save(memberDto.toEntity());
	}

	public void update(String id, Member member) {
		Member mem = memberRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("가입 정보가 없습니다."));

		mem.update(member.getName(), member.getPassword(), member.getCarName(), member.getCompany(),
				member.getOilType(), member.getFuelEffi());
	}

	public Member findById(String id) {
		Member member = memberRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("가입 정보가 없습니다."));

		return new Member(member);
	}

}
