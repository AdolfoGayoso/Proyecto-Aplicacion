package com.ufro.voy_y_vuelvo.service.user;

import com.ufro.voy_y_vuelvo.dto.user.PublisherDto;
import com.ufro.voy_y_vuelvo.model.users.Publisher;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    public PublisherDto toDto(Publisher publisher) {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setUserType(publisher.getUserType());
        publisherDto.setEmail(publisher.getEmail());
        publisherDto.setUserName(publisher.getUserName());
        publisherDto.setPublishedTrips(publisher.getPublishedTrips());
        return publisherDto;
    }
}
