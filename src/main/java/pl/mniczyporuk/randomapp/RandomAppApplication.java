package pl.mniczyporuk.randomapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.beans.ConstructorProperties;
import java.util.UUID;

@SpringBootApplication
@RestController
public class RandomAppApplication {

    private KafkaTemplate<String, Request> kafkaTemplate;

    public RandomAppApplication(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(RandomAppApplication.class, args);
    }

    @PostMapping(value = "/random_objects", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void createAndSave(@RequestBody Request request) {
        kafkaTemplate.send("test", UUID.randomUUID().toString(), request);
    }
}

class Request {

    private final String content;
    private final long random_number;

    @ConstructorProperties({"content", "random_number"})
    public Request(long random_number, String content) {
        this.content = content;
        this.random_number = random_number;
    }

    public String getContent() {
        return content;
    }

    public long getRandom_number() {
        return random_number;
    }
}