package com.valid.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateSubTaskRequest {
	 @NotNull(message = "Subtask ID is required.")
	    private Long subTaskId;

	    @NotNull(message = "Status is required.")
	    private Integer status; // 0 for incomplete, 1 for complete
}
