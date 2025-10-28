package com.zosh.treading.controller;

import com.zosh.treading.model.Coin;
import com.zosh.treading.model.User;
import com.zosh.treading.model.Watchlist;
import com.zosh.treading.service.CoinService;
import com.zosh.treading.service.UserService;
import com.zosh.treading.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final UserService userService;
    private final CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(@RequestHeader("Authorization") String jwt) throws Exception {
        var user = userService.findUserProfileByJwt(jwt);
        var watchlist = watchlistService.findUserWatchlist(user.getId());
        return ResponseEntity.ok(watchlist);
    }
    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlistById(@PathVariable Long watchlistId) throws Exception {
        var watchlist = watchlistService.findById(watchlistId);
        return ResponseEntity.ok(watchlist);
    }
    @PatchMapping("/add/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(@RequestHeader("Authorization")String jwt,
                                                   @PathVariable String coinId) throws Exception {
        var user = userService.findUserProfileByJwt(jwt);
        var coin = coinService.findById(coinId);
        var addCoin = watchlistService.addItemToWatchlist(coin, user);
        return ResponseEntity.ok(addCoin);
    }



}
