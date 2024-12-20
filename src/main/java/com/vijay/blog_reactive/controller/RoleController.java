package com.vijay.blog_reactive.controller;

import com.vijay.blog_reactive.entity.Role;
import com.vijay.blog_reactive.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

    private RoleRepository roleRepository;

    @PostMapping
    public CompletableFuture<ResponseEntity<Role>> createRole(@RequestBody Role role) {
        return CompletableFuture.supplyAsync(() -> {
            Role savedRole = roleRepository.save(role);
            return ResponseEntity.ok(savedRole);
        });
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Role>>> getAllRoles() {
        return CompletableFuture.supplyAsync(() -> {
            List<Role> roles = roleRepository.findAll();
            return ResponseEntity.ok(roles);
        });
    }
}

