package com.example.demo.controller;

import com.example.demo.dto.AttributeDTO;
import com.example.demo.dto.AttributeOptionDTO;
import com.example.demo.service.AttributeOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/attribute-options")
public class AttributeOptionController {

    @Autowired
    private AttributeOptionService attributeOptionService;

    /**
     * Lấy tất cả các Attribute
     * @return Danh sách Attribute
     */
    @GetMapping("/attributes")
    public List<AttributeDTO> getAttributes() {
        return attributeOptionService.getAllAttributes();
    }

    /**
     * Tạo một AttributeOption mới
     * @param attributeOptionDTO DTO chứa thông tin AttributeOption
     * @return AttributeOption đã tạo
     */
    @PostMapping
    public ResponseEntity<?> createAttributeOption(@RequestBody AttributeOptionDTO attributeOptionDTO) {
        try {
            AttributeOptionDTO createdOption = attributeOptionService.addAttributeOption(attributeOptionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOption);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Lấy tất cả AttributeOptions với phân trang
     * @param pageable Đối tượng chứa thông tin phân trang (page, size)
     * @return Danh sách AttributeOption với thông tin phân trang
     */
    @GetMapping
    public ResponseEntity<Page<AttributeOptionDTO>> getAllAttributeOptions(Pageable pageable) {
        Page<AttributeOptionDTO> attributeOptionPage = attributeOptionService.getAllAttributeOptions(pageable);
        return ResponseEntity.ok(attributeOptionPage);
    }

    /**
     * Cập nhật một AttributeOption
     * @param id ID của AttributeOption
     * @param attributeOptionDTO DTO chứa thông tin mới
     * @return AttributeOption đã cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttributeOption(@PathVariable int id, @RequestBody AttributeOptionDTO attributeOptionDTO) {
        try {
            AttributeOptionDTO updatedOption = attributeOptionService.updateAttributeOption(id, attributeOptionDTO);
            return ResponseEntity.ok(updatedOption);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Xóa một AttributeOption
     * @param id ID của AttributeOption cần xóa
     * @return Thông báo kết quả
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttributeOption(@PathVariable int id) {
        try {
            attributeOptionService.deleteAttributeOption(id);
            return ResponseEntity.ok("Xóa AttributeOption thành công!");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Không tìm thấy")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}