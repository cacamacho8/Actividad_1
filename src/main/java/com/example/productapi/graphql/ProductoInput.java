package com.example.productapi.graphql;

import com.example.productapi.model.Producto;

import java.math.BigDecimal;

public record ProductoInput(String nombre, String descripcion, Double precio) {
    public Producto toProducto() {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio == null ? null : BigDecimal.valueOf(precio));
        return producto;
    }
}