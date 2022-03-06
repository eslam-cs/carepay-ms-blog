package com.carepay.blog.restapi;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.carepay.blog.domain.CommentModel;
import com.carepay.blog.domain.PostModel;
import com.carepay.blog.server.dto.CommentApiDto;
import com.carepay.blog.server.dto.CommentContentApiDto;
import com.carepay.blog.server.dto.PostApiDto;
import com.carepay.blog.server.dto.PostTitleApiDto;

@Mapper(componentModel = "spring")
public interface PostMapper {

	@Mapping(target = "uuid", source = "uuid", defaultExpression = "java(java.util.UUID.randomUUID())")
	CommentModel mapCommentApiDtoToCommentModel(CommentApiDto comment);

	CommentApiDto mapCommentModelToCommentApiDto(CommentModel commentModel);

	@Mapping(target = "uuid", source = "uuid", defaultExpression = "java(java.util.UUID.randomUUID())")
	PostModel mapPostApiDtoToPostModel(PostApiDto post);

	PostApiDto mapPostModelToPostApiDto(PostModel postModel);

	List<CommentContentApiDto> mapCommentModelListToCommentContentApiDtoList(List<CommentModel> commentModelList);

	List<PostTitleApiDto> mapPostModelListToPostTitleApiDtoList(List<PostModel> postModelList);
	
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    PostModel mapNewPostModel(PostModel newModel, @MappingTarget PostModel existingModel);
}
