//package com.example.demo.service;
//
//import com.example.demo.dto.AttributeDTO;
//import com.example.demo.entity.Attribute;
//import com.example.demo.repository.AttributeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class AttributeService {
//
//    @Autowired
//    private AttributeRepository attributeRepository;
//
//    public AttributeDTO getAttributeById(int id) {
//        Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
//        if (optionalAttribute.isPresent()) {
//            Attribute attribute = optionalAttribute.get();
//            return new AttributeDTO(attribute.getAttributeId(), attribute.getName());
//        } else {
//            throw new RuntimeException("Không tìm thấy thuộc tính với ID: " + id);
//        }
//    }
//    /**
//     * Thêm một thuộc tính mới
//     * @param attributeDTO Đối tượng DTO chứa thông tin thuộc tính
//     * @return AttributeDTO Đối tượng thuộc tính sau khi được lưu
//     * @throws RuntimeException Nếu tên thuộc tính đã tồn tại
//     */
//    public AttributeDTO addAttribute(AttributeDTO attributeDTO) {
//        // Chuẩn hóa tên (loại bỏ khoảng trắng thừa)
//        String normalizedName = normalizeName(attributeDTO.getName());
//
//        // Kiểm tra xem tên đã tồn tại chưa (không phân biệt hoa/thường)
//        Optional<Attribute> existingAttribute = attributeRepository.findByNameIgnoreCase(normalizedName);
//        if (existingAttribute.isPresent()) {
//            throw new RuntimeException("Tên thuộc tính '" + attributeDTO.getName() + "' đã tồn tại!");
//        }
//
//        // Tạo và lưu thuộc tính mới
//        Attribute attribute = new Attribute();
//        attribute.setName(normalizedName);
//
//        try {
//            Attribute savedAttribute = attributeRepository.save(attribute);
//            return new AttributeDTO(savedAttribute.getAttributeId(), savedAttribute.getName());
//        } catch (DataIntegrityViolationException e) {
//            throw new RuntimeException("Lỗi khi lưu thuộc tính: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Lấy danh sách tất cả các thuộc tính
//     * @return List<AttributeDTO> Danh sách các thuộc tính
//     */
//    public List<AttributeDTO> getAllAttributes() {
//        return attributeRepository.findAll()
//                .stream()
//                .map(attr -> new AttributeDTO(attr.getAttributeId(), attr.getName()))
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Cập nhật thông tin thuộc tính theo ID
//     * @param id ID của thuộc tính cần cập nhật
//     * @param attributeDTO Đối tượng DTO chứa thông tin mới
//     * @return AttributeDTO Đối tượng thuộc tính sau khi cập nhật
//     * @throws RuntimeException Nếu không tìm thấy thuộc tính hoặc tên đã tồn tại
//     */
//    public AttributeDTO updateAttribute(int id, AttributeDTO attributeDTO) {
//        // Tìm thuộc tính theo ID
//        Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
//        if (optionalAttribute.isEmpty()) {
//            throw new RuntimeException("Không tìm thấy thuộc tính với ID: " + id);
//        }
//
//        Attribute attribute = optionalAttribute.get();
//        String normalizedNewName = normalizeName(attributeDTO.getName());
//
//        // Kiểm tra trùng lặp, bỏ qua chính bản ghi đang chỉnh sửa
//        Optional<Attribute> existingAttribute = attributeRepository.findByNameIgnoreCase(normalizedNewName);
//        if (existingAttribute.isPresent() && existingAttribute.get().getAttributeId() != id) {
//            throw new RuntimeException("Tên thuộc tính '" + attributeDTO.getName() + "' đã tồn tại!");
//        }
//
//        // Cập nhật tên và lưu
//        attribute.setName(normalizedNewName);
//        try {
//            Attribute updatedAttribute = attributeRepository.save(attribute);
//            return new AttributeDTO(updatedAttribute.getAttributeId(), updatedAttribute.getName());
//        } catch (DataIntegrityViolationException e) {
//            throw new RuntimeException("Lỗi khi cập nhật thuộc tính: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Xóa thuộc tính theo ID
//     * @param id ID của thuộc tính cần xóa
//     * @throws RuntimeException Nếu không tìm thấy thuộc tính hoặc có lỗi liên quan
//     */
//    public void deleteAttribute(int id) {
//        Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
//        if (optionalAttribute.isEmpty()) {
//            throw new RuntimeException("Không tìm thấy thuộc tính với ID: " + id);
//        }
//
//        try {
//            attributeRepository.deleteById(id);
//        } catch (DataIntegrityViolationException e) {
//            throw new RuntimeException("Không thể xóa thuộc tính vì có dữ liệu liên quan!");
//        }
//    }
//
//    /**
//     * Chuẩn hóa tên thuộc tính (loại bỏ khoảng trắng thừa)
//     * @param name Tên cần chuẩn hóa
//     * @return String Tên đã được chuẩn hóa
//     */
//    private String normalizeName(String name) {
//        return (name != null) ? name.trim() : ""; // Bỏ .toLowerCase()
//    }
//
//
//}

