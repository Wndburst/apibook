package com.apibook.service;

import com.apibook.dto.*;
import com.apibook.entity.*;
import com.apibook.exception.ApiException;
import com.apibook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepo;
    private final CopyBookRepository copyRepo;

    public Book createBook(BookDto dto) {
        if (bookRepo.findByTitle(dto.title()).isPresent())
            throw new ApiException("Título ya existente");

        Book b = new Book(null, dto.author(), dto.title(), dto.type(),
                dto.image64() != null ? java.util.Base64.getDecoder().decode(dto.image64()) : null);
        return bookRepo.save(b);
    }

    public CopyBook addCopy(CopyBookDto dto) {
        Book book = bookRepo.findById(dto.bookId())
                .orElseThrow(() -> new ApiException("Libro no encontrado"));
        CopyBook cp = new CopyBook(null, book, true);
        return copyRepo.save(cp);
    }

    public List<Book> findAll() { return bookRepo.findAll(); }

    public List<Book> findByType(String type) {
        return bookRepo.findByType(type);
    }

    public Book findByTitle(String title) {
        return bookRepo.findByTitle(title).orElseThrow(() -> new ApiException("Libro no encontrado"));
    }

    public List<CopyBook> availableCopies(String title) {
        List<CopyBook> disponibles = copyRepo.findByBook_TitleContainingIgnoreCaseAndStateIsTrue(title);

        if (disponibles.isEmpty()) {
            throw new ApiException("No hay copias disponibles para el título: " + title);
        }

        return disponibles;
    }
}