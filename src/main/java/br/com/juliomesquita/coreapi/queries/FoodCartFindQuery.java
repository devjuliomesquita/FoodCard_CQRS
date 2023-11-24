package br.com.juliomesquita.coreapi.queries;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FoodCartFindQuery {
    private UUID foodCartId;
}
