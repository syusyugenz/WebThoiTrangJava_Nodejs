//package com.example.demo.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.example.demo.entity.User;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Integer> {
//	User findByUsername(String username);
//
//	User findByEmail(String email);
//	
//	User findByUserId(int userId);
//	
//	List<User> findAll();
//	
//	Optional<User> findById(int userId); // Thêm phương thức này
//	
//	// Thêm phương thức kiểm tra email tồn tại
//    boolean existsByEmail(String email);
//}

package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByUserId(int userId);

    List<User> findAll();

    Optional<User> findById(int userId);

    // Thêm phương thức kiểm tra email tồn tại
    boolean existsByEmail(String email);

    // Thêm phương thức tìm kiếm theo username hoặc email với phân trang
    Page<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email, Pageable pageable);
}