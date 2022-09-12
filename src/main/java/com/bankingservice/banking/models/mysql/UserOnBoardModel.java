package com.bankingservice.banking.models.mysql;

import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.Gender;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "onboard_user")
public class UserOnBoardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "aadhaarNumber")
    private Long aadhaarNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "accountNumber")
    private Long accountNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "accountType")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "register_user_id", unique = true)
    private Integer registerUserId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "register_user_id", referencedColumnName = "id", insertable = false, updatable = false, unique = true)
    private RegisterUserModel registerUserModel;

}
