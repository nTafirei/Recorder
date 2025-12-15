package com.marotech.recording.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
@Data
@MappedSuperclass
public abstract class Org extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @NotNull
    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;
    @Enumerated(EnumType.STRING)
    @NotNull
    private OrgType orgType;
    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    @NotNull
    private Address address;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumbers;
    @Column(nullable = false)
    private String contactPerson;
    @Column(nullable = false)
    private String taxId;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String apiKey;
    public String getNormalisedName(){
        return name.replaceAll(" " ,"_").replaceAll("'", "_");
    }
}
