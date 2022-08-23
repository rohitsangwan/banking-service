package com.bankingservice.banking.models.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "register_user")
public class RegisterUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "mobileNumber")
    private long mobileNumber;

    @Column(name = "userName", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "userId")
    private String userId;

    @JsonIgnore
    @OneToOne(mappedBy = "registerUserModel")
    private UserOnBoardModel userOnBoardModel;
}
