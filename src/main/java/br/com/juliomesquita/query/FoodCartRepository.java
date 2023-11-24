package br.com.juliomesquita.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodCartRepository extends JpaRepository<FoodCartView, UUID> {
}
