////package com.example.demo.service;
////
////import com.example.demo.dto.VoucherDTO;
////import com.example.demo.entity.Voucher;
////import com.example.demo.repository.VoucherRepository;
////import com.example.demo.util.VoucherMapper;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////
////import java.util.Date;
////import java.util.List;
////import java.util.Optional;
////import java.util.stream.Collectors;
////
////@Service
////public class VoucherService {
////
////    @Autowired
////    private VoucherRepository voucherRepository;
////
////    // 1. Tạo voucher mới
////    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
////        Voucher voucher = VoucherMapper.toEntity(voucherDTO);
////        Voucher savedVoucher = voucherRepository.save(voucher);
////        return VoucherMapper.toDto(savedVoucher);
////    }
////
////    // 2. Cập nhật voucher
////    public VoucherDTO updateVoucher(int voucherId, VoucherDTO voucherDTO) {
////        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
////        if (optionalVoucher.isPresent()) {
////            Voucher existingVoucher = optionalVoucher.get();
////            existingVoucher.setDiscountType(voucherDTO.getDiscountType());
////            existingVoucher.setMinimumMoneyValue(voucherDTO.getMinimumMoneyValue());
////            existingVoucher.setMaxDiscountValue(voucherDTO.getMaxDiscountValue());
////            existingVoucher.setStartDate(voucherDTO.getStartDate()); // Trực tiếp dùng Date
////            existingVoucher.setLateDate(voucherDTO.getLateDate());   // Trực tiếp dùng Date
////
////            Voucher updatedVoucher = voucherRepository.save(existingVoucher);
////            return VoucherMapper.toDto(updatedVoucher);
////        }
////        throw new RuntimeException("Voucher không tồn tại với ID: " + voucherId);
////    }
////
////    // 3. Xóa voucher
////    public void deleteVoucher(int voucherId) {
////        if (voucherRepository.existsById(voucherId)) {
////            voucherRepository.deleteById(voucherId);
////        } else {
////            throw new RuntimeException("Voucher không tồn tại với ID: " + voucherId);
////        }
////    }
////
////    // 4. Lấy danh sách tất cả các vouchers (trả về tất cả, kể cả chưa đến startDate)
////    public List<VoucherDTO> getAllVouchers() {
////        List<Voucher> vouchers = voucherRepository.findAll();
////        return vouchers.stream()
////                .map(VoucherMapper::toDto)
////                .collect(Collectors.toList());
////    }
////
////    // 5. Lấy voucher theo ID (trả về tất cả, nhưng kiểm tra khi sử dụng)
////    public VoucherDTO getVoucherById(int voucherId) {
////        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
////        return optionalVoucher.map(VoucherMapper::toDto)
////                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại với ID: " + voucherId));
////    }
////
////    // 6. Phương thức kiểm tra voucher có hợp lệ để sử dụng không (chỉ dùng khi apply voucher)
////    public boolean isVoucherUsableForOrder(int voucherId) {
////        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
////        if (optionalVoucher.isPresent()) {
////            Voucher voucher = optionalVoucher.get();
////            Date currentDate = new Date(); // Lấy ngày hiện tại
////            Date startDate = voucher.getStartDate();
////            Date lateDate = voucher.getLateDate();
////
////            // Kiểm tra voucher hợp lệ: đã đến startDate và chưa qua lateDate
////            if (startDate == null || lateDate == null) {
////                return false;
////            }
////            return !currentDate.before(startDate) && !currentDate.after(lateDate);
////        }
////        return false; // Voucher không tồn tại
////    }
////}
//
//package com.example.demo.service;
//
//import com.example.demo.dto.VoucherDTO;
//import com.example.demo.entity.Voucher;
//import com.example.demo.repository.VoucherRepository;
//import com.example.demo.util.VoucherMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.Optional;
//
//@Service
//public class VoucherService {
//
//    @Autowired
//    private VoucherRepository voucherRepository;
//
//    // 1. Tạo voucher mới
//    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
//        Voucher voucher = VoucherMapper.toEntity(voucherDTO);
//        Voucher savedVoucher = voucherRepository.save(voucher);
//        return VoucherMapper.toDto(savedVoucher);
//    }
//
//    // 2. Cập nhật voucher
//    public VoucherDTO updateVoucher(int voucherId, VoucherDTO voucherDTO) {
//        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
//        if (optionalVoucher.isPresent()) {
//            Voucher existingVoucher = optionalVoucher.get();
//            existingVoucher.setDiscountType(voucherDTO.getDiscountType());
//            existingVoucher.setMinimumMoneyValue(voucherDTO.getMinimumMoneyValue());
//            existingVoucher.setMaxDiscountValue(voucherDTO.getMaxDiscountValue());
//            existingVoucher.setStartDate(voucherDTO.getStartDate());
//            existingVoucher.setLateDate(voucherDTO.getLateDate());
//
//            Voucher updatedVoucher = voucherRepository.save(existingVoucher);
//            return VoucherMapper.toDto(updatedVoucher);
//        }
//        throw new RuntimeException("Voucher không tồn tại với ID: " + voucherId);
//    }
//
//    // 3. Xóa voucher
//    public void deleteVoucher(int voucherId) {
//        if (voucherRepository.existsById(voucherId)) {
//            voucherRepository.deleteById(voucherId);
//        } else {
//            throw new RuntimeException("Voucher không tồn tại với ID: " + voucherId);
//        }
//    }
//
//    // 4. Lấy danh sách vouchers với phân trang và sắp xếp
//    public Page<VoucherDTO> getAllVouchers(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("voucherId").descending());
//        Page<Voucher> vouchers = voucherRepository.findAll(pageable);
//        return vouchers.map(VoucherMapper::toDto);
//    }
//
//    // 5. Lấy voucher theo ID
//    public VoucherDTO getVoucherById(int voucherId) {
//        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
//        return optionalVoucher.map(VoucherMapper::toDto)
//                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại với ID: " + voucherId));
//    }
//
//    // 6. Kiểm tra voucher có hợp lệ để sử dụng không
//    public boolean isVoucherUsableForOrder(int voucherId) {
//        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
//        if (optionalVoucher.isPresent()) {
//            Voucher voucher = optionalVoucher.get();
//            Date currentDate = new Date();
//            Date startDate = voucher.getStartDate();
//            Date lateDate = voucher.getLateDate();
//
//            if (startDate == null || lateDate == null) {
//                return false;
//            }
//            return !currentDate.before(startDate) && !currentDate.after(lateDate);
//        }
//        return false;
//    }
//}

