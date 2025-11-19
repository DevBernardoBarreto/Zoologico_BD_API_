package com.example.pedidos.controller;

import com.example.pedidos.Customer;
import com.example.pedidos.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Customer> all() { return repo.findAll(); }

    @PostMapping
    public Customer create(@RequestBody Customer c) { return repo.save(c); }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable Long id){
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer c){
        return repo.findById(id).map(existing -> {
            existing.setName(c.getName());
            existing.setEmail(c.getEmail());
            existing.setPhone(c.getPhone());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

 
}
