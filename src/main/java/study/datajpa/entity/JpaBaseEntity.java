package study.datajpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class JpaBaseEntity {    // 순수 JPA 이용

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist // 퍼시스트 하기 전에 발생
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate  // 업데이트 전에 발생
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
