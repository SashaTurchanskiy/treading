package com.zosh.treading.service;

import com.zosh.treading.model.Coin;
import com.zosh.treading.model.User;
import com.zosh.treading.model.Watchlist;
import com.zosh.treading.repository.WatchlistRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService{

    private final WatchlistRepo watchlistRepo;


    @Override
    public Watchlist findUserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRepo.findByUserId(userId);
        if (watchlist == null){
            throw new Exception("Watchlist not found for user with ID: " + userId);
        }
        return watchlist;
    }

    @Override
    public Watchlist createWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchlistRepo.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> watchlistOptional = watchlistRepo.findById(id);
        if (watchlistOptional.isEmpty()){
            throw new Exception("Watchlist not found with ID: " + id);
        }
        return watchlistOptional.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        Watchlist watchlist = findUserWatchlist(user.getId());
        if (watchlist.getCoins().contains(coin)){
            watchlist.getCoins().remove(coin);
        }
        else watchlist.getCoins().add(coin);
        watchlistRepo.save(watchlist);
        return coin;
    }
}
