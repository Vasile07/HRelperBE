package cs.ubb.hrelperbe.controller;

import cs.ubb.hrelperbe.DTOs.TechnologyResponse;
import cs.ubb.hrelperbe.Interfaces.TechnologyServiceInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/technologies")
@SecurityRequirement(name = "bearerAuth")
public class TechnologyController {
    private final TechnologyServiceInterface technologyService;

    public TechnologyController(TechnologyServiceInterface technologyService) {
        this.technologyService = technologyService;
    }

    @GetMapping(path = "")
    public List<TechnologyResponse> getAllTechnologies() {
        return technologyService.getAllTechnologies();
    }
}
