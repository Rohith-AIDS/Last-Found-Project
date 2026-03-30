package com.project1.demo.controller;

import com.project1.demo.dto.MapItemDTO;
import com.project1.demo.enums.ItemType;
import com.project1.demo.service.MapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    /**
     * GET /map/items
     * Returns all active items with coordinates as map pins.
     * Optional: ?type=LOST or ?type=FOUND
     *
     * Used by frontend to show all pins on the map.
     */
    @GetMapping("/items")
    public ResponseEntity<List<MapItemDTO>> getAllMapItems(
            @RequestParam(required = false) ItemType type) {
        return ResponseEntity.ok(mapService.getAllMapItems(type));
    }

    /**
     * GET /map/search/radius?lat=12.9716&lng=77.5946&radiusKm=5
     * Returns items within radiusKm of given coordinates.
     * Optional: ?type=LOST or ?type=FOUND
     *
     * Used by frontend: user picks a point/circle on the map and searches.
     */
    @GetMapping("/search/radius")
    public ResponseEntity<List<MapItemDTO>> searchByRadius(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5.0") double radiusKm,
            @RequestParam(required = false) ItemType type) {

        if (radiusKm <= 0 || radiusKm > 100) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(mapService.searchByRadius(lat, lng, radiusKm, type));
    }
}
