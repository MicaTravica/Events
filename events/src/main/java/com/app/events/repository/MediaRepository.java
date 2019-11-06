package com.app.events.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.events.model.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long>{

	Collection<Media> findAllByEventId(Long id);

}
