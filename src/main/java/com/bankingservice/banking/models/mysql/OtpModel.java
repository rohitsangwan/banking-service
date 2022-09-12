package com.bankingservice.banking.models.mysql;

import com.bankingservice.banking.enums.OtpStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "otp_details")
public class OtpModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(name = "otp")
    private Long otp;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OtpStatus otpStatus;

    @Column(name = "otp_id", unique = true)
    private Integer otpId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "otp_id", referencedColumnName = "id", insertable = false, updatable = false, unique = true)
    private RegisterUserModel registerUserModel;
}
