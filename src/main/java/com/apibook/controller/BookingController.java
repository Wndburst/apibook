package com.apibook.controller;

import com.apibook.dto.*;
import com.apibook.entity.Booking;
import com.apibook.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    // --- ADMIN -------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<Booking> newBooking(@Valid @RequestBody BookingDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newBooking(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/return/{id}")
    public Booking returnBook(@PathVariable Integer id) {
        return service.returnBook(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{email}")
    public List<Booking> bookings(@PathVariable String email) {
        return service.bookingsByEmail(email);
    }

    // --- LECTOR ------------------------
    @PreAuthorize("hasRole('LECTOR')")
    @GetMapping("/my")
    public List<Booking> myBookings(@RequestParam String email) {
        // email proviene del token en app real; aquí mantenemos compatibilidad con /booking/find/{email}
        return service.bookingsByEmail(email);
    }
}
