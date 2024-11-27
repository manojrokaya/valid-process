package com.valid.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSubTaskRequest {
	 @NotNull(message = "Task ID is required.")
	    private Long taskId;

	    @NotBlank(message = "Subtask description is required.")
	    private String description;

}
