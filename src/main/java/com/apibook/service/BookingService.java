package com.apibook.service;

import com.apibook.dto.*;
import com.apibook.entity.*;
import com.apibook.exception.ApiException;
import com.apibook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepo;
    private final CopyBookRepository copyRepo;
    private final UserRepository userRepo;
    private final FineRepository fineRepo;

    private static final int MAX_BOOKS = 3;
    private static final int LIMIT_DAYS = 5;
    private static final int FINE_PER_DAY = 1000;

    public Booking newBooking(BookingDto dto) {
        User user = userRepo.findByEmail(dto.userEmail())
                .orElseThrow(() -> new ApiException("Usuario no encontrado"));
        if (!user.getState()) throw new ApiException("Usuario bloqueado o multado");

        long activos = bookingRepo.findByUser_Email(user.getEmail()).stream()
                .filter(Booking::getState).count();
        if (activos >= MAX_BOOKS) throw new ApiException("Máximo 3 libros activos");

        CopyBook copy = copyRepo.findById(dto.copyBookId())
                .orElseThrow(() -> new ApiException("Copia no encontrada"));
        if (!copy.getState()) throw new ApiException("Copia no disponible");

        copy.setState(false);
        copyRepo.save(copy);

        Booking bk = new Booking(null, copy, user,
                LocalDateTime.now(), LocalDateTime.now().plusDays(LIMIT_DAYS), true);
        return bookingRepo.save(bk);
    }

    public Booking returnBook(Integer bookingId) {
        Booking bk = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ApiException("Préstamo no encontrado"));
        if (!bk.getState()) throw new ApiException("Préstamo ya devuelto");

        CopyBook copy = bk.getCopyBook();
        User user = bk.getUser();

        // calcular multa si corresponde
        long diasAtraso = ChronoUnit.DAYS.between(bk.getDateReturn(), LocalDateTime.now());
        if (diasAtraso > 0) {
            int monto = (int) diasAtraso * FINE_PER_DAY;
            Fine f = new Fine(null, monto,
                    "Retraso devolución: " + copy.getBook().getTitle(),
                    true, user);
            fineRepo.save(f);
            user.setState(false); // bloquear
            userRepo.save(user);
        }

        // cerrar préstamo y liberar copia
        copy.setState(true);
        copyRepo.save(copy);

        bk.setState(false);
        return bookingRepo.save(bk);
    }

    public List<Booking> bookingsByEmail(String email) {
        return bookingRepo.findByUser_Email(email);
    }
}