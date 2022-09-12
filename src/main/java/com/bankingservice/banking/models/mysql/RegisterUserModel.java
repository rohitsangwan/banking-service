package com.bankingservice.banking.models.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobileNumber")
    private Long mobileNumber;

    @Column(name = "userName", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "userId")
    private String userId;

    @JsonIgnore
    @OneToOne(mappedBy = "registerUserModel")
    private UserOnBoardModel userOnBoardModel;

    @JsonIgnore
    @OneToOne(mappedBy = "registerUser")
    private CardModel cardModel;
}
