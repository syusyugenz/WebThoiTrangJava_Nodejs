package com.example.demo.service;

import com.example.demo.dto.AttributeDTO;
import com.example.demo.dto.AttributeOptionDTO;
import com.example.demo.entity.Attribute;
import com.example.demo.entity.AttributeOption;
import com.example.demo.repository.AttributeOptionRepository;
import com.example.demo.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttributeOptionService {

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeOptionRepository attributeOptionRepository;

    /**
     * Chuẩn hóa giá trị (loại bỏ khoảng trắng thừa)
     * @param value Giá trị cần chuẩn hóa
     * @return Giá trị đã chuẩn hóa
     */
    private String normalizeValue(String value) {
        return (value != null) ? value.trim() : "";
    }

    /**
     * Lấy tất cả các Attribute
     * @return Danh sách Attribute dưới dạng DTO
     */
    public List<AttributeDTO> getAllAttributes() {
        List<Attribute> attributes = attributeRepository.findAll();
        return attributes.stream()
                .map(attribute -> new AttributeDTO(attribute.getAttributeId(), attribute.getName()))
                .toList();
    }

    /**
     * Thêm một AttributeOption mới
     * @param attributeOptionDTO DTO chứa thông tin AttributeOption
     * @return AttributeOptionDTO đã được lưu
     * @throws RuntimeException Nếu dữ liệu không hợp lệ hoặc giá trị đã tồn tại
     */
    public AttributeOptionDTO addAttributeOption(AttributeOptionDTO attributeOptionDTO) {
        if (attributeOptionDTO.getAttributes() == null || attributeOptionDTO.getAttributes().isEmpty()) {
            throw new RuntimeException("Danh sách thuộc tính không hợp lệ");
        }

        String normalizedValue = normalizeValue(attributeOptionDTO.getValue());
        if (normalizedValue.isEmpty()) {
            throw new RuntimeException("Giá trị không được để trống");
        }

        AttributeDTO attributeDTO = attributeOptionDTO.getAttributes().get(0);
        Optional<Attribute> attributeOpt;

        if (attributeDTO.getAttributeId() > 0) {
            attributeOpt = attributeRepository.findById(attributeDTO.getAttributeId());
        } else {
            attributeOpt = attributeRepository.findByName(attributeDTO.getName());
        }

        if (attributeOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy Attribute");
        }

        Attribute attribute = attributeOpt.get();

        Optional<AttributeOption> existingOption = attributeOptionRepository.findByValueIgnoreCaseAndAttribute_AttributeId(
                normalizedValue, attribute.getAttributeId());
        if (existingOption.isPresent()) {
            throw new RuntimeException("Giá trị '" + attributeOptionDTO.getValue() + "' đã tồn tại cho thuộc tính '" + attribute.getName() + "'!");
        }

        AttributeOption attributeOption = new AttributeOption();
        attributeOption.setValue(normalizedValue);
        attributeOption.setAttribute(attribute);

        AttributeOption savedOption = attributeOptionRepository.save(attributeOption);

        return new AttributeOptionDTO(savedOption.getAttributeOptionId(), savedOption.getValue(), attributeOptionDTO.getAttributes());
    }

    /**
     * Cập nhật một AttributeOption
     * @param id ID của AttributeOption cần cập nhật
     * @param attributeOptionDTO DTO chứa thông tin mới
     * @return AttributeOptionDTO đã được cập nhật
     * @throws RuntimeException Nếu dữ liệu không hợp lệ hoặc giá trị đã tồn tại
     */
    public AttributeOptionDTO updateAttributeOption(int id, AttributeOptionDTO attributeOptionDTO) {
        if (attributeOptionDTO.getAttributes() == null || attributeOptionDTO.getAttributes().isEmpty()) {
            throw new RuntimeException("Danh sách thuộc tính không hợp lệ");
        }

        String normalizedValue = normalizeValue(attributeOptionDTO.getValue());
        if (normalizedValue.isEmpty()) {
            throw new RuntimeException("Giá trị không được để trống");
        }

        Optional<AttributeOption> existingOptionOpt = attributeOptionRepository.findById(id);
        if (existingOptionOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy AttributeOption với ID: " + id);
        }

        AttributeOption existingOption = existingOptionOpt.get();
        AttributeDTO attributeDTO = attributeOptionDTO.getAttributes().get(0);
        Optional<Attribute> attributeOpt;

        if (attributeDTO.getAttributeId() > 0) {
            attributeOpt = attributeRepository.findById(attributeDTO.getAttributeId());
        } else {
            attributeOpt = attributeRepository.findByName(attributeDTO.getName());
        }

        if (attributeOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy Attribute");
        }

        Attribute attribute = attributeOpt.get();

        Optional<AttributeOption> existingValueOption = attributeOptionRepository.findByValueIgnoreCaseAndAttribute_AttributeId(
                normalizedValue, attribute.getAttributeId());
        if (existingValueOption.isPresent() && existingValueOption.get().getAttributeOptionId() != id) {
            throw new RuntimeException("Giá trị '" + attributeOptionDTO.getValue() + "' đã tồn tại cho thuộc tính '" + attribute.getName() + "'!");
        }

        existingOption.setValue(normalizedValue);
        existingOption.setAttribute(attribute);

        AttributeOption updatedOption = attributeOptionRepository.save(existingOption);
        return new AttributeOptionDTO(updatedOption.getAttributeOptionId(), updatedOption.getValue(), attributeOptionDTO.getAttributes());
    }

    /**
     * Lấy tất cả AttributeOptions với phân trang, sắp xếp mới nhất trước
     * @param pageable Đối tượng chứa thông tin phân trang (page, size)
     * @return Page<AttributeOptionDTO> Danh sách AttributeOption với thông tin phân trang
     */
    public Page<AttributeOptionDTO> getAllAttributeOptions(Pageable pageable) {
        Pageable sortedByIdDesc = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(),
            Sort.by("attributeOptionId").descending()
        );
        return attributeOptionRepository.findAll(sortedByIdDesc)
                .map(option -> {
                    AttributeDTO attributeDTO = new AttributeDTO(
                            option.getAttribute().getAttributeId(),
                            option.getAttribute().getName()
                    );
                    return new AttributeOptionDTO(
                            option.getAttributeOptionId(),
                            option.getValue(),
                            List.of(attributeDTO)
                    );
                });
    }

    /**
     * Xóa một AttributeOption theo ID
     * @param id ID của AttributeOption cần xóa
     * @throws RuntimeException Nếu không tìm thấy hoặc có lỗi
     */
    public void deleteAttributeOption(int id) {
        Optional<AttributeOption> optionOpt = attributeOptionRepository.findById(id);
        if (optionOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy AttributeOption với ID: " + id);
        }

        try {
            attributeOptionRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Không thể xóa AttributeOption vì có dữ liệu liên quan: " + e.getMessage());
        }
    }
}