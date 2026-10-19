package com.shopsphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

/**
 * HealthController provides a lightweight status endpoint to confirm
 * the REST API is alive and reachable.
 */
@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> status = Map.of(
                "status", "UP",
                "application", "ShopSphere API",
                "timestamp", Instant.now().toString()
        );
        return ResponseEntity.ok(status);
    }
}
