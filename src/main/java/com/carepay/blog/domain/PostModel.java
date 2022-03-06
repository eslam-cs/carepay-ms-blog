package com.carepay.blog.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "post")
public class PostModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private UUID uuid;

	private String title;

	private String content;

	private OffsetDateTime postDate;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public OffsetDateTime getPostDate() {
		return postDate;
	}

	public void setPostDate(OffsetDateTime postDate) {
		this.postDate = postDate;
	}

	@Override
	public String toString() {
		return "PostModel [id=" + id + ", uuid=" + uuid + ", title=" + title + ", content=" + content + ", postDate="
				+ postDate + "]";
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(uuid);
		return builder.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PostModel)) {
			return false;
		}
		PostModel other = (PostModel) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.uuid, other.getUuid());
		return builder.isEquals();
	}

}
