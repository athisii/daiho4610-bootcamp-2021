package com.tothenew.entities.token;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class LogoutToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 300)
    private String token;

    public LogoutToken() {
    }

    public LogoutToken(String token) {
        this.token = token;
    }
}
