package com.alexb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_persisted_tokens")
@NoArgsConstructor
@Setter
@Getter
public class PersistedToken {

    @Id
    private String series;

    private String username;

    @Column(name = "TOKENVALUE")
    private String tokenValue;

    @Column(name = "LAST_USED")
    private Date lastUsed;

    public PersistedToken(PersistentRememberMeToken token) {
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.tokenValue = token.getTokenValue();
        this.lastUsed = token.getDate();
    }
}