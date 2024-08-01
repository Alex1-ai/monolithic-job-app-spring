package com.chidi.firstjobapp.review;


import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> addReview(@PathVariable Long companyId, @RequestBody Review review){
        Review savedReview = reviewService.addReview(companyId, review);

        if(savedReview != null){
            return new ResponseEntity<>(savedReview, HttpStatus.CREATED);



        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId,
                                            @PathVariable Long reviewId){
        Review review = reviewService.getReview(companyId, reviewId);

        if(review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }



    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId,
                                               @RequestBody Review review){

        Boolean isUpdated  = reviewService.updateReview(companyId,reviewId, review);
        if(isUpdated == true){
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Review not updated", HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long companyId,
            @PathVariable Long reviewId
    ){
        Boolean isDeleted  = reviewService.deleteReview(companyId,reviewId);
        if(isDeleted == true){
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Review not deleted", HttpStatus.NOT_FOUND);
        }

    }



}
