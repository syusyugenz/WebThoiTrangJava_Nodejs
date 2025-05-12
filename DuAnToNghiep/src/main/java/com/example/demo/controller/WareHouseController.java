package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.dto.WareHouseDTO;
import com.example.demo.service.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/warehouse")
public class WareHouseController {

    @Autowired
    private WareHouseService wareHouseService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = wareHouseService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/variants")
    public ResponseEntity<Page<VariantDTO>> getAllVariants(Pageable pageable) {
        Page<VariantDTO> variantPage = wareHouseService.getAllVariants(pageable);
        if (variantPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(variantPage);
    }

    @PostMapping("/add-variant")
    public ResponseEntity<String> addVariantToWareHouse(@RequestBody WareHouseDTO wareHouseDTO) {
        if (wareHouseDTO == null || wareHouseDTO.getVariants() == null || wareHouseDTO.getVariants().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input: WareHouseDTO or variants cannot be null or empty.");
        }

        try {
            wareHouseService.addVariantToWareHouse(wareHouseDTO);
            return ResponseEntity.ok("Variant added to warehouse successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<WareHouseDTO>> getAllWareHouses(Pageable pageable) {
        Page<WareHouseDTO> wareHousePage = wareHouseService.getAllWareHouses(pageable);
        if (wareHousePage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(wareHousePage);
    }

    @PutMapping("/{warehouseId}")
    public ResponseEntity<String> updateWareHouse(@PathVariable int warehouseId, @RequestBody WareHouseDTO wareHouseDTO) {
        if (wareHouseDTO == null) {
            return ResponseEntity.badRequest().body("Invalid input: WareHouseDTO cannot be null.");
        }

        try {
            wareHouseService.updateWareHouse(warehouseId, wareHouseDTO);
            return ResponseEntity.ok("Warehouse updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<String> deleteWareHouse(@PathVariable int warehouseId) {
        try {
            wareHouseService.deleteWareHouse(warehouseId);
            return ResponseEntity.ok("Warehouse deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/variants/{productId}")
    public ResponseEntity<List<WareHouseDTO>> getWareHousesByProductId(@PathVariable int productId) {
        List<WareHouseDTO> wareHouses = wareHouseService.getWareHousesByProductId(productId);
        return wareHouses.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(wareHouses);
    }

    @GetMapping("/stock/{variantId}")
    public ResponseEntity<Map<String, Object>> getStockInfoByVariantId(@PathVariable int variantId) {
        try {
            Map<String, Object> stockInfo = wareHouseService.getStockInfoByVariantId(variantId);
            return ResponseEntity.ok(stockInfo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "An error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/stock")
    public ResponseEntity<Page<Map<String, Object>>> getStockInfoForAllVariants(Pageable pageable) {
        try {
            Page<Map<String, Object>> stockInfoPage = wareHouseService.getStockInfoForAllVariants(pageable);
            return stockInfoPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(stockInfoPage);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/stock/product/{productId}")
    public ResponseEntity<List<Map<String, Object>>> getStockInfoByProductId(@PathVariable int productId) {
        try {
            List<Map<String, Object>> stockInfoList = wareHouseService.getStockInfoByProductId(productId);
            return stockInfoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(stockInfoList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of(Map.of("error", "An error occurred: " + e.getMessage())));
        }
    }
}