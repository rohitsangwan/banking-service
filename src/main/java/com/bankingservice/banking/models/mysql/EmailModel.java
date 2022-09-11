package com.bankingservice.banking.models.mysql;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailModel {
    private String to;
    private String subject;
    private Long body;
}
