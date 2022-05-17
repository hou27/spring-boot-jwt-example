package demo.api.common.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter @Setter
/**
 * JPA Entity 클래스들이 CoreEntity 추상 클래스를 상속할 경우
 * createDate, modifiedDate를 컬럼으로 인식하도록
 * MappedSuperclass 어노테이션을 추가
 */
@MappedSuperclass
/**
 * Spring Data JPA에서 시간에 대해서 자동으로 값을 넣어주는 기능인
 * JPA Audit를 사용하기 위해 아래 줄을 통해
 * CoreEntity 클래스에 Auditing 기능을 포함
 *
 * 그리고
 * 스프링 부트의 Entry 포인트 클래스에
 * @EnableJpaAuditing 어노테이션을 적용하여 JPA Auditing을 활성화
 */
@EntityListeners({AuditingEntityListener.class})
public class CoreEntity {
  @CreatedDate
  private LocalDate createdAt;

  @LastModifiedDate
  private LocalDate updateAt;

  @Id // 이 프로퍼티가 pk 역할을 한다는 것을 명시
  @Column(name = "id") // 객체 필드와 DB 테이블 컬럼을 맵핑
  @GeneratedValue(strategy= GenerationType.IDENTITY) // @GeneratedValue는 pk의 값을 위한 자동 생성 전략을 명시하는데 사용
  private Long id;
}
