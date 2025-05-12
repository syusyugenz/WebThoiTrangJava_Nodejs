//package com.example.demo.service;
//
//import com.example.demo.dto.ProductDTO;
//import com.example.demo.dto.VariantDTO;
//import com.example.demo.dto.WareHouseDTO;
//import com.example.demo.entity.Attribute;
//import com.example.demo.entity.AttributeOption;
//import com.example.demo.entity.OrderItem;
//import com.example.demo.entity.Product;
//import com.example.demo.entity.Variant;
//import com.example.demo.entity.WareHouse;
//import com.example.demo.repository.AttributeOptionRepository;
//import com.example.demo.repository.AttributeRepository;
//import com.example.demo.repository.OrderItemRepository;
//import com.example.demo.repository.ProductRepository;
//import com.example.demo.repository.VariantRepository;
//import com.example.demo.repository.WareHouseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class WareHouseService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private VariantRepository variantRepository;
//
//    @Autowired
//    private WareHouseRepository wareHouseRepository;
//
//    @Autowired
//    private AttributeRepository attributeRepository;
//
//    @Autowired
//    private AttributeOptionRepository attributeOptionRepository;
//
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//
//    private String convertCodeToReadableFormat(String code) {
//        if (code == null || code.isEmpty()) {
//            return "N/A";
//        }
//
//        List<String> pairs = Arrays.asList(code.split(";"));
//        List<String> readablePairs = pairs.stream().map(pair -> {
//            String[] ids = pair.split(":");
//            if (ids.length != 2) {
//                return "Invalid pair";
//            }
//
//            Integer attributeId = Integer.parseInt(ids[0]);
//            Integer attributeOptionId = Integer.parseInt(ids[1]);
//
//            Attribute attribute = attributeRepository.findById(attributeId)
//                    .orElseGet(() -> null);
//            String attributeName = (attribute != null) ? attribute.getName() : "Unknown Attribute";
//
//            AttributeOption option = attributeOptionRepository.findById(attributeOptionId)
//                    .orElseGet(() -> null);
//            String optionValue = (option != null) ? option.getValue() : "Unknown Option";
//
//            return attributeName + ":" + optionValue;
//        }).collect(Collectors.toList());
//
//        return String.join(", ", readablePairs);
//    }
//
//    public List<ProductDTO> getAllProducts() {
//        List<Product> products = productRepository.findAll();
//        return products.stream()
//                .map(product -> new ProductDTO(
//                        product.getProductId(),
//                        product.getProductName(),
//                        product.getDescription(),
//                        product.getBrand(),
//                        null
//                ))
//                .collect(Collectors.toList());
//    }
//
//    public Page<VariantDTO> getAllVariants(Pageable pageable) {
//        Pageable sortedByIdDesc = PageRequest.of(
//            pageable.getPageNumber(),
//            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
//            Sort.by("variantId").descending()
//        );
//        return variantRepository.findAll(sortedByIdDesc)
//                .map(variant -> {
//                    List<ProductDTO> productList = new ArrayList<>();
//                    Product product = variant.getProduct();
//
//                    if (product != null) {
//                        productList.add(new ProductDTO(
//                                product.getProductId(),
//                                product.getProductName(),
//                                product.getDescription(),
//                                product.getBrand(),
//                                null
//                        ));
//                    }
//
//                    VariantDTO variantDTO = new VariantDTO(
//                            variant.getVariantId(),
//                            variant.getCode(),
//                            variant.getImage(),
//                            variant.getPrice(),
//                            productList,
//                            null,
//                            null
//                    );
//                    variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
//                    return variantDTO;
//                });
//    }
//
//    public void addVariantToWareHouse(WareHouseDTO wareHouseDTO) {
//        WareHouse wareHouse = new WareHouse();
//        wareHouse.setPrice(wareHouseDTO.getPrice());
//        wareHouse.setQuantity(wareHouseDTO.getQuantity());
//
//        List<VariantDTO> variantDTOs = wareHouseDTO.getVariants();
//        if (variantDTOs.isEmpty()) {
//            throw new RuntimeException("No variants provided");
//        }
//
//        Variant variant = variantRepository.findById(variantDTOs.get(0).getVariantId())
//                .orElseThrow(() -> new RuntimeException("Variant not found"));
//
//        wareHouse.setVariant(variant);
//        wareHouseRepository.save(wareHouse);
//    }
//
//    public Page<WareHouseDTO> getAllWareHouses(Pageable pageable) {
//        Pageable sortedByIdDesc = PageRequest.of(
//            pageable.getPageNumber(),
//            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
//            Sort.by("warehouseId").descending()
//        );
//        return wareHouseRepository.findAll(sortedByIdDesc)
//                .map(wareHouse -> {
//                    List<VariantDTO> variants = new ArrayList<>();
//                    Variant variant = wareHouse.getVariant();
//
//                    if (variant != null) {
//                        List<ProductDTO> products = new ArrayList<>();
//                        Product product = variant.getProduct();
//
//                        if (product != null) {
//                            products.add(new ProductDTO(
//                                    product.getProductId(),
//                                    product.getProductName(),
//                                    product.getDescription(),
//                                    product.getBrand(),
//                                    null
//                            ));
//                        }
//
//                        VariantDTO variantDTO = new VariantDTO(
//                                variant.getVariantId(),
//                                variant.getCode(),
//                                variant.getImage(),
//                                variant.getPrice(),
//                                products,
//                                null,
//                                null
//                        );
//                        variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
//                        variants.add(variantDTO);
//                    }
//
//                    return new WareHouseDTO(
//                            wareHouse.getWarehouseId(),
//                            wareHouse.getPrice(),
//                            wareHouse.getQuantity(),
//                            variants,
//                            null
//                    );
//                });
//    }
//
//    public void updateWareHouse(int warehouseId, WareHouseDTO wareHouseDTO) {
//        WareHouse wareHouse = wareHouseRepository.findById(warehouseId)
//                .orElseThrow(() -> new RuntimeException("WareHouse not found"));
//
//        wareHouse.setPrice(wareHouseDTO.getPrice());
//        wareHouse.setQuantity(wareHouseDTO.getQuantity());
//
//        if (wareHouseDTO.getVariants() != null && !wareHouseDTO.getVariants().isEmpty()) {
//            Variant variant = variantRepository.findById(wareHouseDTO.getVariants().get(0).getVariantId())
//                    .orElseThrow(() -> new RuntimeException("Variant not found"));
//            wareHouse.setVariant(variant);
//        }
//
//        wareHouseRepository.save(wareHouse);
//    }
//
//    public void deleteWareHouse(int warehouseId) {
//        WareHouse wareHouse = wareHouseRepository.findById(warehouseId)
//                .orElseThrow(() -> new RuntimeException("WareHouse not found"));
//        wareHouseRepository.delete(wareHouse);
//    }
//
//    public List<WareHouseDTO> getWareHousesByProductId(int productId) {
//        List<WareHouse> wareHouses = wareHouseRepository.findByVariant_Product_ProductId(productId);
//        return wareHouses.stream()
//                .map(wareHouse -> {
//                    List<VariantDTO> variants = new ArrayList<>();
//                    Variant variant = wareHouse.getVariant();
//
//                    if (variant != null) {
//                        VariantDTO variantDTO = new VariantDTO(
//                                variant.getVariantId(),
//                                variant.getCode(),
//                                variant.getImage(),
//                                variant.getPrice(),
//                                null,
//                                null,
//                                null
//                        );
//                        variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
//
//                        Product product = variant.getProduct();
//                        if (product != null) {
//                            ProductDTO productDTO = new ProductDTO(
//                                    product.getProductId(),
//                                    product.getProductName(),
//                                    product.getDescription(),
//                                    product.getBrand(),
//                                    null
//                            );
//                            variantDTO.setProducts(Collections.singletonList(productDTO));
//                        }
//
//                        variants.add(variantDTO);
//                    }
//
//                    return new WareHouseDTO(
//                            wareHouse.getWarehouseId(),
//                            wareHouse.getPrice(),
//                            wareHouse.getQuantity(),
//                            variants,
//                            null
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//
//    public Map<String, Object> getStockInfoByVariantId(int variantId) {
//        Optional<Integer> quantityInStockOptional = wareHouseRepository.sumQuantityByVariantId(variantId);
//        int quantityInStock = quantityInStockOptional.orElse(0);
//
//        if (quantityInStock == 0) {
//            Map<String, Object> result = new HashMap<>();
//            result.put("variantId", variantId);
//            result.put("quantityInStock", 0);
//            result.put("quantitySold", 0);
//            result.put("quantityRemaining", 0);
//            return result;
//        }
//
//        List<String> validStatuses = Arrays.asList("4", "5", "6", "7");
//        List<OrderItem> soldItems = orderItemRepository.findByVariantIdAndOrderStatusIn(variantId, validStatuses);
//        int quantitySold = soldItems.stream()
//                .mapToInt(OrderItem::getQuantity)
//                .sum();
//
//        int quantityRemaining = quantityInStock - quantitySold;
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("variantId", variantId);
//        result.put("quantityInStock", quantityInStock);
//        result.put("quantitySold", quantitySold);
//        result.put("quantityRemaining", Math.max(quantityRemaining, 0));
//        return result;
//    }
//
//    public Page<Map<String, Object>> getStockInfoForAllVariants(Pageable pageable) {
//        Pageable sortedByIdDesc = PageRequest.of(
//            pageable.getPageNumber(),
//            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
//            Sort.by("variantId").descending()
//        );
//        Page<Variant> variantPage = variantRepository.findAll(sortedByIdDesc);
//        List<Map<String, Object>> stockInfoList = variantPage.getContent().stream()
//                .map(variant -> getStockInfoByVariantId(variant.getVariantId()))
//                .distinct()
//                .collect(Collectors.toList());
//        return new PageImpl<>(stockInfoList, sortedByIdDesc, variantPage.getTotalElements());
//    }
//
//    public List<Map<String, Object>> getStockInfoByProductId(int productId) {
//        List<WareHouse> wareHouses = wareHouseRepository.findByVariant_Product_ProductId(productId);
//        return wareHouses.stream()
//                .map(wh -> getStockInfoByVariantId(wh.getVariant().getVariantId()))
//                .distinct()
//                .collect(Collectors.toList());
//    }
//}

