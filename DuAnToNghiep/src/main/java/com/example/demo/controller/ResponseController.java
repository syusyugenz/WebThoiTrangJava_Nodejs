package com.example.demo.controller;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responses")
@CrossOrigin(origins = "*")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @GetMapping
    public ResponseEntity<PagedResponseDTO<ResponseDTO>> getAllResponses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PagedResponseDTO<ResponseDTO> responses = responseService.getAllResponses(page, size);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getResponseById(@PathVariable("id") int responseId) {
        ResponseDTO responseDTO = responseService.getResponseById(responseId);
        return responseDTO != null ? ResponseEntity.ok(responseDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ResponseDTO> getResponseByReviewId(@PathVariable("reviewId") int reviewId) {
        ResponseDTO responseDTO = responseService.getResponseByReviewId(reviewId);
        return responseDTO != null ? ResponseEntity.ok(responseDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addResponse(@RequestBody ResponseDTO responseDTO) {
        ResponseDTO createdResponse = responseService.addResponse(responseDTO);
        return ResponseEntity.ok(createdResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateResponse(
            @PathVariable("id") int responseId,
            @RequestBody ResponseDTO responseDTO) {
        ResponseDTO updatedResponse = responseService.updateResponse(responseId, responseDTO);
        return updatedResponse != null ? ResponseEntity.ok(updatedResponse) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponse(@PathVariable("id") int responseId) {
        responseService.deleteResponse(responseId);
        return ResponseEntity.noContent().build();
    }
}