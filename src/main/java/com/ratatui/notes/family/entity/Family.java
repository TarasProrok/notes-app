package com.ratatui.notes.family.entity;

import com.ratatui.notes.user.User;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "families", schema = "access")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title")
    @Size(min = 3, max = 100)
    private String title;

    @Column(name = "code")
    private String code;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "family")
    private Set<User> users;
}
