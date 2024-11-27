package com.valid.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.valid.dto.CreateTaskRequest;
import com.valid.dto.UpdateTaskRequest;
import com.valid.entity.SubTask;
import com.valid.entity.Task;
import com.valid.entity.User;
import com.valid.repository.SubTaskRepository;
import com.valid.repository.TaskRepository;
import com.valid.repository.UserRepository;

@Service
public class TaskService 
{
	 @Autowired
	    private TaskRepository taskRepository;

	    @Autowired
	    private SubTaskRepository subTaskRepository;

	    @Autowired
	    private UserRepository userRepository;
	    
	    public Task createTask(CreateTaskRequest request, Task.Priority priority, User user) {
	        Task task = new Task();
	        task.setTitle(request.getTitle());
	        task.setDescription(request.getDescription());
	        task.setDueDate(request.getDueDate());
	        task.setPriority(priority);
	        task.setStatus(Task.Status.TODO);
	        task.setUser(user);
	        return taskRepository.save(task);
	    }
	    
	    public SubTask createSubTask(Long taskId, String title, User user) {
	        Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
	        
	        if (!task.getUser().equals(user)) {
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this task");
	        }

	        SubTask subTask = new SubTask();
	        subTask.setTitle(title);
	        subTask.setTask(task);
	        subTask.setStatus(false); // Default status is 0
	        return subTaskRepository.save(subTask);
	    }
	    public Page<Task> getUserTasks(User user, Pageable pageable) {
	        return taskRepository.findAllByUserAndIsDeletedFalse(user, pageable);
	    }
	    
	    public List<SubTask> getSubTasksByTask(Long taskId, User user) {
	        Task task = taskRepository.findByIdAndUserAndIsDeletedFalse(taskId, user)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
	        return subTaskRepository.findAllByTaskAndIsDeletedFalse(task);
	    }
	    
	    public Task updateTask(Long taskId, UpdateTaskRequest request, Task.Status status, User user) {
	        Task task = taskRepository.findByIdAndUserAndIsDeletedFalse(taskId, user)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

	        if (!task.getUser().equals(user)) {
	            throw new RuntimeException("Unauthorized action.");
	        }

	        if (request.getDueDate() != null) {
	            task.setDueDate(request.getDueDate());
	        }

	        if (request.getStatus() != null) {
	            task.setStatus(status);
	        }
	        return taskRepository.save(task);
	    }

	    
	    public SubTask updateSubTask(Long subTaskId, boolean status, User user) {
	        SubTask subTask = subTaskRepository.findById(subTaskId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SubTask not found"));
	        
	        if (!subTask.getTask().getUser().equals(user)) {
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this subtask");
	        }

	        subTask.setStatus(status);
	        return subTaskRepository.save(subTask);
	    }
	    
	    public void softDeleteTask(Long taskId, User user) {
	        Task task = taskRepository.findByIdAndUserAndIsDeletedFalse(taskId, user)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

	        task.setDeleted(true);
	        taskRepository.save(task);

	        List<SubTask> subTasks = subTaskRepository.findAllByTaskAndIsDeletedFalse(task);
	        for (SubTask subTask : subTasks) {
	            subTask.setDeleted(true);
	            subTaskRepository.save(subTask);
	        }
	    }
	    

	    public void softDeleteSubTask(Long subTaskId, User user) {
	        SubTask subTask = subTaskRepository.findById(subTaskId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SubTask not found"));

	        if (!subTask.getTask().getUser().equals(user)) {
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this subtask");
	        }

	        subTask.setDeleted(true);
	        subTaskRepository.save(subTask);
	    }

}
