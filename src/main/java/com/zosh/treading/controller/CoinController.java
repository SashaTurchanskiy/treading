package com.zosh.treading.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zosh.treading.model.Coin;
import com.zosh.treading.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
@RequiredArgsConstructor
public class CoinController {

    private final CoinService coinService;
    private final ObjectMapper objectMapper;

    @GetMapping("/list")
    public ResponseEntity<List<Coin>> getCoinList(@RequestParam("page") int page ) throws Exception {
        List<Coin> coins = coinService.getCoinsList(page);
        return new ResponseEntity<>(coins, HttpStatus.OK);
    }
    @GetMapping("/{coinId}/chart")
    public ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId,
                                                     @RequestParam("days")int days) throws Exception {
        String coins = coinService.getMarketChart(coinId, days);
        JsonNode jsonNode = objectMapper.readTree(coins);
        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<JsonNode> searchCoin(@RequestParam("q")String keyword) throws Exception {
        String coin = coinService.searchCoin(keyword);
        JsonNode jsonNode = objectMapper.readTree(coin);
        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }
    @GetMapping("/top50")
    public ResponseEntity<JsonNode> getTop50CoinsByMarketCapRank() throws Exception {
        String coins = coinService.getTop50CoinsByMarketCapRank();
        JsonNode jsonNode = objectMapper.readTree(coins);
        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }
    @GetMapping("/trending")
    public ResponseEntity<JsonNode> getTrendingCoins() throws Exception {
        String coins = coinService.getTrendingCoins();
        JsonNode jsonNode = objectMapper.readTree(coins);
        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }
    @GetMapping("details/{coinId}")
    public ResponseEntity<JsonNode>getCoinDetails(@PathVariable String coinId) throws Exception {
        String coin = coinService.getCoinDetails(coinId);
        JsonNode jsonNode = objectMapper.readTree(coin);
        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }

}
