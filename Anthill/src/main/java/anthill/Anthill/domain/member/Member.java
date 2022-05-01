package anthill.Anthill.domain.member;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true, length = 20)
    private String userId;

    @Column(name="password", nullable = false, length = 255)
    private String password;

    @Column(name="nickname", nullable = false, unique = true, length = 20)
    private String nickName;

    @Column(name="name", nullable = false, length = 40)
    private String name;

    @Column(name="phone_number", nullable = false, length = 40)
    private String phone_number;

    @Embedded
    private Address address;
}
