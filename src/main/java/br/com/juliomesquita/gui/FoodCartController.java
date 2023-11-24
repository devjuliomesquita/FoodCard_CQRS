package br.com.juliomesquita.gui;

import br.com.juliomesquita.coreapi.commands.FoodCartCreateCommand;
import br.com.juliomesquita.coreapi.commands.ProductDeselectCommand;
import br.com.juliomesquita.coreapi.commands.ProductSelectCommand;
import br.com.juliomesquita.coreapi.queries.FoodCartFindQuery;
import br.com.juliomesquita.query.FoodCartView;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/foodCart")
@RequiredArgsConstructor
public class FoodCartController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

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

    @GetMapping("/{foodCartId}")
    public CompletableFuture<FoodCartView> findFoodCart(@PathVariable("foodCartId") String foodCartId){
        return this.queryGateway.query(
                FoodCartFindQuery.builder().foodCartId(UUID.fromString(foodCartId)).build(),
                ResponseTypes.instanceOf(FoodCartView.class)
        );
    }


}
