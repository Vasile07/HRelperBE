package cs.ubb.hrelperbe.controller;

import cs.ubb.hrelperbe.DTOs.JobPostData;
import cs.ubb.hrelperbe.DTOs.LoginCredentials;
import cs.ubb.hrelperbe.Interfaces.JobServiceInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PutMapping(path = "/{id}")
    public void editJobPost(@RequestBody JobPostData jobPostData, @PathVariable Integer id){
        jobService.updateJobPost(jobPostData, id);
    }

}
