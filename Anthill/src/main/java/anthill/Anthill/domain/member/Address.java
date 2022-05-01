package anthill.Anthill.domain.member;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String zipCode;
    private String address1;
    private String address2;

}
