package cs.ubb.hrelperbe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public String checkAvailability() {
        // Return a simple 200 OK response with a message
        return "OK";
    }
}