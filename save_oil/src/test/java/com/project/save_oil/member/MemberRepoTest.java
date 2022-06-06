package com.project.save_oil.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepoTest {
	@Autowired
	MemberRepo memberRepo;

	@After
	public void cleanup2() {
		memberRepo.deleteAll();
	}
	
	@Test
	public void find() {
		String id = "tester123";
		String name = "테스터";
		String password = "12345";
		String carName = "기아 모닝 휘발유차";
		String company = "기아";
		String oilType = "휘발유";
		String fuelEffi = "11.1";

		memberRepo.save(Member.builder().id(id).name(name).password(password).carName(carName).company(company)
				.oilType(oilType).fuelEffi(fuelEffi).build());

		List<Member> list = memberRepo.findAll();
		assertTrue(list.size() == 1);

		Member member = list.get(0);
		System.out.println("member : " + member);
		assertThat(member.getId()).isEqualTo(id);
		assertThat(member.getPassword()).isEqualTo(password);
	}
}
