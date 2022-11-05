package com.cookysys.social_media_project.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.cookysys.social_media_project.embeddables.CredentialsEmbeddable;
import com.cookysys.social_media_project.embeddables.ProfileEmbeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "user_table")

public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private CredentialsEmbeddable credentials;

	@Embedded
	private ProfileEmbeddable profile;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp joined;

	@Column(nullable = false)
	private boolean deleted;

	@OneToMany(mappedBy = "author")
	private List<Tweet> tweets;

	@ManyToMany(mappedBy = "likes")
	private List<Tweet> likedTweets;

	@ManyToMany(mappedBy = "followers")
	private List<User> following;

	@ManyToMany
	@JoinTable
	private List<User> followers;

	@ManyToMany(mappedBy = "userMentioned")
	private List<Tweet> mentions;

}
