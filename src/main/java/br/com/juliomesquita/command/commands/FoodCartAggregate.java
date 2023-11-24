package br.com.juliomesquita.command.commands;

import br.com.juliomesquita.coreapi.commands.FoodCartCreateCommand;
import br.com.juliomesquita.coreapi.commands.OrderConfirmCommand;
import br.com.juliomesquita.coreapi.commands.ProductDeselectCommand;
import br.com.juliomesquita.coreapi.commands.ProductSelectCommand;
import br.com.juliomesquita.coreapi.events.FoodCartCreatedEvent;
import br.com.juliomesquita.coreapi.events.OrderConfirmedEvent;
import br.com.juliomesquita.coreapi.events.ProductDeselectedEvent;
import br.com.juliomesquita.coreapi.events.ProductSelectedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class FoodCartAggregate {

    private static final Logger logger = LoggerFactory.getLogger(FoodCartAggregate.class);

    @AggregateIdentifier
    private UUID foodCartId;
    private Map<UUID, Integer> selectedProducts;
    private boolean confirmed;

    @CommandHandler
    public FoodCartAggregate(FoodCartCreateCommand command) {
        AggregateLifecycle.apply(FoodCartCreatedEvent
                .builder()
                .foodCartId(command.getFoodCartId())
                .build()
        );
    }

    @CommandHandler
    public void handle(ProductSelectCommand command) {
        AggregateLifecycle.apply(ProductSelectedEvent
                .builder()
                .foodCartId(foodCartId)
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .build()
        );
    }

    @CommandHandler
    public void handle(ProductDeselectCommand command) {
        AggregateLifecycle.apply(ProductDeselectedEvent
                .builder()
                .foodCartId(foodCartId)
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .build()
        );
    }

    @CommandHandler
    public void handle(OrderConfirmCommand command) {
        AggregateLifecycle.apply(OrderConfirmedEvent
                .builder()
                .foodCartId(foodCartId)
                .build()
        );
    }

    @EventSourcingHandler
    public void on(FoodCartCreatedEvent event) {
        foodCartId = event.getFoodCartId();
        selectedProducts = new HashMap<>();
        confirmed = false;
    }

    @EventSourcingHandler
    public void on(ProductSelectedEvent event) {
        selectedProducts.merge(
                event.getProductId(),
                event.getQuantity(),
                Integer::sum
        );
    }

    @EventSourcingHandler
    public void on(ProductDeselectedEvent event) {
        selectedProducts.computeIfPresent(
                event.getProductId(),
                (productId, quantity) -> quantity -= event.getQuantity()
        );
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        this.confirmed = true;
    }
}
