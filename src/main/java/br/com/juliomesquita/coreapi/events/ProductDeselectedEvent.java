package br.com.juliomesquita.coreapi.events;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class ProductDeselectedEvent {
    private UUID foodCardId;
    private UUID productId;
    private Integer quantity;
}
