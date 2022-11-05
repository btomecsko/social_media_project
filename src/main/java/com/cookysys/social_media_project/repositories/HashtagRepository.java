package com.cookysys.social_media_project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cookysys.social_media_project.entities.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	@Query(value = "SELECT h.* FROM public.hashtag h WHERE h.label = :label", nativeQuery = true)
	Optional<Hashtag> findByLabelContainingIgnoreCase(@Param("label") String label);

}
