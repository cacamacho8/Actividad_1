package com.example.productapi.grpc;

import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {
    private final ProductoGrpcService productoGrpcService;
    private final int port;
    private Server server;

    public GrpcServerConfig(
            ProductoGrpcService productoGrpcService,
            @Value("${grpc.server.port:9090}") int port) {
        this.productoGrpcService = productoGrpcService;
        this.port = port;
    }

    @PostConstruct
    public void start() throws IOException {
        server = NettyServerBuilder.forPort(port)
                .addService(productoGrpcService)
                .build()
                .start();
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
}
