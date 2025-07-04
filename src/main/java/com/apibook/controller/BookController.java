package com.apibook.controller;

import com.apibook.dto.*;
import com.apibook.entity.*;
import com.apibook.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/book")

@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    // --- PUBLIC -------------------------
    @GetMapping({"/all", "/all/"})
    public List<Book> all() { return service.findAll(); }

    @GetMapping("/all/{type}")
    public List<Book> byType(@PathVariable String type) { return service.findByType(type); }

    // --- ADMIN --------------------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<Book> newBook(@Valid @RequestBody BookDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBook(dto));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{title}")
    public Book byTitle(@PathVariable String title) {
        return service.findByTitle(title);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/newcopy")
    public ResponseEntity<CopyBook> newCopy(@Valid @RequestBody CopyBookDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCopy(dto));
    }

    // copias disponibles por título
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/copy/{title}")
    public List<CopyBook> available(@PathVariable("title") String title) {
        return service.availableCopies(title);
    }
}