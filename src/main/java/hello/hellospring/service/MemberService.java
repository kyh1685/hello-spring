package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    //DI 의존 주입 3가지
    //1. 필드 주입
    //@Autowired private final MemberRepository memberRepository;
    //2. setter 주입
    //@Autowired
    //public void MemberService(MemberRepository memberRepository){
    //    this.memberRepository = memberRepository;
    //}
    //3. 생성자 주입
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /*회원가입*/
    public Long join(Member member){
        validateDuplicateMember(member); //중복회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /*전체 회원 조회*/
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
