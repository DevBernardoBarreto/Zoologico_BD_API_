package com.example.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PedidosOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidosOnlineApplication.class, args);
        System.out.println("🚀 API de Pedidos Online iniciada com sucesso!");
    }
}
