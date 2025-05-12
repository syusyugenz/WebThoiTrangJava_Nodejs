package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Voucher;


@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    // Custom query methods can be added here
}