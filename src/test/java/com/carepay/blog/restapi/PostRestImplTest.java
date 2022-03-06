package com.carepay.blog.restapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.carepay.blog.domain.CommentModel;
import com.carepay.blog.domain.PostModel;
import com.carepay.blog.server.dto.CommentApiDto;
import com.carepay.blog.server.dto.CommentContentApiDto;
import com.carepay.blog.server.dto.GenericResponseApiDto;
import com.carepay.blog.server.dto.GenericResponseStatusApiDto.StatusCategoryEnum;
import com.carepay.blog.server.dto.PostApiDto;
import com.carepay.blog.server.dto.PostTitleApiDto;
import com.carepay.blog.service.PostService;


@ExtendWith(SpringExtension.class)
public class PostRestImplTest {
	
	@InjectMocks
    private PostRestImpl postRestImpl;
	
	@Mock
	private PostService postService;

	@Spy
	private PostMapper postMapper= new PostMapperImpl();
	
	@Test
	void addCommentTest() {
		doNothing().when(postService).addComment(any(), any());
		GenericResponseApiDto response = postRestImpl.addComment(any(), createCommentApiDto());
        assertNotNull(response);
        assertEquals(StatusCategoryEnum.OK.value(), response.getStatus().getStatusCategory());
	}
	
	@Test
	void getAllComments() {
		List<CommentModel> commentModelList= new ArrayList<>();
		commentModelList.add(createCommentModel());
		Mockito.when(postService.getAllComments(any(),any(),any())).thenReturn(commentModelList);
		List<CommentContentApiDto> response =postRestImpl.getAllComments(any(), any(), any());
		assertEquals(1,response.size());
	}
	
	@Test
	void addOrUpdatePostTest() {
		doNothing().when(postService).addOrUpdatePost(any());
		GenericResponseApiDto response = postRestImpl.addOrUpdatePost(createPostApiDto());
		assertNotNull(response);
        assertEquals(StatusCategoryEnum.OK.value(), response.getStatus().getStatusCategory());
	}
	
	@Test
	void getAllPostsTest() {
		List<PostModel> postModelList= new ArrayList<>();
		postModelList.add(createPostModel());
		Mockito.when(postService.getAllPosts(any(),any())).thenReturn(postModelList);
		List<PostTitleApiDto> response =postRestImpl.getAllPosts(any(), any());
		assertEquals(1,response.size());
	}
	
	@Test
	void getCommentByUuidTest() {
		
		Mockito.when(postService.getCommentByUuid(any(),any())).thenReturn(createCommentModel());
		CommentApiDto response = postRestImpl.getCommentByUuid(any(), any());
		assertEquals(createCommentModel().getContent(),response.getContent());
		
	}
	
	@Test
	void getPostByUuidTest() {
		Mockito.when(postService.getPostByUuid(any())).thenReturn(createPostModel());
		PostApiDto response = postRestImpl.getPostByUuid(any());
		assertEquals(createPostModel().getContent(),response.getContent());
	}
	
	@Test
	void deleteCommentByUuidTest() {
		doNothing().when(postService).deleteCommentByUuid(any(), any());
		GenericResponseApiDto response = postRestImpl.deleteCommentByUuid(any(), any());
        assertNotNull(response);
        assertEquals(StatusCategoryEnum.OK.value(), response.getStatus().getStatusCategory());
	}
	
	@Test
	void deletePostByUuidTest() {
		doNothing().when(postService).deletePostByUuid( any());
		GenericResponseApiDto response = postRestImpl.deletePostByUuid( any());
        assertNotNull(response);
        assertEquals(StatusCategoryEnum.OK.value(), response.getStatus().getStatusCategory());
	}

	private CommentApiDto createCommentApiDto() {
		CommentApiDto commentApiDto=new CommentApiDto();
		commentApiDto.setUuid(UUID.fromString("e017de52-dc94-4b84-ac97-0ddd43139f2b"));
		commentApiDto.setContent("comment test");
		return commentApiDto;
	}
	
	private CommentModel createCommentModel() {
		CommentModel commentModel=new CommentModel();
		commentModel.setUuid(UUID.fromString("e017de52-dc94-4b84-ac97-0ddd43139f2b"));
		commentModel.setContent("comment test");
		return commentModel;
	}
	
	private PostApiDto createPostApiDto() {
		PostApiDto postApiDto=new PostApiDto();
		postApiDto.setUuid(UUID.fromString("e017de52-dc94-4b84-ac97-0ddd43139f2b"));
		postApiDto.setTitle("post title");
		postApiDto.setContent("post test");
		return postApiDto;
	}
	
	private PostModel createPostModel() {
		PostModel postModel=new PostModel();
		postModel.setUuid(UUID.fromString("e017de52-dc94-4b84-ac97-0ddd43139f2b"));
		postModel.setTitle("post title");
		postModel.setContent("post test");
		return postModel;
	}
}
