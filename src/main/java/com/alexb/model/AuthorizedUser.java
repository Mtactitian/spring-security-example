package com.alexb.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Getter
public class AuthorizedUser {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String password;

    private boolean enabled;

    @OneToMany(mappedBy = "authorizedUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Authority> authorities;
}
