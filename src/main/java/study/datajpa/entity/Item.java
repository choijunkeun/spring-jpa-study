package study.datajpa.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {
    // @GenerateValue 를 쓸 수 없을 때 Persistable를 구현하고 JPA의 @createDate를 사용해서 새로운놈인지 확인

    // JPA식별자 생성 전력이 @GenerateValue면 save() 호출 시점에 식별자가 없으므로ㅓ 새로운 엔티리로 인식해서 정상동작
    // 근데 생성전략을 @Id만 사용해서 직접 할당해주는거면 이미 식별자값이 있는 상태로 save()호출하고,
    // merge()가 호출된다. merge는 우선 DB를 호출해서 값을 확인하고,
    // db에 값이 없으면 새로운 엔티티로 인지하므로 비효율적이다
    // 따라서 Persistable을 사용해서 새로운 엔티티 확인 여부를 직접 구현하는게 효과적.
    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    public Item(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
