package study.datajpa.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
@NamedQuery(    // 실무에서 NamedQuery는 거의 안씀. 스프링 JPA에서 리파지토리에 바로 박는 막강한 기법이 있어서..단 어플리케이션 로딩 시점에 문법 파싱을 해서 오류가 있으면 바로 알려주는 큰 장점이 있다.
        name="Member.findByUsername",
        query="select m from Member m where m.username = :username"
)
//@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)  // XToOne 일 때 무조건 Lazy로 해주자(즉시로딩은 성능최적화가 굉장히 힘들다)
    @JoinColumn(name = "team_id")   // team의 FK명
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);    // 팀에 있는 Member의 팀에도 넣어줘야함
    }
}
