package com.zosh.treading.controller;

import com.zosh.treading.model.Asset;
import com.zosh.treading.service.AssetService;
import com.zosh.treading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;
    private final UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok(asset);
    }
    @GetMapping("/coin/{coinID}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(@PathVariable String coinId, @RequestHeader ("Authorization") String jwt) throws Exception {
        var userId = userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.findAssetByUserIdAndCoinId(userId.getId(), coinId);
        return ResponseEntity.ok(asset);
    }
    @GetMapping
    public ResponseEntity<List<Asset>> getAssetsForUser(@RequestHeader("Authorization")String jwt) throws Exception {
        var userId = userService.findUserProfileByJwt(jwt);
        List<Asset> assets = assetService.getUserAssets(userId.getId());
        return ResponseEntity.ok(assets);
    }
}
