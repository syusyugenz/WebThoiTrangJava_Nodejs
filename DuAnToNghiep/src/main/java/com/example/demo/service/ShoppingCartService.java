//package com.example.demo.service;
//
//import com.example.demo.dto.ProductDTO;
//import com.example.demo.dto.UserRegisterDTO;
//import com.example.demo.dto.VariantDTO;
//import com.example.demo.dto.ShoppingCartDTO;
//import com.example.demo.entity.ShoppingCart;
//import com.example.demo.repository.ProductRepository;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.repository.VariantRepository;
//import com.example.demo.repository.ShoppingCartRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ShoppingCartService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private VariantRepository variantRepository;
//
//    @Autowired
//    private ShoppingCartRepository shoppingCartRepository;
//
//    // Phương thức lọc VariantDTO theo productId
//    public List<VariantDTO> filterVariantsByProductId(int productId) {
//        return variantRepository.findByProduct_ProductId(productId)
//                .stream()
//                .map(variant -> new VariantDTO(
//                        variant.getVariantId(),
//                        variant.getCode(),
//                        variant.getImage(),
//                        variant.getPrice(),
//                        null, null, null)) // Không cần danh sách sản phẩm ở đây
//                .collect(Collectors.toList());
//    }
//
//    // Phương thức lấy tất cả sản phẩm
//    public List<ProductDTO> getAllProducts() {
//        return productRepository.findAll()
//                .stream()
//                .map(product -> new ProductDTO(
//                        product.getProductId(),
//                        product.getProductName(),
//                        product.getDescription(),
//                        product.getBrand(),
//                        null)) // Không cần danh sách categories ở đây
//                .collect(Collectors.toList());
//    }
//
//    // Phương thức thêm hoặc cập nhật sản phẩm vào giỏ hàng
//    public ShoppingCartDTO addToCart(int userId, int variantId, int quantity) {
//        // Lấy giỏ hàng của người dùng
//        List<ShoppingCart> carts = shoppingCartRepository.findByUser_UserId(userId);
//
//        // Kiểm tra nếu giỏ hàng đã tồn tại
//        for (ShoppingCart cart : carts) {
//            if (cart.getVariant().getVariantId() == variantId) {
//                // Nếu đã có sản phẩm trong giỏ, cập nhật số lượng
//                cart.setQuantity(cart.getQuantity() + quantity);
//                shoppingCartRepository.save(cart);
//                return convertToDTO(cart);
//            }
//        }
//
//        // Nếu không có giỏ hàng nào với variantId, tạo mới
//        ShoppingCart newCart = new ShoppingCart();
//        newCart.setUser(userRepository.findById(userId).orElse(null));
//        newCart.setVariant(variantRepository.findById(variantId).orElse(null));
//        newCart.setQuantity(quantity);
//
//        // Lưu giỏ hàng mới
//        shoppingCartRepository.save(newCart);
//        return convertToDTO(newCart);
//    }
//
//    // Phương thức chuyển đổi ShoppingCart thành ShoppingCartDTO
//    private ShoppingCartDTO convertToDTO(ShoppingCart cart) {
//        VariantDTO variant = new VariantDTO(
//                cart.getVariant().getVariantId(),
//                cart.getVariant().getCode(),
//                cart.getVariant().getImage(),
//                cart.getVariant().getPrice(),
//                null // Không cần danh sách sản phẩm ở đây
//, null, null
//        );
//
//        return new ShoppingCartDTO(
//                cart.getCartId(),
//                cart.getQuantity(),
//                List.of(new UserRegisterDTO(cart.getUser().getUserId(), cart.getUser().getUsername(), null, null, null, null, null, null, null)),
//                List.of(variant),
//                List.of() // Nếu không cần danh sách sản phẩm ở đây
//        );
//    }
//
//    // Phương thức hiển thị giỏ hàng của người dùng
//    public List<ShoppingCartDTO> viewCart(int userId) {
//        // Kiểm tra xem người dùng có tồn tại không
//        Optional<UserRegisterDTO> userOpt = userRepository.findById(userId)
//                .map(user -> new UserRegisterDTO(user.getUserId(), user.getUsername(), user.getEmail(), null, null, null, null, null, null));
//        
//        if (!userOpt.isPresent()) {
//            throw new RuntimeException("Người dùng không tồn tại");
//        }
//
//        // Lấy danh sách giỏ hàng của người dùng
//        List<ShoppingCart> carts = shoppingCartRepository.findByUser_UserId(userId);
//
//        // Kiểm tra nếu giỏ hàng trống
//        if (carts.isEmpty()) {
//            System.out.println(userOpt.get().getUsername() + " bạn chưa có sản phẩm nào trong giỏ hàng");
//            return List.of(); // Trả về danh sách rỗng
//        }
//
//        // Chuyển đổi danh sách giỏ hàng thành ShoppingCartDTO
//        return carts.stream()
//                .map(cart -> {
//                    VariantDTO variant = new VariantDTO(
//                            cart.getVariant().getVariantId(),
//                            cart.getVariant().getCode(),
//                            cart.getVariant().getImage(),
//                            cart.getVariant().getPrice(),
//                            null // Không cần danh sách sản phẩm ở đây
//                           , null, null
//                    );
//
//                    // Lấy thông tin sản phẩm từ variant
//                    ProductDTO product = productRepository.findById(cart.getVariant().getProduct().getProductId())
//                            .map(prod -> new ProductDTO(prod.getProductId(), prod.getProductName(), prod.getDescription(), prod.getBrand(), null)) // Không cần danh sách categories
//                            .orElse(null);
//
//                    return new ShoppingCartDTO(
//                            cart.getCartId(),
//                            cart.getQuantity(),
//                            List.of(userOpt.get()),
//                            List.of(variant),
//                            product != null ? List.of(product) : List.of() // Nếu sản phẩm không tồn tại, trả về danh sách rỗng
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//}

