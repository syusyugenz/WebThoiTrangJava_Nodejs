//////package com.example.demo.controller;
//////
//////import com.example.demo.dto.CardDTO;
//////import com.example.demo.service.CardService;
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.web.bind.annotation.*;
//////import org.springframework.web.multipart.MultipartFile;
//////
//////import java.io.IOException;
//////import java.util.List;
//////import java.util.Map;
//////
//////@RestController
//////@CrossOrigin(origins = "*") 
//////@RequestMapping("/api")
//////public class CardController {
//////
//////    @Autowired
//////    private CardService cardService;
//////
//////    // API lấy tất cả card với phân trang
//////    @GetMapping("/cards")
//////    public List<CardDTO> getAllCards(
//////            @RequestParam(defaultValue = "1") int page,
//////            @RequestParam(defaultValue = "24") int size) {
//////        return cardService.getAllCardsWithPriceRange(page, size);
//////    }
//////
//////    // API lọc card theo danh mục với phân trang
//////    @GetMapping("/cards/category/{categoryName}")
//////    public List<CardDTO> getCardsByCategory(
//////            @PathVariable String categoryName,
//////            @RequestParam(defaultValue = "1") int page,
//////            @RequestParam(defaultValue = "24") int size) {
//////        return cardService.getCardsByCategory(categoryName, page, size);
//////    }
//////
//////    // API upload ảnh lên Cloudinary
//////    @PostMapping("/upload")
//////    public Map uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
//////        return cardService.uploadImage(file);
//////    }
//////}
////
////package com.example.demo.controller;
////
////import com.example.demo.dto.CardDTO;
////import com.example.demo.service.CardService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.web.bind.annotation.*;
////import org.springframework.web.multipart.MultipartFile;
////
////import java.io.IOException;
////import java.util.List;
////import java.util.Map;
////
////@RestController
////@CrossOrigin(origins = "*") 
////@RequestMapping("/api")
////public class CardController {
////
////    @Autowired
////    private CardService cardService;
////
////    // API lấy tất cả card với phân trang
////    @GetMapping("/cards")
////    public List<CardDTO> getAllCards(
////            @RequestParam(defaultValue = "1") int page,
////            @RequestParam(defaultValue = "24") int size) {
////        return cardService.getAllCardsWithPriceRange(page, size);
////    }
////
////    // API lọc card theo danh mục với phân trang
////    @GetMapping("/cards/category/{categoryName}")
////    public List<CardDTO> getCardsByCategory(
////            @PathVariable String categoryName,
////            @RequestParam(defaultValue = "1") int page,
////            @RequestParam(defaultValue = "24") int size) {
////        return cardService.getCardsByCategory(categoryName, page, size);
////    }
////
////    // API mới: Tìm kiếm theo tên sản phẩm, khoảng giá và danh mục
////    @GetMapping("/cards/search")
////    public List<CardDTO> searchCards(
////            @RequestParam(required = false) String name, // Tên sản phẩm
////            @RequestParam(required = false) String priceRange, // Khoảng giá (ví dụ: "100000-200000")
////            @RequestParam(required = false) String categoryName, // Tên danh mục
////            @RequestParam(defaultValue = "1") int page,
////            @RequestParam(defaultValue = "24") int size) {
////        return cardService.searchCards(name, priceRange, categoryName, page, size);
////    }
////
////    // API upload ảnh lên Cloudinary
////    @PostMapping("/upload")
////    public Map uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
////        return cardService.uploadImage(file);
////    }
////}
//package com.example.demo.controller;
//
//import com.example.demo.dto.CardDTO;
//import com.example.demo.service.CardService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@CrossOrigin(origins = "*")
//@RequestMapping("/api")
//public class CardController {
//
//    @Autowired
//    private CardService cardService;
//
//    /**
//     * Tìm kiếm sản phẩm với các tiêu chí
//     * @param name Tên sản phẩm (tùy chọn)
//     * @param priceRange Khoảng giá (tùy chọn)
//     * @param categoryName Tên danh mục (tùy chọn)
//     * @param page Số trang (mặc định 1)
//     * @param size Số sản phẩm mỗi trang (mặc định 24)
//     * @return List<CardDTO> Danh sách sản phẩm
//     */
//    @GetMapping("/cards/search")
//    public List<CardDTO> searchCards(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) String priceRange,
//            @RequestParam(required = false) String categoryName,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "24") int size) {
//        return cardService.searchCards(name, priceRange, categoryName, page, size);
//    }
//
//    /**
//     * Upload ảnh lên Cloudinary
//     * @param file File ảnh
//     * @return Map chứa thông tin ảnh
//     */
//    @PostMapping("/upload")
//    public Map uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
//        return cardService.uploadImage(file);
//    }
//}

package com.example.demo.controller;

import com.example.demo.dto.CardDTO;
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * Tìm kiếm sản phẩm với các tiêu chí
     * @param name Tên sản phẩm (tùy chọn)
     * @param priceRange Khoảng giá (tùy chọn)
     * @param categoryName Tên danh mục (tùy chọn)
     * @param page Số trang (mặc định 1)
     * @param size Số sản phẩm mỗi trang (mặc định 24)
     * @return PagedResponseDTO<CardDTO> Danh sách sản phẩm kèm thông tin phân trang
     */
    @GetMapping("/cards/search")
    public PagedResponseDTO<CardDTO> searchCards(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String categoryName,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "24") int size) {
        return cardService.searchCards(name, priceRange, categoryName, page, size);
    }

    /**
     * Upload ảnh lên Cloudinary
     * @param file File ảnh
     * @return Map chứa thông tin ảnh
     */
    @PostMapping("/upload")
    public Map uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return cardService.uploadImage(file);
    }
}