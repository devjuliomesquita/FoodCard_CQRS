package br.com.juliomesquita.coreapi.events;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FoodCardCreatedEvent {
    private UUID foodCardId;
}