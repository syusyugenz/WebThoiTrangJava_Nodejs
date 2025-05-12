package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    // Custom query methods can be added here
	List<Address> findByUser_UserId(int userId); // Sử dụng đúng tên thuộc tính
}