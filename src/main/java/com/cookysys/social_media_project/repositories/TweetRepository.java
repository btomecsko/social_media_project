package com.cookysys.social_media_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cookysys.social_media_project.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

	@Query(value = "SELECT * FROM public.tweet t WHERE t.id = :id AND t.deleted = false", nativeQuery = true)
	Tweet findByIdAndDeletedFalse(@Param("id") Long id);

}
