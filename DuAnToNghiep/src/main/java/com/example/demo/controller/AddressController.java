package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.entity.Address;
import com.example.demo.service.AddressService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Phương thức thêm địa chỉ
    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestBody AddressDTO addressDTO, @RequestParam int userId) {
        Address savedAddress = addressService.saveAddress(addressDTO, userId);
        return ResponseEntity.ok(savedAddress);
    }

    // Phương thức lấy tất cả địa chỉ
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    // Phương thức lấy tất cả người dùng dưới dạng UserRegisterDTO
    @GetMapping("/users")
    public ResponseEntity<List<UserRegisterDTO>> getAllUsers() {
        List<UserRegisterDTO> users = addressService.getAllUsersAsDTO();
        return ResponseEntity.ok(users);
    }

    // Phương thức cập nhật địa chỉ
    @PutMapping("/update/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable int addressId, @RequestBody AddressDTO addressDTO) {
        Address updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable int userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }
    
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable int addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok("Địa chỉ đã được xóa thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xóa địa chỉ thất bại: " + e.getMessage());
        }
    }
}