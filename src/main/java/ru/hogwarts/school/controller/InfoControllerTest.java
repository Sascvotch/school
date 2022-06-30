package ru.hogwarts.school.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("!prodaction")
public class InfoControllerTest {
    @GetMapping("/getPort")
    public ResponseEntity<Integer> serverPort() {
        Integer port = 1111;
        return ResponseEntity.ok(port);
    }
}
