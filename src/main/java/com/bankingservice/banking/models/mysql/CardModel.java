package com.bankingservice.banking.models.mysql;

import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.CardState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "card_details")
public class CardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(name = "cardNumber")
    private Long cardNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "cvv")
    private Integer cvv;

    @Column(name = "pin")
    private Integer pin;

    @Column(name = "cardState")
    @Enumerated(EnumType.STRING)
    private CardState cardState;

    @Column(name = "card_id", unique = true)
    private Integer cardId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id", insertable = false, updatable = false, unique = true)
    private RegisterUserModel registerUser;
}
