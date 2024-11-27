package com.valid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valid.dto.CreateTaskRequest;
import com.valid.dto.UpdateTaskRequest;
import com.valid.entity.SubTask;
import com.valid.entity.Task;
import com.valid.entity.User;
import com.valid.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TestController {
	
	 @Autowired
	    private TaskService taskService;
	 
	@PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody CreateTaskRequest request,
            @RequestParam Task.Priority priority,
            @AuthenticationPrincipal User user) {
        Task task = taskService.createTask(request, priority, user);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }
	
	 @PostMapping("/{taskId}/subtasks")
	    public ResponseEntity<SubTask> createSubTask(
	            @PathVariable Long taskId,
	            @RequestParam String title,
	            @AuthenticationPrincipal User user) {
	        SubTask subTask = taskService.createSubTask(taskId, title, user);
	        return new ResponseEntity<>(subTask, HttpStatus.CREATED);
	    }
	 
	 @GetMapping
	    public ResponseEntity<Page<Task>> getUserTasks(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @AuthenticationPrincipal User user) {
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Task> tasks = taskService.getUserTasks(user, pageable);
	        return ResponseEntity.ok(tasks);
	    }
	 
	    @GetMapping("/{taskId}/subtasks")
	    public ResponseEntity<List<SubTask>> getSubTasksByTask(
	            @PathVariable Long taskId,
	            @AuthenticationPrincipal User user) {
	        List<SubTask> subTasks = taskService.getSubTasksByTask(taskId, user);
	        return ResponseEntity.ok(subTasks);
	    }

	    @PutMapping("/{taskId}")
	    public ResponseEntity<Task> updateTask(
	            @PathVariable Long taskId,
	            @RequestBody UpdateTaskRequest request,
	            @RequestParam Task.Status status,
	            @AuthenticationPrincipal User user) {
	        Task updatedTask = taskService.updateTask(taskId, request, status, user);
	        return ResponseEntity.ok(updatedTask);
	    }
	    
	    @PutMapping("/subtasks/{subTaskId}")
	    public ResponseEntity<SubTask> updateSubTask(
	            @PathVariable Long subTaskId,
	            @RequestParam boolean status,
	            @AuthenticationPrincipal User user) {
	        SubTask updatedSubTask = taskService.updateSubTask(subTaskId, status, user);
	        return ResponseEntity.ok(updatedSubTask);
	    }

	    
	    @DeleteMapping("/{taskId}")
	    public ResponseEntity<Void> softDeleteTask(
	            @PathVariable Long taskId,
	            @AuthenticationPrincipal User user) {
	        taskService.softDeleteTask(taskId, user);
	        return ResponseEntity.noContent().build();
	    }

	    @DeleteMapping("/subtasks/{subTaskId}")
	    public ResponseEntity<Void> softDeleteSubTask(
	            @PathVariable Long subTaskId,
	            @AuthenticationPrincipal User user) {
	        taskService.softDeleteSubTask(subTaskId, user);
	        return ResponseEntity.noContent().build();
	    }

}
