package com.planner.SmartTaskPlanner.repository;

import com.planner.SmartTaskPlanner.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

