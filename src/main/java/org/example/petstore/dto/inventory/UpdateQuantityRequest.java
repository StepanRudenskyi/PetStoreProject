package org.example.petstore.dto.inventory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateQuantityRequest {
    @NotNull(message = "Delta must be provided")
    @Pattern(regexp = "^-?\\d+$", message = "Delta must be a valid integer without spaces or extra characters " +
            "(e.g. '30' to restore or '-30' to reduce)")
    private String delta;

    public Integer getDeltaAsInteger() {
        return Integer.parseInt(delta);
    }
}
