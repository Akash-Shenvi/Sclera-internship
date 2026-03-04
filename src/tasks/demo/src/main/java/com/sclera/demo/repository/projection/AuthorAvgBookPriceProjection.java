package com.sclera.demo.repository.projection;

public interface AuthorAvgBookPriceProjection {
    Long getAuthorId();
    String getFullName();
    String getCountry();
    Double getAvgBookPrice();
}
