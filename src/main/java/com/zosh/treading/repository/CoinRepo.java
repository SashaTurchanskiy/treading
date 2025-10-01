package com.zosh.treading.repository;

import com.zosh.treading.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepo extends JpaRepository<Coin, Integer> {
    Optional<Coin> findById(String coinId);

}
