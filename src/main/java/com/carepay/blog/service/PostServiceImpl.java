package com.carepay.blog.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carepay.blog.dataaccess.CommentRepository;
import com.carepay.blog.dataaccess.PostRepository;
import com.carepay.blog.domain.CommentModel;
import com.carepay.blog.domain.PostModel;
import com.carepay.blog.restapi.PostMapper;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostMapper postMapper;

	@Override
	public void addComment(String uuid, CommentModel comment) {
		Optional<PostModel> post = postRepository.findByUuid(UUID.fromString(uuid));
		if (post.isPresent()) {
			comment.setPost(post.get());
			commentRepository.save(comment);
		} else {
			throw new RuntimeException("Post not found");
		}

	}

	@Transactional
	@Override
	public void addOrUpdatePost(PostModel post) {
		Optional<PostModel> existingPost = postRepository.findByUuid(post.getUuid());
		if (existingPost.isPresent()) {
			postMapper.mapNewPostModel(post, existingPost.get());
		} else {
			postRepository.save(post);
		}

	}

	@Override
	public void deleteCommentByUuid(String uuid, String commentUuid) {

		Optional<CommentModel> comment = commentRepository.findByUuid(UUID.fromString(commentUuid));
		if (comment.isPresent()) {
			commentRepository.delete(comment.get());
		} else {
			throw new RuntimeException("comment not found");
		}

	}

	@Override
	public void deletePostByUuid(String uuid) {
		Optional<PostModel> post = postRepository.findByUuid(UUID.fromString(uuid));
		if (post.isPresent()) {
			postRepository.delete(post.get());
		} else {
			throw new RuntimeException("post not found");
		}

	}

	@Override
	public List<CommentModel> getAllComments(String uuid, Integer page, Integer pageSize) {
		Optional<PostModel> post = postRepository.findByUuid(UUID.fromString(uuid));
		if (post.isPresent()) {
			Pageable pageable = PageRequest.of(page, pageSize);
			return commentRepository.findByPost(post.get(),pageable);
		} else {
			throw new RuntimeException("post not found");
		}
	}

	@Override
	public List<PostModel> getAllPosts(Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return postRepository.findAll(pageable).getContent();
	}

	@Override
	public CommentModel getCommentByUuid(String uuid, String commentUuid) {
		Optional<PostModel> post = postRepository.findByUuid(UUID.fromString(uuid));
		if (post.isPresent()) {
			Optional<CommentModel> comment = commentRepository.findByUuid(UUID.fromString(commentUuid));
			if (post.isPresent()) {
				return comment.get();
			} else {
				throw new RuntimeException("comment not found");
			}
		} else {
			throw new RuntimeException("post not found");
		}
	}

	@Override
	public PostModel getPostByUuid(String uuid) {

		Optional<PostModel> post = postRepository.findByUuid(UUID.fromString(uuid));
		if (post.isPresent()) {
			return post.get();
		} else {
			throw new RuntimeException("post not found");
		}
	}

}
