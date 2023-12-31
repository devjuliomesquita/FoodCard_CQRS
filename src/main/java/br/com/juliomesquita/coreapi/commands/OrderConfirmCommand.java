package br.com.juliomesquita.coreapi.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@Builder
public class OrderConfirmCommand {
    @TargetAggregateIdentifier
    private UUID foodCartId;
}
