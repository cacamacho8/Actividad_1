package com.example.productapi.graphql;

import com.example.productapi.model.Producto;
import com.example.productapi.service.ProductoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductoGraphQLController {
    private final ProductoService service;

    public ProductoGraphQLController(ProductoService service) {
        this.service = service;
    }

    @QueryMapping
    public List<Producto> productos() {
        return service.findAll();
    }

    @QueryMapping
    public Producto producto(@Argument Long id) {
        return service.findById(id);
    }

    @MutationMapping
    public Producto crearProducto(@Argument ProductoInput input) {
        return service.create(input.toProducto());
    }

    @MutationMapping
    public Producto actualizarProducto(@Argument Long id, @Argument ProductoInput input) {
        return service.update(id, input.toProducto());
    }

    @MutationMapping
    public Boolean eliminarProducto(@Argument Long id) {
        service.delete(id);
        return true;
    }
}