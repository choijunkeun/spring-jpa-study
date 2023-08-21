package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom { //인터페이스 이름은 상관없지만 MemberRepositoryImpl << 이 이름은 규칙임.(xml에서 바꿀 수 있지만 웬만하면 관례를 지키자)

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
