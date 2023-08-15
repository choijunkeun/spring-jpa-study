package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 파라미터가 길어질 수록 메서드명이 길어지는 단점
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3MemberBy();

//    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    // 리파지토리에 바로 @Query가능, 일반적인 NamedQuery와 달리 어플리케이션 로딩시점에 문법체크해주는 장점이 있다.
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

}
