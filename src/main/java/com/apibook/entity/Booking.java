package com.apibook.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "copybook_fk")
    private CopyBook copyBook;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_fk")
    private User user;

    private LocalDateTime dateBooking;
    private LocalDateTime dateReturn;
    private Boolean state = true; // true = activo
}
