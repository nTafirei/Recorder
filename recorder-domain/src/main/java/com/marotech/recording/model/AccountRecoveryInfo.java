package com.marotech.recording.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "recovery_info")
public class AccountRecoveryInfo extends BaseEntity {

    @Column
    private String recoveryEmail;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,
            CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<SecurityQuestion> securityQuestions;

    public void addSecurityQuestion(SecurityQuestion securityQuestion) {
        if (securityQuestions == null) {
            securityQuestions = new HashSet<>();
        }
        this.securityQuestions.add(securityQuestion);
    }

    public void clearSecurityQuestions() {
        if (securityQuestions != null) {
            securityQuestions.clear();
        }
    }
}
