package edu.uoc.epcsd.notification.domain.service;

import edu.uoc.epcsd.notification.application.kafka.ProductMessage;
import edu.uoc.epcsd.notification.application.rest.dtos.GetProductResponse;
import edu.uoc.epcsd.notification.application.rest.dtos.GetUserResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

@Log4j2
@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${userService.getUsersToAlert.url}")
    private String userServiceUrl;

    @Value("${productService.getProductDetails.url}")
    private String productServiceUrl;

    @Override
    public void notifyProductAvailable(ProductMessage productMessage) {

            // TODO: Use RestTemplate with the above userServiceUrl to query the User microservice in order to get
            //  the users that have an alert for the specified product (the date specified in the parameter may be the actual date: LocalDate.now()).
            //  Then simulate the email notification for the alerted users by logging a line with INFO level for each user
            //  saying "Sending an email to user " + the user fullName
        RestTemplate restTemplate = new RestTemplate();
        GetUserResponse[] userResponseArray = restTemplate.getForObject(userServiceUrl, GetUserResponse[].class, productMessage.getProductId(), LocalDate.now());

        Arrays.stream(userResponseArray).forEach(userResponse -> {
            if (Strings.isBlank(userResponse.getFullName())) {
                throw new RuntimeException("User name is empty");
            }
            log.info("Sending and email to user {}. ", userResponse.getFullName());
        });

    }
}
