package com.example.demo.util;

import com.example.demo.entity.Voucher;
import com.example.demo.dto.VoucherDTO;

public class VoucherMapper {
    // Chuyển từ Voucher entity sang VoucherDTO
    public static VoucherDTO toDto(Voucher voucher) {
        if (voucher == null) {
            return null;
        }
        return new VoucherDTO(
            voucher.getVoucherId(),
            voucher.getDiscountType(),
            voucher.getMinimumMoneyValue(),
            voucher.getMaxDiscountValue(),
            voucher.getStartDate(),
            voucher.getLateDate()
        );
    }

    // Chuyển từ VoucherDTO sang Voucher entity
    public static Voucher toEntity(VoucherDTO voucherDTO) {
        if (voucherDTO == null) {
            return null;
        }
        Voucher voucher = new Voucher();
        voucher.setVoucherId(voucherDTO.getVoucherId());
        voucher.setDiscountType(voucherDTO.getDiscountType());
        voucher.setMinimumMoneyValue(voucherDTO.getMinimumMoneyValue());
        voucher.setMaxDiscountValue(voucherDTO.getMaxDiscountValue());
        voucher.setStartDate(voucherDTO.getStartDate());
        voucher.setLateDate(voucherDTO.getLateDate());
        return voucher;
    }
}