package com.example.demo.service;

import com.example.demo.dto.VoucherDTO;
import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.util.VoucherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
        Voucher voucher = VoucherMapper.toEntity(voucherDTO);
        Voucher savedVoucher = voucherRepository.save(voucher);
        return VoucherMapper.toDto(savedVoucher);
    }

    public VoucherDTO updateVoucher(int voucherId, VoucherDTO voucherDTO) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
        if (optionalVoucher.isPresent()) {
            Voucher existingVoucher = optionalVoucher.get();
            existingVoucher.setDiscountType(voucherDTO.getDiscountType());
            existingVoucher.setMinimumMoneyValue(voucherDTO.getMinimumMoneyValue());
            existingVoucher.setMaxDiscountValue(voucherDTO.getMaxDiscountValue());
            existingVoucher.setStartDate(voucherDTO.getStartDate());
            existingVoucher.setLateDate(voucherDTO.getLateDate());

            Voucher updatedVoucher = voucherRepository.save(existingVoucher);
            return VoucherMapper.toDto(updatedVoucher);
        }
        throw new RuntimeException("Voucher không tồn tại với ID: " + voucherId);
    }

    public void deleteVoucher(int voucherId) {
        if (voucherRepository.existsById(voucherId)) {
            voucherRepository.deleteById(voucherId);
        } else {
            throw new RuntimeException("Voucher không tồn tại với ID: " + voucherId);
        }
    }

    public Page<VoucherDTO> getAllVouchers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("voucherId").descending());
        Page<Voucher> vouchers = voucherRepository.findAll(pageable);
        return vouchers.map(VoucherMapper::toDto);
    }

    public VoucherDTO getVoucherById(int voucherId) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
        return optionalVoucher.map(VoucherMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại với ID: " + voucherId));
    }

    public boolean isVoucherUsableForOrder(int voucherId) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
        if (optionalVoucher.isPresent()) {
            Voucher voucher = optionalVoucher.get();
            Date currentDate = new Date();
            Date startDate = voucher.getStartDate();
            Date lateDate = voucher.getLateDate();

            if (startDate == null || lateDate == null) {
                return false;
            }
            return !currentDate.before(startDate) && !currentDate.after(lateDate);
        }
        return false;
    }
}