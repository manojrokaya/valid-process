package com.valid.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTaskRequest {
	 @NotBlank(message = "Title is required.")
	  private String title;

	    private String description;

	    @NotNull(message = "Due date is required.")
	    private LocalDate dueDate;

	    @NotBlank(message = "Priority is required.")
	    private String priority;
}
