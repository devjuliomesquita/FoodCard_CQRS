package br.com.juliomesquita.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class FoodCartView {
    @Id
    private UUID foodCrtId;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<UUID, Integer> products;

    public void addProducts(UUID productId, Integer amount){
        products.compute(
                productId,
                (key, quantity) -> (quantity == null ? 0 : quantity) + amount
        );
    }

    public void removeProducts(UUID productId, Integer amount){
        products.compute(
                productId,
                (key, quantity) -> (quantity == null ? 0 : quantity) - amount
        );
    }
}
