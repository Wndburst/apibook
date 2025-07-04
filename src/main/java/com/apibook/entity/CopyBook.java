package com.apibook.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "copy_book")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CopyBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_copybook")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_fk")
    private Book book;

    private Boolean state = true; // true = disponible, false = reservado
}