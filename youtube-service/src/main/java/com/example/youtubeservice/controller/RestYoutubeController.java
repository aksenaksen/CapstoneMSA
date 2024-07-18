package com.example.youtubeservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/youtube")
public class RestYoutubeController {
    @GetMapping("/get")
    public ResponseEntity<String> success(){
        return ResponseEntity.ok("good");
    }
}
