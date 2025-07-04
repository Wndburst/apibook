package com.apibook.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fine")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fine")
    private Integer id;

    private Integer amount;

    private String description;

    private Boolean state = true; // multa activa

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_fk")
    private User user;
}