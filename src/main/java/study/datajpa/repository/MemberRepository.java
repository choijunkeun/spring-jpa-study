package study.datajpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

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

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username);   // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

    // 반환타입 Page || Slice (Page는 토탈카운트 쿼리도 날라가고, Slice는 안나감)
//    Page<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m left join fetch m.team t",
            countQuery = "select count(m) from Member m")   // Hibernate6 부터는 자동으로 카운터쿼리 최적화해줌(조인 안걸고 Member카운트만 가져옴)
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)   // 이 쿼리 나가고 난 다음 clear()자동으로 해줌
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    @Query("select m from Member m left join fetch m.team") // Member를 조회할 때 연관된 Team을 한방쿼리로 끌고옴(fetch)
    List<Member> findMemberFetchJoin();


    // 기존의 findAll은 Lazy로딩이 되어서 team은 프록시객체를 가져올텐데,
    // @EntityGraph 붙여주면 fetch조인으로 객체를 가져온다.
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

//    @EntityGraph("Member.all")
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) // 변경감지체크 안함
    Member findReadOnlyByUsername(String username);


    @Lock(LockModeType.PESSIMISTIC_WRITE)   // select ~for update
    List<Member> findLockByUsername(String username);


}
