package com.marotech.recording.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Transactional
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"mobileNumber"})})
public class User extends BaseEntity {

    @Column(nullable = false, length = 32)
    private String firstName;
    @Column(length = 16)
    private String middleName;
    @Column(length = 80, nullable = false)
    private String lastName;
    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    private Address address;
    @Column(length = 32)
    @NotNull
    private String mobileNumber;
    @Column(length = 128)
    private String email;
    @Column(length = 32)
    private String nationalId = "";
    @Column(nullable = false)
    private LocalDate dateOfBirth = LocalDate.now();
    @Enumerated(EnumType.STRING)
    @NotNull
    private Verified verified = Verified.NO;
    @Enumerated(EnumType.STRING)
    @NotNull
    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;
    @Enumerated(EnumType.STRING)
    private AgentStatus agentStatus = AgentStatus.ACTIVE;
    @ToString.Exclude
    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> userRoles = new HashSet<>();
    @OneToOne(fetch = FetchType.EAGER)
    private Attachment photoId;

    public User(String firstName, String middleName, String lastName,
                Address address, String mobile, String nationalId) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.nationalId = nationalId;
        this.mobileNumber = mobile;
    }

    public boolean getHasRoles() {
        return userRoles != null && userRoles.size() > 0;
    }

    public void addUserRole(UserRole userRole) {
        if (userRoles == null) {
            userRoles = new HashSet<>();
        }
        this.userRoles.add(userRole);
    }

    public boolean getIsAdmin() {
        return hasRole(ADMINISTRATOR) ||
                hasRole(SYSTEM_ADMINISTRATOR);
    }

    public boolean hasRole(String roleName) {
        if (roleName == null) {
            return false;
        }
        for (UserRole role : userRoles) {
            if (role.getRoleName().equalsIgnoreCase(roleName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOneRoleOf(String[] roleNames) {
        if (roleNames == null || roleNames.length == 0) {
            return false;
        }
        for (String roleName : roleNames) {
            for (UserRole role : userRoles) {
                if (role.getRoleName().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getLastNameInitial() {
        return lastName.substring(0, 1);
    }

    public String getFirstNameInitial() {
        return firstName.substring(0, 1);
    }

    public String getInitials() {
        return getFirstNameInitial() + "." + getLastNameInitial();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public List<String> getRoleNames() {
        List<String> roles = new ArrayList<>();
        for (UserRole role : userRoles) {
            roles.add(role.getRoleName());
        }
        return roles;
    }

    public static final String ADMINISTRATOR = "Administrator";
    public static final String SYSTEM_ADMINISTRATOR = "System Administrator";
}
