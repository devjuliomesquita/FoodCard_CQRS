package br.com.juliomesquita.coreapi.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.commandhandling.RoutingKey;

import java.util.UUID;
@Data
@Builder
public class FoodCartCreateCommand {

    @RoutingKey
    private UUID foodCartId;
}
