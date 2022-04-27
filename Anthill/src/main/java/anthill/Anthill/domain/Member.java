package anthill.Anthill.domain;



import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
    @Column(name="member_Id")
    private Long id;

    @Column(name ="user_Id", nullable = false, unique = true, length = 20)
    String userId;

}
