package com.marotech.recording.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;


@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "auth_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"userName","mobileNumber"})})
public class AuthUser extends BaseEntity {

    @Column(nullable = false, length = 32)
    private String userName;
    @Column(nullable = false, length = 32)
    private String mobileNumber;
    @Column(length = 128)
    private String password;
    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER, cascade = {jakarta.persistence.CascadeType.REMOVE})
    private User user;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER)
    private List<SecurityQuestion> securityQuestions = new ArrayList<>();

    public static final String encodedPassword(String original) throws Exception {
        return DigestUtils.sha256Hex(original);
    }
}
