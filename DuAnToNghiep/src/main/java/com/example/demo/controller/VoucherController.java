////package com.example.demo.controller;
////
////import com.example.demo.dto.VoucherDTO;
////import com.example.demo.service.VoucherService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.text.SimpleDateFormat;
////import java.util.Date;
////import java.util.List;
////
////@RestController
////@CrossOrigin(origins = "*")
////@RequestMapping("/api/vouchers") // Base path cho API vouchers
////public class VoucherController {
////
////    @Autowired
////    private VoucherService voucherService;
////
////    // 1. Tạo một voucher mới
////    @PostMapping
////    public ResponseEntity<VoucherDTO> createVoucher(@RequestBody VoucherDTO voucherDTO) {
////        VoucherDTO createdVoucher = voucherService.createVoucher(voucherDTO);
////        return ResponseEntity.ok(createdVoucher);
////    }
////
////    // 2. Cập nhật thông tin một voucher
////    @PutMapping("/{id}")
////    public ResponseEntity<VoucherDTO> updateVoucher(@PathVariable int id, @RequestBody VoucherDTO voucherDTO) {
////        VoucherDTO updatedVoucher = voucherService.updateVoucher(id, voucherDTO);
////        return ResponseEntity.ok(updatedVoucher);
////    }
////
////    // 3. Xóa một voucher
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deleteVoucher(@PathVariable int id) {
////        voucherService.deleteVoucher(id);
////        return ResponseEntity.ok().build(); // Trả về 200 OK khi xóa thành công
////    }
////
////    // 4. Lấy danh sách tất cả các vouchers
////    @GetMapping
////    public ResponseEntity<List<VoucherDTO>> getAllVouchers() {
////        List<VoucherDTO> vouchers = voucherService.getAllVouchers();
////        return ResponseEntity.ok(vouchers);
////    }
////
////    // 5. Lấy thông tin một voucher theo ID
////    @GetMapping("/{id}")
////    public ResponseEntity<VoucherDTO> getVoucherById(@PathVariable int id) {
////        VoucherDTO voucher = voucherService.getVoucherById(id);
////        return ResponseEntity.ok(voucher);
////    }
////
////    // 6. Kiểm tra voucher có thể sử dụng cho order không
////    @GetMapping("/{id}/usable")
////    public ResponseEntity<Boolean> isVoucherUsableForOrder(@PathVariable int id) {
////        boolean isUsable = voucherService.isVoucherUsableForOrder(id);
////        return ResponseEntity.ok(isUsable);
////    }
////
////    // 7. Lấy trạng thái và thông báo chi tiết của voucher
////    @GetMapping("/{id}/status")
////    public ResponseEntity<StatusResponse> getVoucherStatus(@PathVariable int id) {
////        VoucherDTO voucher = voucherService.getVoucherById(id);
////        Date currentDate = new Date();
////        Date startDate = voucher.getStartDate();
////        Date lateDate = voucher.getLateDate();
////
////        String message;
////        if (startDate == null || lateDate == null) {
////            message = "Voucher không hợp lệ (ngày bắt đầu hoặc ngày kết thúc không được để trống).";
////        } else {
////            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
////            if (currentDate.before(startDate)) {
////                message = "Không thể sử dụng vì chưa đến ngày: " + dateFormat.format(startDate);
////            } else if (currentDate.after(lateDate)) {
////                message = "Không thể sử dụng vì đã hết hạn: " + dateFormat.format(lateDate);
////            } else {
////                message = "Voucher có thể sử dụng.";
////            }
////        }
////
////        return ResponseEntity.ok(new StatusResponse(message));
////    }
////
////    // Class để trả về thông báo trạng thái
////    public static class StatusResponse {
////        private String message;
////
////        public StatusResponse(String message) {
////            this.message = message;
////        }
////
////        public String getMessage() {
////            return message;
////        }
////
////        public void setMessage(String message) {
////            this.message = message;
////        }
////    }
////}
//
//package com.example.demo.controller;
//
//import com.example.demo.dto.VoucherDTO;
//import com.example.demo.service.VoucherService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@RestController
//@CrossOrigin(origins = "*")
//@RequestMapping("/api/vouchers")
//public class VoucherController {
//
//    @Autowired
//    private VoucherService voucherService;
//
//    // 1. Tạo một voucher mới
//    @PostMapping
//    public ResponseEntity<VoucherDTO> createVoucher(@RequestBody VoucherDTO voucherDTO) {
//        VoucherDTO createdVoucher = voucherService.createVoucher(voucherDTO);
//        return ResponseEntity.ok(createdVoucher);
//    }
//
//    // 2. Cập nhật thông tin một voucher
//    @PutMapping("/{id}")
//    public ResponseEntity<VoucherDTO> updateVoucher(@PathVariable int id, @RequestBody VoucherDTO voucherDTO) {
//        VoucherDTO updatedVoucher = voucherService.updateVoucher(id, voucherDTO);
//        return ResponseEntity.ok(updatedVoucher);
//    }
//
//    // 3. Xóa một voucher
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteVoucher(@PathVariable int id) {
//        voucherService.deleteVoucher(id);
//        return ResponseEntity.ok().build();
//    }
//
//    // 4. Lấy danh sách vouchers với phân trang
//    @GetMapping
//    public ResponseEntity<Page<VoucherDTO>> getAllVouchers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        Page<VoucherDTO> vouchers = voucherService.getAllVouchers(page, size);
//        return ResponseEntity.ok(vouchers);
//    }
//
//    // 5. Lấy thông tin một voucher theo ID
//    @GetMapping("/{id}")
//    public ResponseEntity<VoucherDTO> getVoucherById(@PathVariable int id) {
//        VoucherDTO voucher = voucherService.getVoucherById(id);
//        return ResponseEntity.ok(voucher);
//    }
//
//    // 6. Kiểm tra voucher có thể sử dụng cho order không
//    @GetMapping("/{id}/usable")
//    public ResponseEntity<Boolean> isVoucherUsableForOrder(@PathVariable int id) {
//        boolean isUsable = voucherService.isVoucherUsableForOrder(id);
//        return ResponseEntity.ok(isUsable);
//    }
//
//    // 7. Lấy trạng thái và thông báo chi tiết của voucher
//    @GetMapping("/{id}/status")
//    public ResponseEntity<StatusResponse> getVoucherStatus(@PathVariable int id) {
//        VoucherDTO voucher = voucherService.getVoucherById(id);
//        Date currentDate = new Date();
//        Date startDate = voucher.getStartDate();
//        Date lateDate = voucher.getLateDate();
//
//        String message;
//        if (startDate == null || lateDate == null) {
//            message = "Voucher không hợp lệ (ngày bắt đầu hoặc ngày kết thúc không được để trống).";
//        } else {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            if (currentDate.before(startDate)) {
//                message = "Không thể sử dụng vì chưa đến ngày: " + dateFormat.format(startDate);
//            } else if (currentDate.after(lateDate)) {
//                message = "Không thể sử dụng vì đã hết hạn: " + dateFormat.format(lateDate);
//            } else {
//                message = "Voucher có thể sử dụng.";
//            }
//        }
//
//        return ResponseEntity.ok(new StatusResponse(message));
//    }
//
//    // Class để trả về thông báo trạng thái
//    public static class StatusResponse {
//        private String message;
//
//        public StatusResponse(String message) {
//            this.message = message;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//    }
//}

package com.example.demo.controller;

import com.example.demo.dto.VoucherDTO;
import com.example.demo.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PostMapping
    public ResponseEntity<VoucherDTO> createVoucher(@RequestBody VoucherDTO voucherDTO) {
        VoucherDTO createdVoucher = voucherService.createVoucher(voucherDTO);
        return ResponseEntity.ok(createdVoucher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherDTO> updateVoucher(@PathVariable int id, @RequestBody VoucherDTO voucherDTO) {
        VoucherDTO updatedVoucher = voucherService.updateVoucher(id, voucherDTO);
        return ResponseEntity.ok(updatedVoucher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable int id) {
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<VoucherDTO>> getAllVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<VoucherDTO> vouchers = voucherService.getAllVouchers(page, size);
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherDTO> getVoucherById(@PathVariable int id) {
        VoucherDTO voucher = voucherService.getVoucherById(id);
        return ResponseEntity.ok(voucher);
    }

    @GetMapping("/{id}/usable")
    public ResponseEntity<Boolean> isVoucherUsableForOrder(@PathVariable int id) {
        boolean isUsable = voucherService.isVoucherUsableForOrder(id);
        return ResponseEntity.ok(isUsable);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<StatusResponse> getVoucherStatus(@PathVariable int id) {
        VoucherDTO voucher = voucherService.getVoucherById(id);
        Date currentDate = new Date();
        Date startDate = voucher.getStartDate();
        Date lateDate = voucher.getLateDate();

        String message;
        if (startDate == null || lateDate == null) {
            message = "Voucher không hợp lệ (ngày bắt đầu hoặc ngày kết thúc không được để trống).";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (currentDate.before(startDate)) {
                message = "Không thể sử dụng vì chưa đến ngày: " + dateFormat.format(startDate);
            } else if (currentDate.after(lateDate)) {
                message = "Không thể sử dụng vì đã hết hạn: " + dateFormat.format(lateDate);
            } else {
                message = "Voucher có thể sử dụng.";
            }
        }

        return ResponseEntity.ok(new StatusResponse(message));
    }

    public static class StatusResponse {
        private String message;

        public StatusResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}