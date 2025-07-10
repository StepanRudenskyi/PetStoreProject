package org.example.petstore.enums;

import java.util.Optional;

public enum OrderStatus {
    PROCESSING,
    COMPLETED,
    CANCELLED;

    public static Optional<OrderStatus> fromString(String status) {
        try {
            return Optional.of(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            return Optional.empty();
        }
    }
}
