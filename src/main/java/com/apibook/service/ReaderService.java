package com.apibook.service;

import com.apibook.entity.*;
import com.apibook.exception.ApiException;
import com.apibook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final UserRepository userRepo;
    private final BookingRepository bookingRepo;
    private final FineRepository fineRepo;

    public User changeState(String email, boolean state) {
        User u = userRepo.findByEmail(email)
                .orElseThrow(() -> new ApiException("Usuario no encontrado"));

        u.setState(state);
        userRepo.save(u);

        if (state) {
            List<Fine> multas = fineRepo.findByUser_Email(email).stream()
                    .filter(Fine::getState) // solo las activas
                    .toList();

            for (Fine m : multas) {
                m.setState(false);
            }
            fineRepo.saveAll(multas);
        }

        return u;
    }

    public User detail(String email) { return userRepo.findByEmail(email)
            .orElseThrow(() -> new ApiException("Usuario no encontrado")); }

    public List<Booking> bookings(String email) { return bookingRepo.findByUser_Email(email); }

    public List<Fine> fines(String email) { return fineRepo.findByUser_Email(email); }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public List<User> getByState(boolean state) {
        return userRepo.findAll().stream()
                .filter(u -> u.getState() == state)
                .toList();
    }
}