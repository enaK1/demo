package com.example.demo.Repository;

import com.example.demo.Entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByNameContainingIgnoreCase(String name);

    List<Member> findByNameContainingOrEmailContaining(String name, String email);

    Member findByEmail(String email);
}
