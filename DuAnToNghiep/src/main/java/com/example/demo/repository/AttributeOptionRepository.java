//package com.example.demo.repository;
//
//import com.example.demo.entity.AttributeOption;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface AttributeOptionRepository extends JpaRepository<AttributeOption, Integer> {
//    
//	List<AttributeOption> findAll(); // Phương thức này đã có sẵn từ JpaRepository
////	
//}

package com.example.demo.repository;

import com.example.demo.entity.Attribute;
import com.example.demo.entity.AttributeOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeOptionRepository extends JpaRepository<AttributeOption, Integer> {
    
    List<AttributeOption> findAll(); // Phương thức này đã có sẵn từ JpaRepository
    
    List<AttributeOption> findByAttribute(Attribute attribute); // Nếu bạn muốn tìm theo đối tượng Attribute

    List<AttributeOption> findByAttribute_AttributeId(Integer attributeId); // Tìm theo attributeId
    
 // Khôi phục phương thức để tìm AttributeOption theo value (không phân biệt hoa/thường) và attributeId
    Optional<AttributeOption> findByValueIgnoreCaseAndAttribute_AttributeId(String value, Integer attributeId);
}