package com.example.productapi.service;

import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.model.Producto;
import com.example.productapi.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> findAll() { return repo.findAll(); }

    public Producto findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }

    public Producto create(Producto p) { return repo.save(p); }

    public Producto update(Long id, Producto p) {
        Producto existing = findById(id);
        existing.setNombre(p.getNombre());
        existing.setDescripcion(p.getDescripcion());
        existing.setPrecio(p.getPrecio());
        return repo.save(existing);
    }

    public void delete(Long id) {
        Producto existing = findById(id);
        repo.delete(existing);
    }
}
