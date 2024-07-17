package com.example.capstone.youtube.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "YOUTUBE", description = "YOUTUBE API")
@RestController
@RequestMapping("/api/youtube")
public class youtubeController {

    @GetMapping("/search")
    public ResponseEntity<Void> searchYoutube(@RequestParam String food){


        return null;
    }
}
