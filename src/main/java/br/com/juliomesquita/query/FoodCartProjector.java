package br.com.juliomesquita.query;

import br.com.juliomesquita.coreapi.events.FoodCartCreatedEvent;
import br.com.juliomesquita.coreapi.events.ProductDeselectedEvent;
import br.com.juliomesquita.coreapi.events.ProductSelectedEvent;
import br.com.juliomesquita.coreapi.queries.FoodCartFindQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class FoodCartProjector {
    private final FoodCartRepository repository;

    @EventHandler
    public void on(FoodCartCreatedEvent event){
        FoodCartView foodCartView = new FoodCartView(event.getFoodCartId(), Collections.emptyMap());
        this.repository.save(foodCartView);
    }

    @EventHandler
    public void on(ProductSelectedEvent event){
        this.repository.findById(event.getFoodCartId())
                .ifPresent(
                        fcv -> fcv.addProducts(event.getProductId(), event.getQuantity())
                );
    }

    @EventHandler
    public void on(ProductDeselectedEvent event){
        this.repository.findById(event.getFoodCartId())
                .ifPresent(
                        fcv -> fcv.removeProducts(event.getProductId(), event.getQuantity())
                );
    }

    @QueryHandler
    public FoodCartView handle(FoodCartFindQuery query){
        return this.repository.findById(query.getFoodCartId()).orElse(null);
    }
}
