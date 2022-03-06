package com.carepay.blog.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "comment")
public class CommentModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private UUID uuid;
	
	private String content;

	private OffsetDateTime commentDate;
	
	@ManyToOne
	private PostModel post;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	
	public OffsetDateTime getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(OffsetDateTime commentDate) {
		this.commentDate = commentDate;
	}

	public PostModel getPost() {
		return post;
	}

	public void setPost(PostModel post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", uuid=" + uuid + ", content=" + content + ", postDate=" + commentDate + "]";
	}
	
	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(uuid);
		return builder.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CommentModel)) {
			return false;
		}
		CommentModel other = (CommentModel) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.uuid, other.getUuid());
		return builder.isEquals();
	}

}
