package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.*;
import cs.ubb.hrelperbe.DTOs.JobPostData;
import cs.ubb.hrelperbe.Interfaces.JobRepositoryInterface;
import cs.ubb.hrelperbe.Interfaces.JobServiceInterface;
import cs.ubb.hrelperbe.Interfaces.RoleRepositoryInterface;
import cs.ubb.hrelperbe.Interfaces.TechnologyRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
