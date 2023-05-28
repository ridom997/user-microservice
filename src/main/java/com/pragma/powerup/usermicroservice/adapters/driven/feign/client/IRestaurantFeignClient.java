package com.pragma.powerup.usermicroservice.adapters.driven.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "foodcourt-microservice", url = "localhost:8091/restaurants")
public interface IRestaurantFeignClient {

    @GetMapping(value = "/{id}/validateOwner")
    Map<String, Boolean> userIsTheRestaurantOwner(@PathVariable("id") Long id);

}
