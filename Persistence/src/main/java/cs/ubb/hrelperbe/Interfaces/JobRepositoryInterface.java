package cs.ubb.hrelperbe.Interfaces;

import java.util.List;

import cs.ubb.hrelperbe.BaseModels.Job;

public interface JobRepositoryInterface {
    public void save(Job job);

    public void update(Job job);

    public void deleteById(Integer jobId);

    Job getJobDetailsById(Integer jobId);

    List<Job> getAllJobs();

    List<Job> getJobHeaders();
}
