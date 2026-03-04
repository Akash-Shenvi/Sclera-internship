package com.sclera.demo.repository.projection;

public interface AuthorAvgRatingProjection {
    Long getAuthorId();
    String getFullName();
    String getCountry();
    Double getAvgRating();
}
