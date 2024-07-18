package com.example.capstone.user.service;

import com.example.capstone.user.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserDto send(String topic, UserDto userDto){
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";

        try{
            jsonInString = mapper.writeValueAsString(userDto);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
        }

        kafkaTemplate.send(topic,jsonInString);

        log.info("Kafka Producer send data" + userDto);

        return userDto;
    }

}
