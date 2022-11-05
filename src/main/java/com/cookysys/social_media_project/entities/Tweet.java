package com.cookysys.social_media_project.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data

public class Tweet {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn
	private User author;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp posted;

	@Column(nullable = false)
	private boolean deleted;

	private String content;

	@ManyToOne
	@JoinColumn
	private Tweet inReplyTo;

	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies;

	@ManyToOne
	@JoinColumn
	private Tweet repostOf;

	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts;

	@ManyToMany
	@JoinTable
	private List<User> likes;

	@ManyToMany
	@JoinTable
	private List<Hashtag> hashtags;

	@ManyToMany
	@JoinTable
	private List<User> userMentioned;

}
