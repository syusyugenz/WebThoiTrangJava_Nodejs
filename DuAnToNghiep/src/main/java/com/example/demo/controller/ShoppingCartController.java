//package com.example.demo.controller;
//
//import com.example.demo.dto.ProductDTO;
//import com.example.demo.dto.ShoppingCartDTO;
//import com.example.demo.dto.VariantDTO;
//import com.example.demo.service.ShoppingCartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin(value = {"*"})
//@RequestMapping("/api/cart")
//public class ShoppingCartController {
//
//    @Autowired
//    private ShoppingCartService shoppingCartService;
//
//    // Endpoint để thêm sản phẩm vào giỏ hàng
//    @PostMapping("/add")
//    public ResponseEntity<ShoppingCartDTO> addToCart(@RequestParam int userId, @RequestParam int variantId, @RequestParam int quantity) {
//        ShoppingCartDTO updatedCart = shoppingCartService.addToCart(userId, variantId, quantity);
//        return ResponseEntity.ok(updatedCart);
//    }
//
//    // Endpoint để hiển thị giỏ hàng của người dùng
//    @GetMapping("/view/{userId}")
//    public ResponseEntity<List<ShoppingCartDTO>> viewCart(@PathVariable int userId) {
//        List<ShoppingCartDTO> cartItems = shoppingCartService.viewCart(userId);
//        return ResponseEntity.ok(cartItems);
//    }
//
//    // Endpoint để lấy tất cả sản phẩm
//    @GetMapping("/products")
//    public ResponseEntity<List<ProductDTO>> getAllProducts() {
//        List<ProductDTO> products = shoppingCartService.getAllProducts();
//        return ResponseEntity.ok(products);
//    }
//
//    // Endpoint để lọc các biến thể sản phẩm theo productId
//    @GetMapping("/variants/{productId}")
//    public ResponseEntity<List<VariantDTO>> filterVariantsByProductId(@PathVariable int productId) {
//        List<VariantDTO> variants = shoppingCartService.filterVariantsByProductId(productId);
//        return ResponseEntity.ok(variants);
//    }
//}

package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ShoppingCartDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = {"*"})
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Endpoint để thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<ShoppingCartDTO> addToCart(@RequestParam int userId, @RequestParam int variantId, @RequestParam int quantity) {
        ShoppingCartDTO updatedCart = shoppingCartService.addToCart(userId, variantId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    // Endpoint để hiển thị giỏ hàng của người dùng
    @GetMapping("/view/{userId}")
    public ResponseEntity<List<ShoppingCartDTO>> viewCart(@PathVariable int userId) {
        List<ShoppingCartDTO> cartItems = shoppingCartService.viewCart(userId);
        return ResponseEntity.ok(cartItems);
    }

    // Endpoint để lấy tất cả sản phẩm
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = shoppingCartService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Endpoint để lọc các biến thể sản phẩm theo productId
    @GetMapping("/variants/{productId}")
    public ResponseEntity<List<VariantDTO>> filterVariantsByProductId(@PathVariable int productId) {
        List<VariantDTO> variants = shoppingCartService.filterVariantsByProductId(productId);
        return ResponseEntity.ok(variants);
    }

    // **Endpoint mới: Xóa sản phẩm khỏi giỏ hàng**
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam int userId, @RequestParam int variantId) {
        shoppingCartService.removeFromCart(userId, variantId);
        return ResponseEntity.ok("Đã xóa sản phẩm khỏi giỏ hàng.");
    }

    // **Endpoint mới: Cập nhật số lượng sản phẩm trong giỏ hàng**
    @PutMapping("/update-quantity")
    public ResponseEntity<ShoppingCartDTO> updateQuantity(
            @RequestParam int userId,
            @RequestParam int variantId,
            @RequestParam int quantity) {
        ShoppingCartDTO updatedCart = shoppingCartService.updateQuantity(userId, variantId, quantity);
        if (updatedCart == null) {
            return ResponseEntity.ok(null); // Nếu sản phẩm bị xóa do quantity <= 0
        }
        return ResponseEntity.ok(updatedCart);
    }
}