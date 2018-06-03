package com.alexb.model;

import com.alexb.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "t_user_authorities")
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Role authority;

    @ManyToOne
    @Getter
    @JoinColumn(name = "username", referencedColumnName = "username")
    private AuthorizedUser authorizedUser;

    @Override
    public String getAuthority() {
        return authority.name();
    }
}
