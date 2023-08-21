package study.datajpa.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    // 항상 사용자 정의 리파지토리를 만들지 말고, 임의의 리파지토리를 만들고 스프링빈으로 등록해서 직접 사용해도된다.
    // 이 경우에는 스프링 데이터 JPA와는 아무런 관계없이 별도로 동작한다.
    // * 화면에 맞춘 복잡한 쿼리일 경우 이렇게 쓰자(핵심 비즈니스가 아닌것)

    private final EntityManager em;


    List<Member> findAllMembers() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
