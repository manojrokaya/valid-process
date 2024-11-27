package com.valid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valid.entity.SubTask;
import com.valid.entity.Task;

public interface SubTaskRepository extends JpaRepository<SubTask, Long>{
	List<SubTask> findAllByTaskAndIsDeletedFalse(Task task);
}
