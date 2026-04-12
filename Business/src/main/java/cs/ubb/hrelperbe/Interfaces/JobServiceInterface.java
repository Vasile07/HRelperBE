package cs.ubb.hrelperbe.Interfaces;

import cs.ubb.hrelperbe.DTOs.JobPostData;

public interface JobServiceInterface {
    public void createJobPost(JobPostData jobPostData);

    public void updateJobPost(JobPostData jobPostData, Integer jobId);
}
