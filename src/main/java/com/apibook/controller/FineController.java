package com.apibook.controller;


import com.apibook.entity.Fine;
import com.apibook.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fine")
@RequiredArgsConstructor
public class FineController {

    private final ReaderService service;

    // ADMIN y LECTOR ven igual pero filtrado por email
    @PreAuthorize("hasAnyRole('ADMIN','LECTOR')")
    @GetMapping("/find/{email}")
    public List<Fine> fines(@PathVariable String email) {
        return service.fines(email);
    }
}