package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.dto.ShoppingCartDTO;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VariantRepository;
import com.example.demo.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    // Phương thức lọc VariantDTO theo productId
    public List<VariantDTO> filterVariantsByProductId(int productId) {
        return variantRepository.findByProduct_ProductId(productId)
                .stream()
                .map(variant -> new VariantDTO(
                        variant.getVariantId(),
                        variant.getCode(),
                        variant.getImage(),
                        variant.getPrice(),
                        null, null, null))
                .collect(Collectors.toList());
    }

    // Phương thức lấy tất cả sản phẩm
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductDTO(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getBrand(),
                        null))
                .collect(Collectors.toList());
    }

    // Phương thức thêm hoặc cập nhật sản phẩm vào giỏ hàng
    public ShoppingCartDTO addToCart(int userId, int variantId, int quantity) {
        List<ShoppingCart> carts = shoppingCartRepository.findByUser_UserId(userId);

        for (ShoppingCart cart : carts) {
            if (cart.getVariant().getVariantId() == variantId) {
                cart.setQuantity(cart.getQuantity() + quantity);
                shoppingCartRepository.save(cart);
                return convertToDTO(cart);
            }
        }

        ShoppingCart newCart = new ShoppingCart();
        newCart.setUser(userRepository.findById(userId).orElse(null));
        newCart.setVariant(variantRepository.findById(variantId).orElse(null));
        newCart.setQuantity(quantity);

        shoppingCartRepository.save(newCart);
        return convertToDTO(newCart);
    }

    // **Phương thức mới: Xóa sản phẩm khỏi giỏ hàng**
    public void removeFromCart(int userId, int variantId) {
        List<ShoppingCart> carts = shoppingCartRepository.findByUser_UserId(userId);
        ShoppingCart cartToRemove = carts.stream()
                .filter(cart -> cart.getVariant().getVariantId() == variantId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trong giỏ hàng"));

        shoppingCartRepository.delete(cartToRemove);
    }

    // **Phương thức mới: Cập nhật số lượng sản phẩm trong giỏ hàng**
    public ShoppingCartDTO updateQuantity(int userId, int variantId, int newQuantity) {
        List<ShoppingCart> carts = shoppingCartRepository.findByUser_UserId(userId);
        ShoppingCart cartToUpdate = carts.stream()
                .filter(cart -> cart.getVariant().getVariantId() == variantId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trong giỏ hàng"));

        if (newQuantity <= 0) {
            shoppingCartRepository.delete(cartToUpdate);
            return null; // Trả về null nếu xóa sản phẩm
        }

        cartToUpdate.setQuantity(newQuantity);
        shoppingCartRepository.save(cartToUpdate);
        return convertToDTO(cartToUpdate);
    }

    // Phương thức chuyển đổi ShoppingCart thành ShoppingCartDTO
    private ShoppingCartDTO convertToDTO(ShoppingCart cart) {
        VariantDTO variant = new VariantDTO(
                cart.getVariant().getVariantId(),
                cart.getVariant().getCode(),
                cart.getVariant().getImage(),
                cart.getVariant().getPrice(),
                null, null, null
        );

        return new ShoppingCartDTO(
                cart.getCartId(),
                cart.getQuantity(),
                List.of(new UserRegisterDTO(cart.getUser().getUserId(), cart.getUser().getUsername(), null, null, null, null, null, null, null)),
                List.of(variant),
                List.of()
        );
    }

    // Phương thức hiển thị giỏ hàng của người dùng
    public List<ShoppingCartDTO> viewCart(int userId) {
        Optional<UserRegisterDTO> userOpt = userRepository.findById(userId)
                .map(user -> new UserRegisterDTO(user.getUserId(), user.getUsername(), user.getEmail(), null, null, null, null, null, null));
        
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Người dùng không tồn tại");
        }

        List<ShoppingCart> carts = shoppingCartRepository.findByUser_UserId(userId);

        if (carts.isEmpty()) {
            System.out.println(userOpt.get().getUsername() + " bạn chưa có sản phẩm nào trong giỏ hàng");
            return List.of();
        }

        return carts.stream()
                .map(cart -> {
                    VariantDTO variant = new VariantDTO(
                            cart.getVariant().getVariantId(),
                            cart.getVariant().getCode(),
                            cart.getVariant().getImage(),
                            cart.getVariant().getPrice(),
                            null, null, null
                    );

                    ProductDTO product = productRepository.findById(cart.getVariant().getProduct().getProductId())
                            .map(prod -> new ProductDTO(prod.getProductId(), prod.getProductName(), prod.getDescription(), prod.getBrand(), null))
                            .orElse(null);

                    return new ShoppingCartDTO(
                            cart.getCartId(),
                            cart.getQuantity(),
                            List.of(userOpt.get()),
                            List.of(variant),
                            product != null ? List.of(product) : List.of()
                    );
                })
                .collect(Collectors.toList());
    }
}