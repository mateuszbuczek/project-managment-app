package com.mateuszbuczek.pm.services;

import com.mateuszbuczek.pm.domain.Backlog;
import com.mateuszbuczek.pm.domain.Project;
import com.mateuszbuczek.pm.domain.ProjectTask;
import com.mateuszbuczek.pm.exceptions.ProjectNotFoundException;
import com.mateuszbuczek.pm.repositories.BacklogRepository;
import com.mateuszbuczek.pm.repositories.ProjectRepository;
import com.mateuszbuczek.pm.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            // PTs to be added to a specific project, project != null, BL exists
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            // set the BL to projecttask
            projectTask.setBacklog(backlog);
            // we want our project sequence to be like this IDPRO-1 IDPRO-2
            Integer BacklogSequence = backlog.getPTSequence();
            // update the BL sequence
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            // add sequence to project task
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            // initial priority when priority null
            if(projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }
            // initial status when status is null
            if(projectTask.getStatus() == "" || projectTask.getStatus()== null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not found");
        }

    }


    public Iterable<ProjectTask> findBacklogById(String id) {

        Project project = projectRepository.findByProjectIdentifier(id);

        if(project==null) {
            throw new ProjectNotFoundException("Project with ID: '" + id + "' doest not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
