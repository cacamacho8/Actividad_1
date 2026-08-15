package com.example.productapi.controller;

import com.example.productapi.model.Producto;
import com.example.productapi.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Validated
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) { this.service = service; }

    @GetMapping
    public List<Producto> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public Producto get(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<Producto> create(@Valid @RequestBody Producto p) {
        Producto created = service.create(p);
        return ResponseEntity.created(URI.create("/api/productos/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public Producto update(@PathVariable Long id, @Valid @RequestBody Producto p) { return service.update(id, p); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
