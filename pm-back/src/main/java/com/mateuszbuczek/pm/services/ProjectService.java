package com.mateuszbuczek.pm.services;

import com.mateuszbuczek.pm.domain.Project;
import com.mateuszbuczek.pm.exceptions.ProjectIdentifierException;
import com.mateuszbuczek.pm.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdate(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdentifierException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exits");
        }
    }

    public Project findProjectByIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if(project == null)
            throw new ProjectIdentifierException("Project ID '" + projectIdentifier + "' doest not exits");

        return project;
    }
}
