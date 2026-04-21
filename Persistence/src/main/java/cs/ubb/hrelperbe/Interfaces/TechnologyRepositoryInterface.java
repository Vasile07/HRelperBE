package cs.ubb.hrelperbe.Interfaces;

import cs.ubb.hrelperbe.BaseModels.Technology;

import java.util.List;

public interface TechnologyRepositoryInterface {
    public Technology getTechnologyById(Integer technologyId);
    List<Technology> getAllTechnologies();
}
