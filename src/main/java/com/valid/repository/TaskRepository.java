package com.valid.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valid.entity.Task;
import com.valid.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	Page<Task> findAllByUserAndIsDeletedFalse(User user, Pageable pageable);
	
	Optional<Task> findByIdAndUserAndIsDeletedFalse(Long id, User user);

}
