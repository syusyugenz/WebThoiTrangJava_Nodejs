package com.example.demo.service;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ReviewDTO;
import com.example.demo.entity.Response;
import com.example.demo.entity.Review;
import com.example.demo.repository.ResponseRepository;
import com.example.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    // Cập nhật phương thức getAllResponses để hỗ trợ phân trang
    public PagedResponseDTO<ResponseDTO> getAllResponses(int page, int size) {
        // Phân trang và sắp xếp theo responseId giảm dần
        Pageable pageable = PageRequest.of(page, size, Sort.by("responseId").descending());
        Page<Response> responsesPage = responseRepository.findAll(pageable);

        // Chuyển đổi thành ResponseDTO
        List<ResponseDTO> responseList = responsesPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Tạo PagedResponseDTO
        return new PagedResponseDTO<>(
                responseList,
                responsesPage.getNumber(),
                responsesPage.getSize(),
                responsesPage.getTotalElements(),
                responsesPage.getTotalPages(),
                responsesPage.isLast()
        );
    }

    public ResponseDTO getResponseById(int responseId) {
        Optional<Response> optionalResponse = responseRepository.findById(responseId);
        return optionalResponse.map(this::convertToDTO).orElse(null);
    }

    public ResponseDTO getResponseByReviewId(int reviewId) {
        Optional<Response> optionalResponse = responseRepository.findByReview_ReviewId(reviewId);
        return optionalResponse.map(this::convertToDTO).orElse(null);
    }

    public ResponseDTO addResponse(ResponseDTO responseDTO) {
        Review review = reviewRepository.findById(responseDTO.getReview().getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + responseDTO.getReview().getReviewId()));

        if (responseRepository.existsByReview_ReviewId(review.getReviewId())) {
            throw new IllegalStateException("A response already exists for review: " + review.getReviewId());
        }

        Response response = new Response();
        response.setContent(responseDTO.getContent());
        response.setResponseDate(new Date());
        response.setReview(review);

        Response savedResponse = responseRepository.save(response);
        return convertToDTO(savedResponse);
    }

    public ResponseDTO updateResponse(int responseId, ResponseDTO responseDTO) {
        Optional<Response> optionalResponse = responseRepository.findById(responseId);
        if (optionalResponse.isPresent()) {
            Response response = optionalResponse.get();
            response.setContent(responseDTO.getContent());
            response.setResponseDate(new Date());

            Review review = reviewRepository.findById(responseDTO.getReview().getReviewId())
                    .orElseThrow(() -> new IllegalArgumentException("Review not found: " + responseDTO.getReview().getReviewId()));
            response.setReview(review);

            Response updatedResponse = responseRepository.save(response);
            return convertToDTO(updatedResponse);
        }
        return null;
    }

    public void deleteResponse(int responseId) {
        responseRepository.deleteById(responseId);
    }

    private ResponseDTO convertToDTO(Response response) {
        ReviewDTO reviewDTO = reviewService.getReviewById(response.getReview().getReviewId());
        return new ResponseDTO(
                response.getResponseId(),
                response.getContent(),
                response.getResponseDate(),
                reviewDTO
        );
    }
}