package com.mateuszbuczek.pm.services;

import com.mateuszbuczek.pm.domain.Backlog;
import com.mateuszbuczek.pm.domain.ProjectTask;
import com.mateuszbuczek.pm.repositories.BacklogRepository;
import com.mateuszbuczek.pm.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        // Exceptions: project not found

        // PTs to be added to a specific project, project != null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        // set the BL to projecttask
        projectTask.setBacklog(backlog);
        // we want our project sequence to be like this IDPRO-1 IDPRO-2
        Integer BacklogSequence = backlog.getPTSequence();
        // update the BL sequence
        BacklogSequence++;

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
    }
}
