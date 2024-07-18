package com.example.capstone.user.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="youtube-service")
public interface YoutubeServiceClient {

    @GetMapping("/api/youtube/get")
    String getResponse();

}
