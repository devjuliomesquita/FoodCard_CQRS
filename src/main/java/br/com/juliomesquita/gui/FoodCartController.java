package br.com.juliomesquita.gui;

import br.com.juliomesquita.coreapi.commands.FoodCartCreateCommand;
import br.com.juliomesquita.coreapi.commands.ProductDeselectCommand;
import br.com.juliomesquita.coreapi.commands.ProductSelectCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/foodCart")
@RequiredArgsConstructor
public class FoodCartController {

    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public CompletableFuture<UUID> createFoodCart() {
        return commandGateway.send(
                FoodCartCreateCommand
                        .builder()
                        .foodCartId(UUID.randomUUID())
                        .build()
        );
    }

    @PostMapping("/{foodCartId}/select/{productId}/quantity/{quantity}")
    public void selectProduct(
            @PathVariable("foodCartId") String foodCartId,
            @PathVariable("productId") String productId,
            @PathVariable("quantity") Integer quantity
    ){
        commandGateway.send(ProductSelectCommand
                .builder()
                .foodCartId(UUID.fromString(foodCartId))
                .productId(UUID.fromString(productId))
                .quantity(quantity)
                .build()
        );
    }

    @PostMapping("{foodCartId}/deselect/{productId}/quantity/{quantity}")
    public void deselectProduct(
            @PathVariable("foodCartId") String foodCartId,
            @PathVariable("productId") String productId,
            @PathVariable("quantity") Integer quantity
    ){
        commandGateway.send(ProductDeselectCommand
                .builder()
                .foodCartId(UUID.fromString(foodCartId))
                .productId(UUID.fromString(productId))
                .quantity(quantity)
                .build()
        );
    }



}
