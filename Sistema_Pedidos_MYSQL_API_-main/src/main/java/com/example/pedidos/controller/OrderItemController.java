package com.example.pedidos.controller;

import com.example.pedidos.OrderItem;
import com.example.pedidos.repository.OrderItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemRepository repo;

    public OrderItemController(OrderItemRepository repo) { this.repo = repo; }

    @GetMapping
    public List<OrderItem> all() { return repo.findAll(); }

    @PostMapping
    public OrderItem create(@RequestBody OrderItem it) { return repo.save(it); }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> get(@PathVariable Long id){
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> update(@PathVariable Long id, @RequestBody OrderItem it){
        return repo.findById(id).map(existing -> {
            existing.setQuantity(it.getQuantity());
            existing.setPrice(it.getPrice());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    
}
