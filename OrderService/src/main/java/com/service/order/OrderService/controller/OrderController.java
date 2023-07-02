package com.service.order.OrderService.controller;

import com.service.order.OrderService.dto.OrderRequest;
import com.service.order.OrderService.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @CircuitBreaker(name="OrderBreaker",fallbackMethod = "placeOrderFallback")
    @Retry(name="OrderBreaker",fallbackMethod = "placeOrderFallback")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {

        return orderService.placeOrder(orderRequest);
    }

    public String placeOrderFallback(OrderRequest orderRequest,Throwable throwable){
        return "Service is down, Please try again later";
    }

}