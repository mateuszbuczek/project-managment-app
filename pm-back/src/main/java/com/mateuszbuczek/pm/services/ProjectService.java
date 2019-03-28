package com.mateuszbuczek.pm.services;

import com.mateuszbuczek.pm.domain.Backlog;
import com.mateuszbuczek.pm.domain.Project;
import com.mateuszbuczek.pm.exceptions.ProjectIdentifierException;
import com.mateuszbuczek.pm.repositories.BacklogRepository;
import com.mateuszbuczek.pm.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdate(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            // create object
            if(project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            // update object
            if(project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

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

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if(project == null)
            throw new ProjectIdentifierException("Cannot delete Project with ID '" + projectIdentifier + "'. This project does not exist");

        projectRepository.delete(project);
    }
}