//package com.example.demo.service;
//
//import com.example.demo.dto.ProductDTO;
//import com.example.demo.dto.VariantDTO;
//import com.example.demo.dto.WareHouseDTO;
//import com.example.demo.entity.Attribute;
//import com.example.demo.entity.AttributeOption;
//import com.example.demo.entity.OrderItem;
//import com.example.demo.entity.Product;
//import com.example.demo.entity.Variant;
//import com.example.demo.entity.WareHouse;
//import com.example.demo.repository.AttributeOptionRepository;
//import com.example.demo.repository.AttributeRepository;
//import com.example.demo.repository.OrderItemRepository;
//import com.example.demo.repository.ProductRepository;
//import com.example.demo.repository.VariantRepository;
//import com.example.demo.repository.WareHouseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class WareHouseService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private VariantRepository variantRepository;
//
//    @Autowired
//    private WareHouseRepository wareHouseRepository;
//
//    @Autowired
//    private AttributeRepository attributeRepository;
//
//    @Autowired
//    private AttributeOptionRepository attributeOptionRepository;
//
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//
//    private String convertCodeToReadableFormat(String code) {
//        if (code == null || code.isEmpty()) {
//            return "N/A";
//        }
//
//        List<String> pairs = Arrays.asList(code.split(";"));
//        List<String> readablePairs = pairs.stream().map(pair -> {
//            String[] ids = pair.split(":");
//            if (ids.length != 2) {
//                return "Invalid pair";
//            }
//
//            Integer attributeId = Integer.parseInt(ids[0]);
//            Integer attributeOptionId = Integer.parseInt(ids[1]);
//
//            Attribute attribute = attributeRepository.findById(attributeId)
//                    .orElseGet(() -> null);
//            String attributeName = (attribute != null) ? attribute.getName() : "Unknown Attribute";
//
//            AttributeOption option = attributeOptionRepository.findById(attributeOptionId)
//                    .orElseGet(() -> null);
//            String optionValue = (option != null) ? option.getValue() : "Unknown Option";
//
//            return attributeName + ":" + optionValue;
//        }).collect(Collectors.toList());
//
//        return String.join(", ", readablePairs);
//    }
//
//    public List<ProductDTO> getAllProducts() {
//        List<Product> products = productRepository.findAll();
//        return products.stream()
//                .map(product -> new ProductDTO(
//                        product.getProductId(),
//                        product.getProductName(),
//                        product.getDescription(),
//                        product.getBrand(),
//                        null
//                ))
//                .collect(Collectors.toList());
//    }
//
//    public Page<VariantDTO> getAllVariants(Pageable pageable) {
//        Pageable sortedByIdDesc = PageRequest.of(
//            pageable.getPageNumber(),
//            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
//            Sort.by("variantId").descending()
//        );
//        return variantRepository.findAll(sortedByIdDesc)
//                .map(variant -> {
//                    List<ProductDTO> productList = new ArrayList<>();
//                    Product product = variant.getProduct();
//
//                    if (product != null) {
//                        productList.add(new ProductDTO(
//                                product.getProductId(),
//                                product.getProductName(),
//                                product.getDescription(),
//                                product.getBrand(),
//                                null
//                        ));
//                    }
//
//                    VariantDTO variantDTO = new VariantDTO(
//                            variant.getVariantId(),
//                            variant.getCode(),
//                            variant.getImage(),
//                            variant.getPrice(),
//                            productList,
//                            null,
//                            null
//                    );
//                    variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
//                    return variantDTO;
//                });
//    }
//
//    public void addVariantToWareHouse(WareHouseDTO wareHouseDTO) {
//        WareHouse wareHouse = new WareHouse();
//        wareHouse.setPrice(wareHouseDTO.getPrice());
//        wareHouse.setQuantity(wareHouseDTO.getQuantity());
//
//        List<VariantDTO> variantDTOs = wareHouseDTO.getVariants();
//        if (variantDTOs.isEmpty()) {
//            throw new RuntimeException("No variants provided");
//        }
//
//        Variant variant = variantRepository.findById(variantDTOs.get(0).getVariantId())
//                .orElseThrow(() -> new RuntimeException("Variant not found"));
//
//        wareHouse.setVariant(variant);
//        wareHouseRepository.save(wareHouse);
//    }
//
//    public Page<WareHouseDTO> getAllWareHouses(Pageable pageable) {
//        Pageable sortedByIdDesc = PageRequest.of(
//            pageable.getPageNumber(),
//            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
//            Sort.by("warehouseId").descending()
//        );
//        return wareHouseRepository.findAll(sortedByIdDesc)
//                .map(wareHouse -> {
//                    List<VariantDTO> variants = new ArrayList<>();
//                    Variant variant = wareHouse.getVariant();
//
//                    if (variant != null) {
//                        List<ProductDTO> products = new ArrayList<>();
//                        Product product = variant.getProduct();
//
//                        if (product != null) {
//                            products.add(new ProductDTO(
//                                    product.getProductId(),
//                                    product.getProductName(),
//                                    product.getDescription(),
//                                    product.getBrand(),
//                                    null
//                            ));
//                        }
//
//                        VariantDTO variantDTO = new VariantDTO(
//                                variant.getVariantId(),
//                                variant.getCode(),
//                                variant.getImage(),
//                                variant.getPrice(),
//                                products,
//                                null,
//                                null
//                        );
//                        variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
//                        variants.add(variantDTO);
//                    }
//
//                    return new WareHouseDTO(
//                            wareHouse.getWarehouseId(),
//                            wareHouse.getPrice(),
//                            wareHouse.getQuantity(),
//                            variants,
//                            null
//                    );
//                });
//    }
//
//    public void updateWareHouse(int warehouseId, WareHouseDTO wareHouseDTO) {
//        WareHouse wareHouse = wareHouseRepository.findById(warehouseId)
//                .orElseThrow(() -> new RuntimeException("WareHouse not found"));
//
//        wareHouse.setPrice(wareHouseDTO.getPrice());
//        wareHouse.setQuantity(wareHouseDTO.getQuantity());
//
//        if (wareHouseDTO.getVariants() != null && !wareHouseDTO.getVariants().isEmpty()) {
//            Variant variant = variantRepository.findById(wareHouseDTO.getVariants().get(0).getVariantId())
//                    .orElseThrow(() -> new RuntimeException("Variant not found"));
//            wareHouse.setVariant(variant);
//        }
//
//        wareHouseRepository.save(wareHouse);
//    }
//
//    public void deleteWareHouse(int warehouseId) {
//        WareHouse wareHouse = wareHouseRepository.findById(warehouseId)
//                .orElseThrow(() -> new RuntimeException("WareHouse not found"));
//        wareHouseRepository.delete(wareHouse);
//    }
//
//    public List<WareHouseDTO> getWareHousesByProductId(int productId) {
//        List<WareHouse> wareHouses = wareHouseRepository.findByVariant_Product_ProductId(productId);
//        return wareHouses.stream()
//                .map(wareHouse -> {
//                    List<VariantDTO> variants = new ArrayList<>();
//                    Variant variant = wareHouse.getVariant();
//
//                    if (variant != null) {
//                        VariantDTO variantDTO = new VariantDTO(
//                                variant.getVariantId(),
//                                variant.getCode(),
//                                variant.getImage(),
//                                variant.getPrice(),
//                                null,
//                                null,
//                                null
//                        );
//                        variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
//
//                        Product product = variant.getProduct();
//                        if (product != null) {
//                            ProductDTO productDTO = new ProductDTO(
//                                    product.getProductId(),
//                                    product.getProductName(),
//                                    product.getDescription(),
//                                    product.getBrand(),
//                                    null
//                            );
//                            variantDTO.setProducts(Collections.singletonList(productDTO));
//                        }
//
//                        variants.add(variantDTO);
//                    }
//
//                    return new WareHouseDTO(
//                            wareHouse.getWarehouseId(),
//                            wareHouse.getPrice(),
//                            wareHouse.getQuantity(),
//                            variants,
//                            null
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//
//    public Map<String, Object> getStockInfoByVariantId(int variantId) {
//        Optional<Integer> quantityInStockOptional = wareHouseRepository.sumQuantityByVariantId(variantId);
//        int quantityInStock = quantityInStockOptional.orElse(0);
//
//        Variant variant = variantRepository.findById(variantId)
//                .orElseThrow(() -> new RuntimeException("Variant not found"));
//        String readableCode = convertCodeToReadableFormat(variant.getCode());
//        String productName = variant.getProduct() != null ? variant.getProduct().getProductName() : "N/A";
//
//        if (quantityInStock == 0) {
//            Map<String, Object> result = new HashMap<>();
//            result.put("variantId", variantId);
//            result.put("quantityInStock", 0);
//            result.put("quantitySold", 0);
//            result.put("quantityRemaining", 0);
//            result.put("productName", productName);
//            result.put("readableCode", readableCode);
//            return result;
//        }
//
//        List<String> validStatuses = Arrays.asList("4", "5", "6", "7");
//        List<OrderItem> soldItems = orderItemRepository.findByVariantIdAndOrderStatusIn(variantId, validStatuses);
//        int quantitySold = soldItems.stream()
//                .mapToInt(OrderItem::getQuantity)
//                .sum();
//
//        int quantityRemaining = quantityInStock - quantitySold;
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("variantId", variantId);
//        result.put("quantityInStock", quantityInStock);
//        result.put("quantitySold", quantitySold);
//        result.put("quantityRemaining", Math.max(quantityRemaining, 0));
//        result.put("productName", productName);
//        result.put("readableCode", readableCode);
//        return result;
//    }
//
//    public Page<Map<String, Object>> getStockInfoForAllVariants(Pageable pageable) {
//        Pageable sortedByIdDesc = PageRequest.of(
//            pageable.getPageNumber(),
//            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
//            Sort.by("variantId").descending()
//        );
//        Page<Variant> variantPage = variantRepository.findAll(sortedByIdDesc);
//        List<Map<String, Object>> stockInfoList = variantPage.getContent().stream()
//                .map(variant -> getStockInfoByVariantId(variant.getVariantId()))
//                .distinct()
//                .collect(Collectors.toList());
//        return new PageImpl<>(stockInfoList, sortedByIdDesc, variantPage.getTotalElements());
//    }
//
//    public List<Map<String, Object>> getStockInfoByProductId(int productId) {
//        List<WareHouse> wareHouses = wareHouseRepository.findByVariant_Product_ProductId(productId);
//        return wareHouses.stream()
//                .map(wh -> getStockInfoByVariantId(wh.getVariant().getVariantId()))
//                .distinct()
//                .collect(Collectors.toList());
//    }
//}

