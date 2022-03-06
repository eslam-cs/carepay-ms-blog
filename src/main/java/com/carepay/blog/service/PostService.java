package com.carepay.blog.service;

import java.util.List;

import com.carepay.blog.domain.CommentModel;
import com.carepay.blog.domain.PostModel;

public interface PostService {

	void addComment(String uuid,  CommentModel comment);
	void addOrUpdatePost( PostModel post);
	void deleteCommentByUuid(String uuid, String commentUuid);
	void deletePostByUuid(String uuid);
	List<CommentModel> getAllComments(String uuid, Integer page, Integer pageSize);
	List<PostModel> getAllPosts(Integer page, Integer pageSize);
	CommentModel getCommentByUuid(String uuid, String commentUuid);
	PostModel getPostByUuid(String uuid);
}
