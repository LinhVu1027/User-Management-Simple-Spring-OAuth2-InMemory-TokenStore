package com.cloud.um.persistence.model;

import com.cloud.common.interfaces.dto.INameableDto;
import com.cloud.common.persistence.model.INameableEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
public class User implements INameableDto, INameableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private Boolean locked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")})
    private Set<Role> roles;

    public User() {
        locked = false;
    }

    public User(final String name, final String password, final Set<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }
}
