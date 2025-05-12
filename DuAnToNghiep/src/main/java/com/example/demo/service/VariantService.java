package com.example.demo.service;

import com.example.demo.dto.AttributeDTO;
import com.example.demo.dto.AttributeOptionDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.VariantDTO;
import com.example.demo.entity.Attribute;
import com.example.demo.entity.AttributeOption;
import com.example.demo.entity.Product;
import com.example.demo.entity.Variant;
import com.example.demo.repository.AttributeOptionRepository;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VariantService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeOptionRepository attributeOptionRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    /**
     * Tạo chuỗi code đã chuẩn hóa từ VariantDTO
     * @param variantDTO DTO chứa thông tin variant
     * @return Chuỗi code chuẩn hóa
     */
    private String generateStandardizedCode(VariantDTO variantDTO) {
        if (variantDTO.getAttributes().isEmpty() || variantDTO.getAttributeOptions().isEmpty()) {
            throw new RuntimeException("Phải có ít nhất một cặp thuộc tính và tùy chọn");
        }
        if (variantDTO.getAttributes().size() != variantDTO.getAttributeOptions().size()) {
            throw new RuntimeException("Số lượng thuộc tính và tùy chọn không khớp");
        }

        List<String> sortedPairs = variantDTO.getAttributes().stream()
                .sorted(Comparator.comparing(AttributeDTO::getAttributeId))
                .map(attribute -> {
                    int index = variantDTO.getAttributes().indexOf(attribute);
                    AttributeOptionDTO option = variantDTO.getAttributeOptions().get(index);
                    return attribute.getAttributeId() + ":" + option.getAttributeOptionId();
                })
                .collect(Collectors.toList());

        return String.join(";", sortedPairs);
    }

    /**
     * Kiểm tra trùng lặp variant
     * @param productId ID sản phẩm
     * @param code Mã variant
     */
    private void checkDuplicateVariant(Integer productId, String code) {
        if (variantRepository.existsByProductIdAndCode(productId, code)) {
            throw new RuntimeException("Biến thể sản phẩm này đã tồn tại: " + convertCodeToReadableFormat(code));
        }
    }

    /**
     * Chuyển đổi mã variant thành định dạng dễ đọc
     * @param code Mã variant
     * @return Chuỗi định dạng dễ đọc
     */
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
                    .orElse(null);
            String attributeName = attribute != null ? attribute.getName() : "Unknown Attribute";

            AttributeOption option = attributeOptionRepository.findById(attributeOptionId)
                    .orElse(null);
            String optionValue = option != null ? option.getValue() : "Unknown Option";

            return attributeName + ":" + optionValue;
        }).collect(Collectors.toList());

        return String.join(", ", readablePairs);
    }

    /**
     * Lấy tất cả sản phẩm
     * @return Danh sách ProductDTO
     */
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductDTO(product.getProductId(), product.getProductName(),
                        product.getDescription(), product.getBrand(), null))
                .collect(Collectors.toList());
    }

    /**
     * Lấy tất cả thuộc tính
     * @return Danh sách AttributeDTO
     */
    public List<AttributeDTO> getAllAttributes() {
        List<Attribute> attributes = attributeRepository.findAll();
        return attributes.stream()
                .map(attribute -> new AttributeDTO(attribute.getAttributeId(), attribute.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Lấy tất cả tùy chọn thuộc tính
     * @return Danh sách AttributeOptionDTO
     */
    public List<AttributeOptionDTO> getAllAttributeOptions() {
        List<AttributeOption> attributeOptions = attributeOptionRepository.findAll();
        return attributeOptions.stream()
                .map(option -> new AttributeOptionDTO(option.getAttributeOptionId(), option.getValue(), null))
                .collect(Collectors.toList());
    }

    /**
     * Lấy tùy chọn thuộc tính theo attributeId
     * @param attributeId ID thuộc tính
     * @return Danh sách AttributeOptionDTO
     */
    public List<AttributeOptionDTO> getAttributeOptionsByAttributeId(Integer attributeId) {
        List<AttributeOption> options = attributeOptionRepository.findByAttribute_AttributeId(attributeId);
        return options.stream()
                .map(option -> new AttributeOptionDTO(option.getAttributeOptionId(), option.getValue(), null))
                .collect(Collectors.toList());
    }

    /**
     * Lưu variant
     * @param variantDTO DTO chứa thông tin variant
     * @param imageFile File hình ảnh
     * @throws IOException Nếu lỗi khi tải ảnh
     */
    public void saveVariant(VariantDTO variantDTO, MultipartFile imageFile) throws IOException {
        if (variantDTO.getProducts().isEmpty()) {
            throw new RuntimeException("Chưa cung cấp ID sản phẩm");
        }

        Integer productId = variantDTO.getProducts().get(0).getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        String code = generateStandardizedCode(variantDTO);
        checkDuplicateVariant(productId, code);

        if (imageFile == null || imageFile.isEmpty()) {
            throw new RuntimeException("Vui lòng tải lên hình ảnh");
        }
        Map uploadResult = cloudinaryService.uploadFile(imageFile);
        String imageUrl = (String) uploadResult.get("url");

        Variant variant = new Variant();
        variant.setProduct(product);
        variant.setCode(code);
        variant.setImage(imageUrl);
        variant.setPrice(variantDTO.getPrice());

        variantRepository.save(variant);
    }

    /**
     * Lấy tất cả variants với phân trang, sắp xếp mới nhất trước
     * @param pageable Đối tượng chứa thông tin phân trang (page, size)
     * @return Page<VariantDTO> Danh sách variant với thông tin phân trang
     */
    public Page<VariantDTO> getAllVariants(Pageable pageable) {
        Pageable sortedByIdDesc = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
            Sort.by("variantId").descending()
        );
        return variantRepository.findAll(sortedByIdDesc)
                .map(variant -> {
                    VariantDTO variantDTO = new VariantDTO(
                            variant.getVariantId(),
                            variant.getCode(),
                            variant.getImage(),
                            variant.getPrice(),
                            List.of(new ProductDTO(
                                    variant.getProduct().getProductId(),
                                    variant.getProduct().getProductName(),
                                    variant.getProduct().getDescription(),
                                    variant.getProduct().getBrand(),
                                    null)),
                            null,
                            null
                    );
                    variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
                    return variantDTO;
                });
    }

    /**
     * Cập nhật variant
     * @param variantId ID của variant
     * @param variantDTO DTO chứa thông tin mới
     * @param imageFile File hình ảnh (có thể null)
     * @throws IOException Nếu lỗi khi tải ảnh
     */
    public void updateVariant(Integer variantId, VariantDTO variantDTO, MultipartFile imageFile) throws IOException {
        Variant existingVariant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy variant"));

        Integer productId = variantDTO.getProducts().get(0).getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        String code = generateStandardizedCode(variantDTO);

        boolean isCodeChanged = !Objects.equals(existingVariant.getCode(), code);
        boolean isProductChanged = existingVariant.getProduct() == null || !Objects.equals(existingVariant.getProduct().getProductId(), productId);

        if (isCodeChanged || isProductChanged) {
            checkDuplicateVariant(productId, code);
        }

        String imageUrl = existingVariant.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            Map uploadResult = cloudinaryService.uploadFile(imageFile);
            imageUrl = (String) uploadResult.get("url");
        }

        existingVariant.setProduct(product);
        existingVariant.setCode(code);
        existingVariant.setImage(imageUrl);
        existingVariant.setPrice(variantDTO.getPrice());

        variantRepository.save(existingVariant);
    }

    /**
     * Xóa variant
     * @param variantId ID của variant cần xóa
     */
    public void deleteVariant(Integer variantId) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy variant với ID: " + variantId));
        variantRepository.delete(variant);
    }

    /**
     * Lấy biến thể theo productId
     * @param productId ID sản phẩm
     * @return Danh sách VariantDTO
     */
    public List<VariantDTO> getVariantsByProductId(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));

        List<Variant> variants = variantRepository.findByProduct(product);

        return variants.stream()
                .map(variant -> {
                    VariantDTO variantDTO = new VariantDTO(
                            variant.getVariantId(),
                            variant.getCode(),
                            variant.getImage(),
                            variant.getPrice(),
                            List.of(new ProductDTO(
                                    variant.getProduct().getProductId(),
                                    variant.getProduct().getProductName(),
                                    variant.getProduct().getDescription(),
                                    variant.getProduct().getBrand(),
                                    null
                            )),
                            null,
                            null
                    );
                    variantDTO.setReadableCode(convertCodeToReadableFormat(variant.getCode()));
                    return variantDTO;
                })
                .collect(Collectors.toList());
    }
}