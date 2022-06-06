package com.project.save_oil.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, String> {
}
