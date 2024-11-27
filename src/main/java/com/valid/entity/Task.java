package com.valid.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable =false)
	private String title;
	
	private String description;
	
	@Column(nullable = false)
	private LocalDate dueDate;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	 @Enumerated(EnumType.STRING)
	private Status status;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	private boolean isDeleted = false;
	
	
	public enum Priority {
        HIGH, MEDIUM, LOW
    }
	
	public enum Status {
        TODO, DONE
    }

}
