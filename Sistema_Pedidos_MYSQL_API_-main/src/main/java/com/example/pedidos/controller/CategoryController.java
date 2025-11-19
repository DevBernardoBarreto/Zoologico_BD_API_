package com.example.pedidos.controller;

import com.example.pedidos.Category;
import com.example.pedidos.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository repo;

    public CategoryController(CategoryRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Category> all() { return repo.findAll(); }

    @PostMapping
    public Category create(@RequestBody Category c) { return repo.save(c); }

    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id){
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category c){
        return repo.findById(id).map(existing -> {
            existing.setName(c.getName());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    
}
