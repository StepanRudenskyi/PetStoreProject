package org.example.petstore.service.cart;

import org.example.petstore.model.Cart;
import org.example.petstore.repository.CartRepository;
import org.example.petstore.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class responsible for cleaning up expired cart items.
 * It restores stock for products in expired carts and deletes those items from the cart.
 * The cleanup process runs periodically to ensure that cart items that haven't been purchased in time
 * are handled appropriately.
 */
@Service
public class CartCleanupService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    InventoryService inventoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CartCleanupService.class);
    private static final long SCHEDULE_INTERVAL_MS = 3600000; // run every hour
    private static final long DURATION_HR = 24; // will delete items older than 24 hours

    /**
     * This method runs periodically to clean up expired cart items.
     * It finds cart items that have been in the cart for more than a specified duration (24 hours in this case),
     * restores stock for the associated products, and deletes the expired cart items.
     * This method is scheduled to run at fixed intervals as defined by the @Scheduled annotation.
     * <p>
     * The task runs every 1 hour (3600000 milliseconds interval).
     */
    @Scheduled(fixedRate = SCHEDULE_INTERVAL_MS)
    public void cleanupExpiredCartItems() {
        LocalDateTime expirationDate = LocalDateTime.now().minusHours(DURATION_HR);

        List<Cart> expiredItems = cartRepository.findByAdditionTimeBefore(expirationDate);

        // restore inventory and delete cart
        if (expiredItems != null && !expiredItems.isEmpty()) {
            expiredItems.forEach(item -> {
                inventoryService.restoreStock(item.getProduct().getId(), item.getQuantity());
                cartRepository.delete(item);
            });
            LOGGER.info("--------> Cleaning up expired cart items. Found {} items to clean.", expiredItems.size());
        } else {
            LOGGER.info("--------> No expired cart items found.");
        }
    }
}
