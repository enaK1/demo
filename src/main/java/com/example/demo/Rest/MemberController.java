package com.example.demo.Rest;

import com.example.demo.Entities.Member;
import com.example.demo.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @GetMapping("/search/name")
    public List<Member> searchMemberByName(@RequestParam String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam String query) {
        List<Member> members = memberRepository.findByNameContainingOrEmailContaining(query, query);
        return ResponseEntity.ok(members);
    }

    @PostMapping
    public Member addMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            member.setName(memberDetails.getName());
            member.setEmail(memberDetails.getEmail());
            member.setPhoneNumber(memberDetails.getPhoneNumber());
            return memberRepository.save(member);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }
}
