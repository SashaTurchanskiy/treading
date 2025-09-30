package com.zosh.treading.repository;

import com.zosh.treading.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepo extends JpaRepository<Coin, Integer> {

}
