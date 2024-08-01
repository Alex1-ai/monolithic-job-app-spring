package com.chidi.firstjobapp.review.impl;

import com.chidi.firstjobapp.company.Company;
import com.chidi.firstjobapp.company.CompanyService;
import com.chidi.firstjobapp.review.Review;
import com.chidi.firstjobapp.review.ReviewRepository;
import com.chidi.firstjobapp.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
   @Autowired
    private ReviewRepository reviewRepository;

   @Autowired
   private CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
         List<Review> reviews = reviewRepository.findByCompanyId(companyId);
         return reviews;
    }

    @Override
    public Review addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null){
            review.setCompany(company);
            return reviewRepository.save(review);

        }
        return null;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);

        return reviews.stream()
                .filter(review -> review.getId()
                        .equals(reviewId))
                .findFirst().orElse(null);
    }

    @Override
    public Boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
//        System.out.println(companyService.getCompanyById(companyId));
        if(companyService.getCompanyById(companyId) != null){
            updatedReview.setCompany(companyService.getCompanyById(companyId));
            updatedReview.setId(reviewId);
            System.out.println(updatedReview);
            reviewRepository.save(updatedReview);
            return true;

        }
        return null;
    }

    @Override
    public Boolean deleteReview(Long companyId, Long reviewId) {
        if(
                companyService.getCompanyById(companyId) != null
                && reviewRepository.existsById(reviewId)
        ){
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company = review.getCompany();
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(companyId, company);

            reviewRepository.deleteById(reviewId);

            return true;




        }else{
            return false;

        }

    }
}
