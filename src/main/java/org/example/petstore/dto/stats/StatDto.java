package org.example.petstore.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatDto {
    private String label;
    private int value;
    private int change;
    private String unit;
    private String currency;
}
