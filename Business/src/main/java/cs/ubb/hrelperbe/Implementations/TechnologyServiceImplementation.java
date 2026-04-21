package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.Technology;
import cs.ubb.hrelperbe.DTOs.TechnologyResponse;
import cs.ubb.hrelperbe.Interfaces.TechnologyRepositoryInterface;
import cs.ubb.hrelperbe.Interfaces.TechnologyServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnologyServiceImplementation implements TechnologyServiceInterface {
    private final TechnologyRepositoryInterface technologyRepository;

    public TechnologyServiceImplementation(TechnologyRepositoryInterface technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Override
    public List<TechnologyResponse> getAllTechnologies() {
        List<Technology> technologies = technologyRepository.getAllTechnologies();

        return technologies.stream()
                .map(tech -> new TechnologyResponse(tech.getTechnologyId(), tech.getName()))
                .collect(Collectors.toList());
    }
}
