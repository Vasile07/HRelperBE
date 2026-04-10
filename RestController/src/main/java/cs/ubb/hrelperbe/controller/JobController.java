package cs.ubb.hrelperbe.controller;

import cs.ubb.hrelperbe.DTOs.JobPostData;
import cs.ubb.hrelperbe.DTOs.LoginCredentials;
import cs.ubb.hrelperbe.Interfaces.JobServiceInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
@SecurityRequirement(name = "bearerAuth")
public class JobController {
    private final JobServiceInterface jobService;

    public JobController(JobServiceInterface jobService) {
        this.jobService = jobService;
    }

    @PostMapping(path = "")
    public void createNewJobPost(@RequestBody JobPostData jobPostData){
        jobService.createJobPost(jobPostData);
    }
}
