package com.shopsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the ShopSphere E-Commerce Backend Application.
 *
 * @SpringBootApplication encapsulates:
 * - @Configuration: Tags the class as a source of bean definitions for the application context.
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings.
 * - @ComponentScan: Tells Spring to scan for components, configurations, and services in this package and subpackages.
 */
@SpringBootApplication
public class ShopSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSphereApplication.class, args);
    }
}
