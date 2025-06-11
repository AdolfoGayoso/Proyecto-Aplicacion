package com.ufro.voy_y_vuelvo.dto.user;

import com.ufro.voy_y_vuelvo.model.trips.Trip;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PublisherDto {
    private String userType;
    private String email;
    private String userName;
    private List<Trip> publishedTrips;
}
