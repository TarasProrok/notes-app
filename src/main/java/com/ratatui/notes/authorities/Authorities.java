package com.ratatui.notes.authorities;

import com.ratatui.notes.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "authorities", schema = "access")
public class Authorities {
    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "authority")
    private String authority;
}
