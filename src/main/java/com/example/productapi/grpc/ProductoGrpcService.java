package com.example.productapi.grpc;

import com.example.productapi.exception.ResourceNotFoundException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductoGrpcService extends ProductoServiceGrpc.ProductoServiceImplBase {
    private final com.example.productapi.service.ProductoService service;

    public ProductoGrpcService(com.example.productapi.service.ProductoService service) {
        this.service = service;
    }

    @Override
    public void listarProductos(Vacio request, StreamObserver<ListaProductos> responseObserver) {
        try {
            ListaProductos.Builder response = ListaProductos.newBuilder();
            service.findAll().stream()
                    .map(this::toGrpcProducto)
                    .forEach(response::addProductos);
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (Exception exception) {
            respondWithError(responseObserver, exception);
        }
    }

    @Override
    public void obtenerProducto(ProductoId request, StreamObserver<com.example.productapi.grpc.Producto> responseObserver) {
        try {
            responseObserver.onNext(toGrpcProducto(service.findById(request.getId())));
            responseObserver.onCompleted();
        } catch (Exception exception) {
            respondWithError(responseObserver, exception);
        }
    }

    @Override
    public void crearProducto(ProductoInput request, StreamObserver<com.example.productapi.grpc.Producto> responseObserver) {
        try {
            responseObserver.onNext(toGrpcProducto(service.create(toDomainProducto(request))));
            responseObserver.onCompleted();
        } catch (Exception exception) {
            respondWithError(responseObserver, exception);
        }
    }

    @Override
    public void actualizarProducto(ActualizarProductoRequest request, StreamObserver<com.example.productapi.grpc.Producto> responseObserver) {
        try {
            com.example.productapi.model.Producto updated = service.update(request.getId(), toDomainProducto(request.getProducto()));
            responseObserver.onNext(toGrpcProducto(updated));
            responseObserver.onCompleted();
        } catch (Exception exception) {
            respondWithError(responseObserver, exception);
        }
    }

    @Override
    public void eliminarProducto(ProductoId request, StreamObserver<OperacionResponse> responseObserver) {
        try {
            service.delete(request.getId());
            responseObserver.onNext(OperacionResponse.newBuilder()
                    .setExitoso(true)
                    .setMensaje("Producto eliminado correctamente")
                    .build());
            responseObserver.onCompleted();
        } catch (Exception exception) {
            respondWithError(responseObserver, exception);
        }
    }

    private com.example.productapi.model.Producto toDomainProducto(ProductoInput input) {
        com.example.productapi.model.Producto producto = new com.example.productapi.model.Producto();
        producto.setNombre(input.getNombre());
        producto.setDescripcion(input.getDescripcion());
        producto.setPrecio(BigDecimal.valueOf(input.getPrecio()));
        return producto;
    }

    private com.example.productapi.grpc.Producto toGrpcProducto(com.example.productapi.model.Producto producto) {
        return com.example.productapi.grpc.Producto.newBuilder()
                .setId(producto.getId())
                .setNombre(producto.getNombre())
                .setDescripcion(producto.getDescripcion() == null ? "" : producto.getDescripcion())
                .setPrecio(producto.getPrecio() == null ? 0 : producto.getPrecio().doubleValue())
                .build();
    }

    private void respondWithError(StreamObserver<?> responseObserver, Exception exception) {
        Status status = exception instanceof ResourceNotFoundException
                ? Status.NOT_FOUND
                : Status.INTERNAL;
        responseObserver.onError(status.withDescription(exception.getMessage()).withCause(exception).asRuntimeException());
    }
}
