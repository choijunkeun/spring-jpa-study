package study.datajpa.dto;


import lombok.Data;
import study.datajpa.entity.Member;

@Data   // 엔티티에는 @Data를 쓰면 안됨. 게터세터 다 들어가있는거라서
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    public MemberDto(Member member) {   // Member가 저 위의 필드로 들어가는건 안된다.
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
