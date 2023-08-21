package study.datajpa.controller;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")   // 도메인 클래스 컨버터 사용(권장하진 않음, 간단할 때만 사용 할 수 있다)
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable pageable) {
        // 요청 파라미터 (@PageableDefault 안 썼을경우, 컨트롤러마다 설정 가능)
        // page : 현재페이지, 0부터 시작
        // size : 한 페이지에 노출할 데이터 건수
        // sort : 정렬 조건 (asc는 생략가능)
        // 예 : /members?page=0&size=3&sort=id,desc&sort=username.desc

        // 1 .
//        return memberRepository.findAll(pageable)
//                .map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        // 2.
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
    }

    @PostConstruct
    public void init() {
        for (int i=0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));

        }
    }
}
