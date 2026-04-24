package cs.ubb.hrelperbe.Interfaces;

import cs.ubb.hrelperbe.DTOs.TechnologyData;
import cs.ubb.hrelperbe.DTOs.TechnologyResponse;

import java.util.List;

public interface TechnologyServiceInterface {
    List<TechnologyResponse> getAllTechnologies();
    TechnologyData getTechnologyById(Integer technologyId);
}
