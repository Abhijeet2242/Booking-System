package com.multigenesys.booking_system.model;

import com.multigenesys.booking_system.model.enums.RoleName;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Data  // helps in -> @Getter, @Setter and @toString  lombok features
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName name;
}
