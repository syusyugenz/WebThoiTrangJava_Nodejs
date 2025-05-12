package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    // Lấy tất cả người dùng dưới dạng UserRegisterDTO
    public List<UserRegisterDTO> getAllUsersAsDTO() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Chuyển đổi User thành UserRegisterDTO
    private UserRegisterDTO convertToDTO(User user) {
        return new UserRegisterDTO(
            user.getUserId(),
            user.getUsername(),
            user.getPassword(),
            user.getEmail(),
            user.getRole(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getPoint()
        );
    }

    // Phương thức lưu địa chỉ
    public Address saveAddress(AddressDTO addressDTO, int userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        
        Address address = new Address();
        address.setProvinceId(addressDTO.getProvinceId());
        address.setProvinceName(addressDTO.getProvinceName());
        address.setDistrictId(addressDTO.getDistrictId());
        address.setDistrictName(addressDTO.getDistrictName());
        address.setWardId(addressDTO.getWardId());
        address.setWardName(addressDTO.getWardName());
        address.setAddressLine(addressDTO.getAddressLine());
        address.setReceiverPhone(addressDTO.getReceiverPhone()); // Đổi từ int sang String
        address.setReceiverName(addressDTO.getReceiverName());
        address.setUser(user);

        return addressRepository.save(address);
    }

    // Phương thức lấy tất cả địa chỉ
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // Phương thức cập nhật địa chỉ
    public Address updateAddress(int addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setProvinceId(addressDTO.getProvinceId());
        address.setProvinceName(addressDTO.getProvinceName());
        address.setDistrictId(addressDTO.getDistrictId());
        address.setDistrictName(addressDTO.getDistrictName());
        address.setWardId(addressDTO.getWardId());
        address.setWardName(addressDTO.getWardName());
        address.setAddressLine(addressDTO.getAddressLine());
        address.setReceiverPhone(addressDTO.getReceiverPhone()); // Đổi từ int sang String
        address.setReceiverName(addressDTO.getReceiverName());

        return addressRepository.save(address); // Cập nhật địa chỉ vào database
    }
    
    public List<Address> getAddressesByUserId(int userId) {
        List<Address> addresses = addressRepository.findByUser_UserId(userId);
        System.out.println("Số địa chỉ tìm thấy cho userId " + userId + ": " + addresses.size()); // Logging số lượng địa chỉ
        return addresses;
    }
    
    // Phương thức xóa địa chỉ
    public void deleteAddress(int addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressRepository.delete(address);
    }
}