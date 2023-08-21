package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		// 실제론 시큐리티 세션 뭐 그런것들에서 유저아이디 꺼내서 넣어줘야함
		// 아래는 테스트용으로 랜덤UUID를 넣어줬다.
		return () -> Optional.of(UUID.randomUUID().toString());
	}

}
