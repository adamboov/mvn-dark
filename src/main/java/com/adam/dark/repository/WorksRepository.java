package com.adam.dark.repository;

import com.adam.dark.entity.ActorEntity;
import com.adam.dark.entity.WorksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorksRepository extends JpaRepository<WorksEntity, Long> {
}
