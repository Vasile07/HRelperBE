package cs.ubb.hrelperbe.controller;

import cs.ubb.hrelperbe.DTOs.JobDetailsResponse;
import cs.ubb.hrelperbe.DTOs.JobHeaderData;
import cs.ubb.hrelperbe.DTOs.JobPostData;
import cs.ubb.hrelperbe.DTOs.QuizQuestionDTO;
import cs.ubb.hrelperbe.Interfaces.JobServiceInterface;
import cs.ubb.hrelperbe.Interfaces.QuizServiceInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jobs")
@SecurityRequirement(name = "bearerAuth")
public class JobController {
    private final JobServiceInterface jobService;

    private final QuizServiceInterface quizService;

    public JobController(JobServiceInterface jobService, QuizServiceInterface quizService) {
        this.jobService = jobService;
        this.quizService = quizService;
    }

    @PostMapping(path = "")
    public void createNewJobPost(@RequestBody JobPostData jobPostData){
        jobService.createJobPost(jobPostData);
    }

    @PutMapping(path = "/{id}")
    public void editJobPost(@RequestBody JobPostData jobPostData, @PathVariable("id") Integer id){
        jobService.updateJobPost(jobPostData, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteJob(@PathVariable("id") Integer id){
        jobService.deleteJob(id);
    }

    @GetMapping(path = "/{id}/quiz")
    public List<QuizQuestionDTO> getQuizForJob(@PathVariable("id") Integer id){
        return quizService.getQuizForJob(id);
    }

    @GetMapping(path = "/{id}")
    public JobDetailsResponse getJobDetailsById(@PathVariable("id") Integer id) {
        return jobService.getJobDetails(id);
    }

    @GetMapping(path = "")
    public List<JobHeaderData> getJobHeaders() {
        return jobService.getJobHeaders();
    }
    
}
