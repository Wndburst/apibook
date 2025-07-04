package com.apibook.controller;

import com.apibook.entity.User;
import com.apibook.service.ReaderService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{email}")
    public User detail(@PathVariable @Email String email) { return service.detail(email); }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/state/{email}")
    public User change(@PathVariable String email,
                       @RequestBody Map<String, Boolean> payload) {
        Boolean newState = payload.get("state");
        return service.changeState(email, newState );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<User> all(@RequestParam(required = false) Boolean state) {
        // simple filtro si se pasa ?state=true/false
        return state == null ? service.getAll() : service.getByState(state);
    }
}