package com.pragma.powerup.usermicroservice.adapters.driven.feign.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.IRestaurantFeignClient;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.FailConnectionToExternalMicroserviceException;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.RestaurantFeignBadRequestException;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.RestaurantFeignNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.RestaurantFeignUnauthorizedException;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantValidationCommunicationPort;
import feign.FeignException;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@AllArgsConstructor
@Component
public class RestaurantValidationFeignAdapter implements IRestaurantValidationCommunicationPort {

    private final IRestaurantFeignClient restaurantFeignClient;

    @Override
    public Boolean isTheRestaurantOwner(Long idRestaurant) {
        try{
            Map<String, Boolean> responseUserIsTheRestaurantOwner = restaurantFeignClient.userIsTheRestaurantOwner(idRestaurant);
            return responseUserIsTheRestaurantOwner.get("isTheRestaurantOwner");
        } catch (FeignException.NotFound e) {
            throw new RestaurantFeignNotFoundException(e);
        } catch (FeignException.Unauthorized e) {
            throw new RestaurantFeignUnauthorizedException(e);
        } catch (FeignException.BadRequest e) {
            throw new RestaurantFeignBadRequestException(e);
        } catch (RetryableException e){
            throw new FailConnectionToExternalMicroserviceException();
        }
    }
}
