package com.carepay.blog.restapi;

import java.util.List;


import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.carepay.blog.domain.CommentModel;
import com.carepay.blog.domain.PostModel;
import com.carepay.blog.server.api.PostsApi;
import com.carepay.blog.server.dto.CommentApiDto;
import com.carepay.blog.server.dto.CommentContentApiDto;
import com.carepay.blog.server.dto.GenericResponseApiDto;
import com.carepay.blog.server.dto.GenericResponseStatusApiDto;
import com.carepay.blog.server.dto.GenericResponseStatusApiDto.StatusCategoryEnum;
import com.carepay.blog.server.dto.PostApiDto;
import com.carepay.blog.server.dto.PostTitleApiDto;
import com.carepay.blog.service.PostService;

@Component
@Features(features = { "org.apache.cxf.jaxrs.validation.JAXRSBeanValidationFeature",
		"org.apache.cxf.ext.logging.LoggingFeature" })
public class PostRestImpl implements PostsApi {

	@Autowired
	private PostService postService;

	@Autowired
	private PostMapper postMapper;

	@Override
	public GenericResponseApiDto addComment(String uuid, CommentApiDto comment) {
		CommentModel commentModel = postMapper.mapCommentApiDtoToCommentModel(comment);
		postService.addComment(uuid, commentModel);
		return new GenericResponseApiDto().status(new GenericResponseStatusApiDto()
				.statusCategory(StatusCategoryEnum.OK).statusMessage("Comment created/updated successfully"));
	}

	@Override
	@Transactional
	public GenericResponseApiDto addOrUpdatePost(PostApiDto post) {
		PostModel postModel = postMapper.mapPostApiDtoToPostModel(post);
		postService.addOrUpdatePost(postModel);
		return new GenericResponseApiDto().status(new GenericResponseStatusApiDto()
				.statusCategory(StatusCategoryEnum.OK).statusMessage("Post created/updated successfully"));
	}

	@Override
	public List<CommentContentApiDto> getAllComments(String uuid, Integer page, Integer pageSize) {
		return postMapper
				.mapCommentModelListToCommentContentApiDtoList(postService.getAllComments(uuid, page, pageSize));

	}

	@Override
	public List<PostTitleApiDto> getAllPosts(Integer page, Integer pageSize) {
		return postMapper.mapPostModelListToPostTitleApiDtoList(postService.getAllPosts(page, pageSize));
	}

	@Override
	public CommentApiDto getCommentByUuid(String uuid, String commentUuid) {
		return postMapper.mapCommentModelToCommentApiDto(postService.getCommentByUuid(uuid, commentUuid));
	}

	@Override
	public PostApiDto getPostByUuid(String uuid) {
		return postMapper.mapPostModelToPostApiDto(postService.getPostByUuid(uuid));
	}

	@Override
	public GenericResponseApiDto deleteCommentByUuid(String uuid, String commentUuid) {
		postService.deleteCommentByUuid(uuid, commentUuid);
		return new GenericResponseApiDto().status(new GenericResponseStatusApiDto()
				.statusCategory(StatusCategoryEnum.OK).statusMessage("Comment deleted successfully"));
	}

	@Override
	public GenericResponseApiDto deletePostByUuid(String uuid) {
		postService.deletePostByUuid(uuid);
		return new GenericResponseApiDto().status(new GenericResponseStatusApiDto()
				.statusCategory(StatusCategoryEnum.OK).statusMessage("Comment deleted successfully"));
	}

}
