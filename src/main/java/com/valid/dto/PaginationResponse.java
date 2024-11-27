package com.valid.dto;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PaginationResponse<T> {
	   private int page;
	    private int size;
	    private long totalElements;
	    private int totalPages;
	    private boolean last;
	    private T data;
	    
	    public PaginationResponse(Page<T> pageData, T data) {
	        this.page = pageData.getNumber();
	        this.size = pageData.getSize();
	        this.totalElements = pageData.getTotalElements();
	        this.totalPages = pageData.getTotalPages();
	        this.last = pageData.isLast();
	        this.data = data;
	    }
}
