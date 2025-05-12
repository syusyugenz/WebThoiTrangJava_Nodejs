//package com.example.demo.controller;
//
//import com.example.demo.dto.AttributeDTO;
//import com.example.demo.service.AttributeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "*")
//@RequestMapping("/api/attributes")
//public class AttributeController {
//
//    @Autowired
//    private AttributeService attributeService;
//
//    /**
//     * Thêm một thuộc tính mới
//     * @param attributeDTO Thông tin thuộc tính cần thêm
//     * @return ResponseEntity Kết quả (thành công hoặc lỗi)
//     */
//    @PostMapping
//    public ResponseEntity<?> createAttribute(@RequestBody AttributeDTO attributeDTO) {
//        try {
//            AttributeDTO createdAttribute = attributeService.addAttribute(attributeDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdAttribute);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//        }
//    }
//
//    /**
//     * Lấy danh sách tất cả các thuộc tính
//     * @return ResponseEntity Danh sách các thuộc tính
//     */
//    @GetMapping
//    public ResponseEntity<List<AttributeDTO>> getAllAttributes() {
//        List<AttributeDTO> attributeList = attributeService.getAllAttributes();
//        return ResponseEntity.ok(attributeList);
//    }
//
//    /**
//     * Cập nhật thông tin thuộc tính theo ID
//     * @param id ID của thuộc tính cần cập nhật
//     * @param attributeDTO Thông tin mới của thuộc tính
//     * @return ResponseEntity Kết quả (thành công hoặc lỗi)
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateAttribute(@PathVariable int id, @RequestBody AttributeDTO attributeDTO) {
//        try {
//            AttributeDTO updatedAttribute = attributeService.updateAttribute(id, attributeDTO);
//            return ResponseEntity.ok(updatedAttribute);
//        } catch (RuntimeException e) {
//            if (e.getMessage().contains("Không tìm thấy")) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//            }
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//        }
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getAttributeById(@PathVariable int id) {
//        try {
//            AttributeDTO attribute = attributeService.getAttributeById(id);
//            return ResponseEntity.ok(attribute);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
//    /**
//     * Xóa thuộc tính theo ID
//     * @param id ID của thuộc tính cần xóa
//     * @return ResponseEntity Kết quả (thành công hoặc lỗi)
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteAttribute(@PathVariable int id) {
//        try {
//            attributeService.deleteAttribute(id);
//            return ResponseEntity.ok("Xóa thuộc tính thành công!");
//        } catch (RuntimeException e) {
//            if (e.getMessage().contains("Không tìm thấy")) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//            }
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//        }
//    }
//}
//


package com.example.demo.controller;

import com.example.demo.dto.AttributeDTO;
import com.example.demo.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/attributes")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    /**
     * Thêm một thuộc tính mới
     * @param attributeDTO Thông tin thuộc tính cần thêm
     * @return ResponseEntity Kết quả (thành công hoặc lỗi)
     */
    @PostMapping
    public ResponseEntity<?> createAttribute(@RequestBody AttributeDTO attributeDTO) {
        try {
            AttributeDTO createdAttribute = attributeService.addAttribute(attributeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAttribute);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Lấy danh sách tất cả các thuộc tính với phân trang
     * @param pageable Đối tượng chứa thông tin phân trang (page, size)
     * @return ResponseEntity Danh sách các thuộc tính với thông tin phân trang
     */
    @GetMapping
    public ResponseEntity<Page<AttributeDTO>> getAllAttributes(Pageable pageable) {
        Page<AttributeDTO> attributePage = attributeService.getAllAttributes(pageable);
        return ResponseEntity.ok(attributePage);
    }

    /**
     * Cập nhật thông tin thuộc tính theo ID
     * @param id ID của thuộc tính cần cập nhật
     * @param attributeDTO Thông tin mới của thuộc tính
     * @return ResponseEntity Kết quả (thành công hoặc lỗi)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttribute(@PathVariable int id, @RequestBody AttributeDTO attributeDTO) {
        try {
            AttributeDTO updatedAttribute = attributeService.updateAttribute(id, attributeDTO);
            return ResponseEntity.ok(updatedAttribute);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Không tìm thấy")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Lấy thuộc tính theo ID
     * @param id ID của thuộc tính
     * @return ResponseEntity Kết quả (thành công hoặc lỗi)
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAttributeById(@PathVariable int id) {
        try {
            AttributeDTO attribute = attributeService.getAttributeById(id);
            return ResponseEntity.ok(attribute);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Xóa thuộc tính theo ID
     * @param id ID của thuộc tính cần xóa
     * @return ResponseEntity Kết quả (thành công hoặc lỗi)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttribute(@PathVariable int id) {
        try {
            attributeService.deleteAttribute(id);
            return ResponseEntity.ok("Xóa thuộc tính thành công!");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Không tìm thấy")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}