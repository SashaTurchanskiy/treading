package com.zosh.treading.service;

import com.zosh.treading.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinsList(int page) throws Exception;
    String getMarketChart(String coinId, int days) throws Exception;
    String getCoinDetails(String coinId) throws Exception;
    Coin findById(Long id);
    String searchCoin(String keyword);
    String getTop50CoinsByMarketCapRank();
    String getTrendingCoins();
}
