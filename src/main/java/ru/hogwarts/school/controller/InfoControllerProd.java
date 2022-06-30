package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("prodaction")
public class InfoControllerProd {
    @Autowired
    private ServletWebServerApplicationContext server;

    @GetMapping("/getPort")
    public ResponseEntity<Integer> serverPort() {
        Integer port = server.getWebServer().getPort();
        return ResponseEntity.ok(port);
    }
}