package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.dto.WareHouseDTO;
import com.example.demo.entity.Attribute;
import com.example.demo.entity.AttributeOption;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.Variant;
import com.example.demo.entity.WareHouse;
import com.example.demo.repository.AttributeOptionRepository;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.VariantRepository;
import com.example.demo.repository.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WareHouseService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeOptionRepository attributeOptionRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private String convertCodeToReadableFormat(String code) {
        if (code == null || code.isEmpty()) {
            return "N/A";
        }

        List<String> pairs = Arrays.asList(code.split(";"));
        List<String> readablePairs = pairs.stream().map(pair -> {
            String[] ids = pair.split(":");
            if (ids.length != 2) {
                return "Invalid pair";
            }

            Integer attributeId = Integer.parseInt(ids[0]);
            Integer attributeOptionId = Integer.parseInt(ids[1]);

            Attribute attribute = attributeRepository.findById(attributeId)
                    .orElseGet(() -> null);
            String attributeName = (attribute != null) ? attribute.getName() : "Unknown Attribute";

            AttributeOption option = attributeOptionRepository.findById(attributeOptionId)
                    .orElseGet(() -> null);
            String optionValue = (option != null) ? option.getValue() : "Unknown Option";

            return attributeName + ":" + optionValue;
        }).collect(Collectors.toList());

        return String.join(", ", readablePairs);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getBrand(),
                        null
                ))
                .collect(Collectors.toList());
    }

    public Page<VariantDTO> getAllVariants(Pageable pageable) {
        Pageable sortedByIdDesc = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
            Sort.by("variantId").descending()
        );
        return variantRepository.findAll(sortedByIdDesc)
                .map(variant -> {
                    List<ProductDTO> productList = new ArrayList<>();
                    Product product = variant.getProduct();

                    if (product != null) {
                        productList.add(new ProductDTO(
                                product.getProductId(),
                                product.getProductName(),
                                product.getDescription(),
                                product.getBrand(),
                                null
                        ));
                    }

                    VariantDTO variantDTO = new VariantDTO(
                            variant.getVariantId(),
                            variant.getCode(),
                            variant.getImage(),
                            variant.getPrice(),
                            productList,
                            null,
                            null
                    );
                    variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
                    return variantDTO;
                });
    }

    public void addVariantToWareHouse(WareHouseDTO wareHouseDTO) {
        WareHouse wareHouse = new WareHouse();
        wareHouse.setPrice(wareHouseDTO.getPrice());
        wareHouse.setQuantity(wareHouseDTO.getQuantity());

        List<VariantDTO> variantDTOs = wareHouseDTO.getVariants();
        if (variantDTOs.isEmpty()) {
            throw new RuntimeException("No variants provided");
        }

        Variant variant = variantRepository.findById(variantDTOs.get(0).getVariantId())
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        wareHouse.setVariant(variant);
        wareHouseRepository.save(wareHouse);
    }

    public Page<WareHouseDTO> getAllWareHouses(Pageable pageable) {
        Pageable sortedByIdDesc = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
            Sort.by("warehouseId").descending()
        );
        return wareHouseRepository.findAll(sortedByIdDesc)
                .map(wareHouse -> {
                    List<VariantDTO> variants = new ArrayList<>();
                    Variant variant = wareHouse.getVariant();

                    if (variant != null) {
                        List<ProductDTO> products = new ArrayList<>();
                        Product product = variant.getProduct();

                        if (product != null) {
                            products.add(new ProductDTO(
                                    product.getProductId(),
                                    product.getProductName(),
                                    product.getDescription(),
                                    product.getBrand(),
                                    null
                            ));
                        }

                        VariantDTO variantDTO = new VariantDTO(
                                variant.getVariantId(),
                                variant.getCode(),
                                variant.getImage(),
                                variant.getPrice(),
                                products,
                                null,
                                null
                        );
                        variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
                        variants.add(variantDTO);
                    }

                    return new WareHouseDTO(
                            wareHouse.getWarehouseId(),
                            wareHouse.getPrice(),
                            wareHouse.getQuantity(),
                            variants,
                            null
                    );
                });
    }

    public void updateWareHouse(int warehouseId, WareHouseDTO wareHouseDTO) {
        WareHouse wareHouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("WareHouse not found"));

        wareHouse.setPrice(wareHouseDTO.getPrice());
        wareHouse.setQuantity(wareHouseDTO.getQuantity());

        if (wareHouseDTO.getVariants() != null && !wareHouseDTO.getVariants().isEmpty()) {
            Variant variant = variantRepository.findById(wareHouseDTO.getVariants().get(0).getVariantId())
                    .orElseThrow(() -> new RuntimeException("Variant not found"));
            wareHouse.setVariant(variant);
        }

        wareHouseRepository.save(wareHouse);
    }

    public void deleteWareHouse(int warehouseId) {
        WareHouse wareHouse = wareHouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("WareHouse not found"));
        wareHouseRepository.delete(wareHouse);
    }

    public List<WareHouseDTO> getWareHousesByProductId(int productId) {
        List<WareHouse> wareHouses = wareHouseRepository.findByVariant_Product_ProductId(productId);
        return wareHouses.stream()
                .map(wareHouse -> {
                    List<VariantDTO> variants = new ArrayList<>();
                    Variant variant = wareHouse.getVariant();

                    if (variant != null) {
                        VariantDTO variantDTO = new VariantDTO(
                                variant.getVariantId(),
                                variant.getCode(),
                                variant.getImage(),
                                variant.getPrice(),
                                null,
                                null,
                                null
                        );
                        variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));

                        Product product = variant.getProduct();
                        if (product != null) {
                            ProductDTO productDTO = new ProductDTO(
                                    product.getProductId(),
                                    product.getProductName(),
                                    product.getDescription(),
                                    product.getBrand(),
                                    null
                            );
                            variantDTO.setProducts(Collections.singletonList(productDTO));
                        }

                        variants.add(variantDTO);
                    }

                    return new WareHouseDTO(
                            wareHouse.getWarehouseId(),
                            wareHouse.getPrice(),
                            wareHouse.getQuantity(),
                            variants,
                            null
                    );
                })
                .collect(Collectors.toList());
    }

    public Map<String, Object> getStockInfoByVariantId(int variantId) {
        // Lấy tổng số lượng tồn kho của biến thể từ bảng WareHouse
        Optional<Integer> quantityInStockOptional = wareHouseRepository.sumQuantityByVariantId(variantId);
        int quantityInStock = quantityInStockOptional.orElse(0);

        // Lấy thông tin biến thể
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Biến thể không tồn tại"));
        String readableCode = convertCodeToReadableFormat(variant.getCode());
        String productName = variant.getProduct() != null ? variant.getProduct().getProductName() : "N/A";

        // Nếu không có tồn kho, trả về kết quả với các giá trị bằng 0
        if (quantityInStock == 0) {
            Map<String, Object> result = new HashMap<>();
            result.put("variantId", variantId);
            result.put("quantityInStock", 0);
            result.put("quantitySold", 0);
            result.put("quantityRemaining", 0);
            result.put("productName", productName);
            result.put("readableCode", readableCode);
            return result;
        }

        // Các trạng thái đơn hàng được tính là đã bán (loại trừ trạng thái "3" - hủy)
        List<String> validStatuses = Arrays.asList("4", "5", "6", "7");

        // Lấy danh sách OrderItem của biến thể với các trạng thái hợp lệ
        List<OrderItem> soldItems = orderItemRepository.findByVariantIdAndOrderStatusIn(variantId, validStatuses);
        // Tính tổng số lượng đã bán
        int quantitySold = soldItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        // Tính số lượng tồn kho còn lại
        int quantityRemaining = quantityInStock - quantitySold;

        // Tạo đối tượng kết quả
        Map<String, Object> result = new HashMap<>();
        result.put("variantId", variantId);
        result.put("quantityInStock", quantityInStock);
        result.put("quantitySold", quantitySold);
        result.put("quantityRemaining", Math.max(quantityRemaining, 0)); // Đảm bảo không âm
        result.put("productName", productName);
        result.put("readableCode", readableCode);
        return result;
    }

    public Page<Map<String, Object>> getStockInfoForAllVariants(Pageable pageable) {
        Pageable sortedByIdDesc = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
            Sort.by("variantId").descending()
        );
        Page<Variant> variantPage = variantRepository.findAll(sortedByIdDesc);
        List<Map<String, Object>> stockInfoList = variantPage.getContent().stream()
                .map(variant -> getStockInfoByVariantId(variant.getVariantId()))
                .distinct()
                .collect(Collectors.toList());
        return new PageImpl<>(stockInfoList, sortedByIdDesc, variantPage.getTotalElements());
    }

    public List<Map<String, Object>> getStockInfoByProductId(int productId) {
        List<WareHouse> wareHouses = wareHouseRepository.findByVariant_Product_ProductId(productId);
        return wareHouses.stream()
                .map(wh -> getStockInfoByVariantId(wh.getVariant().getVariantId()))
                .distinct()
                .collect(Collectors.toList());
    }
}