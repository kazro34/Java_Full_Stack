package dev.kazro.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview(String reviewBody, String imdbId) {
        Review review = reviewRepository.insert(new Review(reviewBody));

        mongoTemplate.update(Movie.class) //We are updating a movie where the IMDB ID of the movie in the database
                .matching(Criteria.where("imdbId").is(imdbId)) //matches the ID that we have received from the user
                .apply(new Update().push("reviewIds").value(review))
                .first();//then we apply this update

        return review;

    }
}
