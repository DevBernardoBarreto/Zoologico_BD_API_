package com.example.pedidos.controller;

import com.example.pedidos.OrderEntity;
import com.example.pedidos.OrderItem;
import com.example.pedidos.Product;
import com.example.pedidos.repository.OrderRepository;
import com.example.pedidos.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public OrderController(OrderRepository orderRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @GetMapping
    public List<OrderEntity> all() { return orderRepo.findAll(); }

    @PostMapping
    public ResponseEntity<OrderEntity> create(@RequestBody OrderEntity order) {
        // naive total calculation
        double total = 0.0;
        if(order.getItems() != null) {
            for(OrderItem it : order.getItems()) {
                Product p = productRepo.findById(it.getProduct().getId()).orElse(null);
                if(p != null) {
                    it.setPrice(p.getPrice());
                    total += p.getPrice() * (it.getQuantity() == null ? 1 : it.getQuantity());
                }
                it.setOrder(order);
            }
        }
        order.setTotal(total);
        OrderEntity saved = orderRepo.save(order);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> get(@PathVariable Long id){
        return orderRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> update(@PathVariable Long id, @RequestBody OrderEntity updated){
        return orderRepo.findById(id).map(existing -> {
            existing.setStatus(updated.getStatus());
            existing.setCustomer(updated.getCustomer());
            // skipping deep item updates for brevity
            return ResponseEntity.ok(orderRepo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    
}
