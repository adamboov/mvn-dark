package com.adam.dark.repository;

import com.adam.dark.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adamboov
 */
@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Long>  {
}
