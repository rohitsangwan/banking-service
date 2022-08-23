package com.bankingservice.banking.models.mysql;

import com.bankingservice.banking.enums.AccountType;
import com.bankingservice.banking.enums.Gender;
import lombok.*;

import javax.persistence.*;

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
    private int id;

    @Column(name = "age")
    private int age;

    @Column(name = "aadhaarNumber")
    private long aadhaarNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "accountType")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "register_user_id")
    private int registerUserId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "register_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RegisterUserModel registerUserModel;

}
