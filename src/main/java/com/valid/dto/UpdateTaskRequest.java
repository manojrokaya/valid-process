package com.valid.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskRequest {
	 @NotNull(message = "Task ID is required.")
	    private Long taskId;

	    private LocalDate dueDate;

	    private String status; // "TODO" or "DONE"
}
