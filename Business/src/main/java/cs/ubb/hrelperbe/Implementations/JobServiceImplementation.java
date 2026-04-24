package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.*;
import cs.ubb.hrelperbe.DTOs.JobDetailsResponse;
import cs.ubb.hrelperbe.DTOs.JobHeaderData;
import cs.ubb.hrelperbe.DTOs.JobPostData;
import cs.ubb.hrelperbe.DTOs.TechnologyResponse;
import cs.ubb.hrelperbe.Interfaces.JobRepositoryInterface;
import cs.ubb.hrelperbe.Interfaces.JobServiceInterface;
import cs.ubb.hrelperbe.Interfaces.RoleRepositoryInterface;
import cs.ubb.hrelperbe.Interfaces.TechnologyRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImplementation implements JobServiceInterface {
    private final JobRepositoryInterface jobRepository;

    private final TechnologyRepositoryInterface technologyRepository;

    private final RoleRepositoryInterface roleRepository;

    public JobServiceImplementation(JobRepositoryInterface jobRepository, TechnologyRepositoryInterface technologyRepository, RoleRepositoryInterface roleRepository) {
        this.jobRepository = jobRepository;
        this.technologyRepository = technologyRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void createJobPost(JobPostData jobPostData) {
        Role role = roleRepository.getRoleById(jobPostData.getRoleId());
        List<MustHaveSkill> skills = jobPostData.getSkills()
                .stream()
                .map(skillDescription -> {
                    MustHaveSkill skill = new MustHaveSkill();
                    skill.setDescription(skillDescription);
                    return skill;
                })
                .toList();
        List<InterviewGuideQuestion> guides = jobPostData.getGuides()
                .stream()
                .map(guideDescription -> {
                    InterviewGuideQuestion guide = new InterviewGuideQuestion();
                    guide.setDescription(guideDescription);
                    return guide;
                })
                .toList();
        List<Technology> technologies = jobPostData.getTechnologies()
                .stream()
                .map(technologyRepository::getTechnologyById)
                .toList();

        Job job = new Job();
        job.setRole(role);
        job.setDescription(jobPostData.getDescription());
        job.setTechnologies(technologies);
        job.setMustHaveSkills(skills);
        job.setInterviewGuideQuestions(guides);
        jobRepository.save(job);
    }

    @Override
    public void updateJobPost(JobPostData jobPostData, Integer jobId) {
        Role role = roleRepository.getRoleById(jobPostData.getRoleId());
        List<MustHaveSkill> skills = jobPostData.getSkills()
                .stream()
                .map(skillDescription -> {
                    MustHaveSkill skill = new MustHaveSkill();
                    skill.setDescription(skillDescription);
                    return skill;
                })
                .toList();
        List<InterviewGuideQuestion> guides = jobPostData.getGuides()
                .stream()
                .map(guideDescription -> {
                    InterviewGuideQuestion guide = new InterviewGuideQuestion();
                    guide.setDescription(guideDescription);
                    return guide;
                })
                .toList();
        List<Technology> technologies = jobPostData.getTechnologies()
                .stream()
                .map(technologyRepository::getTechnologyById)
                .toList();

        Job job = new Job();
        job.setJobId(jobId);
        job.setRole(role);
        job.setDescription(jobPostData.getDescription());
        job.setTechnologies(technologies);
        job.setMustHaveSkills(skills);
        job.setInterviewGuideQuestions(guides);
        jobRepository.update(job);
    }

        @Override
        public void deleteJob(Integer jobId) {
                jobRepository.deleteById(jobId);
        }

    @Override
    public JobDetailsResponse getJobDetails(Integer jobId) {
        Job job = jobRepository.getJobDetailsById(jobId);

        List<String> skills = job.getMustHaveSkills().stream()
                .map(MustHaveSkill::getDescription)
                .toList();

        List<String> guides = job.getInterviewGuideQuestions().stream()
                .map(InterviewGuideQuestion::getDescription)
                .toList();

        List<TechnologyResponse> techResponses = job.getTechnologies().stream()
                .map(tech -> new TechnologyResponse(tech.getTechnologyId(), tech.getName()))
                .toList();

        return new JobDetailsResponse(
                job.getJobId(),
                job.getDescription(),
                job.getRole().getName(),
                job.getRole().getDepartment().getName(),
                skills,
                techResponses,
                guides
        );
    }
    @Override
    public List<JobHeaderData> getJobHeaders(){
        List<Job> jobHeaders = jobRepository.getJobHeaders();

        return jobHeaders.stream()
        .map(job -> new JobHeaderData(
            job.getJobId(), 
            job.getRole().getName(),
            job.getRole().getDepartment().getName()
        ))
        .collect(Collectors.toList());
    }
}
