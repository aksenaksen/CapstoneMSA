package com.example.youtubeservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topics = "youtube-events")
    public void successKafka(String kafkaMessage){
        log.info("kafka Message -> " + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>(){});
        }
        catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        String email = (String)map.get("email");
        log.info(email + "success");


    }
}
