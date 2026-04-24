package cs.ubb.hrelperbe.Interfaces;

import java.util.List;

import cs.ubb.hrelperbe.DTOs.JobDetailsResponse;
import cs.ubb.hrelperbe.DTOs.JobHeaderData;
import cs.ubb.hrelperbe.DTOs.JobPostData;

public interface JobServiceInterface {
    public void createJobPost(JobPostData jobPostData);

    public void updateJobPost(JobPostData jobPostData, Integer jobId);

    JobDetailsResponse getJobDetails(Integer jobId);

    public List<JobHeaderData> getJobHeaders();
}
