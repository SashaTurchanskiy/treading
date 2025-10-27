package com.zosh.treading.repository;

import com.zosh.treading.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepo extends JpaRepository<Watchlist, Long> {

    Watchlist findByUserId(Long userId);
}