package com.example.demo.service;

import com.example.demo.dto.AttributeDTO;
import com.example.demo.entity.Attribute;
import com.example.demo.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    public AttributeDTO getAttributeById(int id) {
        Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
        if (optionalAttribute.isPresent()) {
            Attribute attribute = optionalAttribute.get();
            return new AttributeDTO(attribute.getAttributeId(), attribute.getName());
        } else {
            throw new RuntimeException("Không tìm thấy thuộc tính với ID: " + id);
        }
    }

    /**
     * Thêm một thuộc tính mới
     * @param attributeDTO Đối tượng DTO chứa thông tin thuộc tính
     * @return AttributeDTO Đối tượng thuộc tính sau khi được lưu
     * @throws RuntimeException Nếu tên thuộc tính đã tồn tại
     */
    public AttributeDTO addAttribute(AttributeDTO attributeDTO) {
        String normalizedName = normalizeName(attributeDTO.getName());
        Optional<Attribute> existingAttribute = attributeRepository.findByNameIgnoreCase(normalizedName);
        if (existingAttribute.isPresent()) {
            throw new RuntimeException("Tên thuộc tính '" + attributeDTO.getName() + "' đã tồn tại!");
        }
        Attribute attribute = new Attribute();
        attribute.setName(normalizedName);
        try {
            Attribute savedAttribute = attributeRepository.save(attribute);
            return new AttributeDTO(savedAttribute.getAttributeId(), savedAttribute.getName());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Lỗi khi lưu thuộc tính: " + e.getMessage());
        }
    }

    /**
     * Lấy danh sách tất cả các thuộc tính với phân trang, sắp xếp mới nhất trước
     * @param pageable Đối tượng chứa thông tin phân trang (page, size, sort)
     * @return Page<AttributeDTO> Danh sách các thuộc tính với thông tin phân trang
     */
    public Page<AttributeDTO> getAllAttributes(Pageable pageable) {
        // Mặc định sắp xếp theo attributeId giảm dần (mới nhất trước) và 10 bản ghi mỗi trang
        Pageable sortedByIdDesc = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize() == 0 ? 10 : pageable.getPageSize(), // Mặc định 10 nếu size không được chỉ định
            Sort.by("attributeId").descending()
        );
        return attributeRepository.findAll(sortedByIdDesc)
                .map(attr -> new AttributeDTO(attr.getAttributeId(), attr.getName()));
    }

    /**
     * Cập nhật thông tin thuộc tính theo ID
     * @param id ID của thuộc tính cần cập nhật
     * @param attributeDTO Đối tượng DTO chứa thông tin mới
     * @return AttributeDTO Đối tượng thuộc tính sau khi cập nhật
     * @throws RuntimeException Nếu không tìm thấy thuộc tính hoặc tên đã tồn tại
     */
    public AttributeDTO updateAttribute(int id, AttributeDTO attributeDTO) {
        Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
        if (optionalAttribute.isEmpty()) {
            throw new RuntimeException("Không tìm thấy thuộc tính với ID: " + id);
        }
        Attribute attribute = optionalAttribute.get();
        String normalizedNewName = normalizeName(attributeDTO.getName());
        Optional<Attribute> existingAttribute = attributeRepository.findByNameIgnoreCase(normalizedNewName);
        if (existingAttribute.isPresent() && existingAttribute.get().getAttributeId() != id) {
            throw new RuntimeException("Tên thuộc tính '" + attributeDTO.getName() + "' đã tồn tại!");
        }
        attribute.setName(normalizedNewName);
        try {
            Attribute updatedAttribute = attributeRepository.save(attribute);
            return new AttributeDTO(updatedAttribute.getAttributeId(), updatedAttribute.getName());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Lỗi khi cập nhật thuộc tính: " + e.getMessage());
        }
    }

    /**
     * Xóa thuộc tính theo ID
     * @param id ID của thuộc tính cần xóa
     * @throws RuntimeException Nếu không tìm thấy thuộc tính hoặc có lỗi liên quan
     */
    public void deleteAttribute(int id) {
        Optional<Attribute> optionalAttribute = attributeRepository.findById(id);
        if (optionalAttribute.isEmpty()) {
            throw new RuntimeException("Không tìm thấy thuộc tính với ID: " + id);
        }
        try {
            attributeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Không thể xóa thuộc tính vì có dữ liệu liên quan!");
        }
    }

    /**
     * Chuẩn hóa tên thuộc tính (loại bỏ khoảng trắng thừa)
     * @param name Tên cần chuẩn hóa
     * @return String Tên đã được chuẩn hóa
     */
    private String normalizeName(String name) {
        return (name != null) ? name.trim() : "";
    }
}