package com.carepay.blog.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carepay.blog.dataaccess.CommentRepository;
import com.carepay.blog.dataaccess.PostRepository;
import com.carepay.blog.domain.CommentModel;
import com.carepay.blog.domain.PostModel;
import com.carepay.blog.restapi.PostMapper;
import com.carepay.blog.restapi.PostMapperImpl;
import com.carepay.blog.server.dto.CommentApiDto;
import com.carepay.blog.server.dto.PostApiDto;

@ExtendWith(SpringExtension.class)
public class PostServiceImplTest {

	@InjectMocks
	private PostServiceImpl postServiceImpl;

	@Mock
	private PostRepository postRepository;

	@Mock
	private CommentRepository commentRepository;

	@Spy
	private PostMapper postMapper = new PostMapperImpl();

	@Test
	void addCommentTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.of(createPostModel()));
		Mockito.when(commentRepository.save(any())).thenReturn(createCommentModel());
		postServiceImpl.addComment("e017de52-dc94-4b84-ac97-0ddd43139f2b", createCommentModel());
		verify(postRepository, times(1)).findByUuid(any());
		verify(commentRepository, times(1)).save(any());
	}

	@Test
	void addCommentFailTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> {
			postServiceImpl.addComment("e017de52-dc94-4b84-ac97-0ddd43139f2b", createCommentModel());
		});
	}

	@Test
	void addOrUpdatePostTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.of(createPostModel()));
		postServiceImpl.addOrUpdatePost(createPostModel());
		verify(postRepository, times(1)).findByUuid(any());
	}

	@Test
	void addOrUpdatePostFailTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.empty());
		Mockito.when(postRepository.save(any())).thenReturn(createPostModel());
		postServiceImpl.addOrUpdatePost(createPostModel());
		verify(postRepository, times(1)).findByUuid(any());
		verify(postRepository, times(1)).save(any());

	}
	
	@Test
	void deleteCommentByUuidTest() {
		Mockito.when(commentRepository.findByUuid(any())).thenReturn(Optional.of(createCommentModel()));
		doNothing().when(commentRepository).delete(any());
		postServiceImpl.deleteCommentByUuid("e017de52-dc94-4b84-ac97-0ddd43139f2b", "a017de52-dc94-4b84-ac97-0ddd43139f2b");
		verify(commentRepository, times(1)).findByUuid(any());
		verify(commentRepository, times(1)).delete(any());
	}
	
	@Test
	void deleteCommentByUuidFailTest() {
		Mockito.when(commentRepository.findByUuid(any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> {
			postServiceImpl.deleteCommentByUuid("e017de52-dc94-4b84-ac97-0ddd43139f2b", "a017de52-dc94-4b84-ac97-0ddd43139f2b");
		});
	}
	
	@Test
	void deletePostByUuidTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.of(createPostModel()));
		doNothing().when(postRepository).delete(any());
		postServiceImpl.deletePostByUuid("e017de52-dc94-4b84-ac97-0ddd43139f2b");
		verify(postRepository, times(1)).findByUuid(any());
		verify(postRepository, times(1)).delete(any());
	}
	
	@Test
	void deletePostByUuidFailTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> {
			postServiceImpl.deletePostByUuid("e017de52-dc94-4b84-ac97-0ddd43139f2b");
		});
	}
	
	@Test
	void getAllPostsTest() {
		List<PostModel> postModelList= new ArrayList<>();
		postModelList.add(createPostModel());
		Mockito.when(postRepository.findAll(org.mockito.Matchers.isA(Pageable.class))).thenReturn(new PageImpl<>(postModelList));
		List<PostModel> response = postServiceImpl.getAllPosts(0, 10);
		assertEquals(1,response.size());
	}
	
	@Test
	void getAllCommentsTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.of(createPostModel()));
		List<CommentModel> commentModelModelList= new ArrayList<>();
		commentModelModelList.add(createCommentModel());
		Mockito.when(commentRepository.findByPost(any(),org.mockito.Matchers.isA(Pageable.class))).thenReturn(commentModelModelList);
		List<CommentModel> response = postServiceImpl.getAllComments("e017de52-dc94-4b84-ac97-0ddd43139f2b",0, 10);
		assertEquals(1,response.size());
	}
	
	@Test
	void getCommentByUuidTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.of(createPostModel()));
		Mockito.when(commentRepository.findByUuid(any())).thenReturn(Optional.of(createCommentModel()));
		CommentModel response = postServiceImpl.getCommentByUuid("e017de52-dc94-4b84-ac97-0ddd43139f2b", "a017de52-dc94-4b84-ac97-0ddd43139f2b");
		assertEquals(createCommentModel().getContent(),response.getContent());
	}
	@Test
	void getCommentByUuidFailPostTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> {
			postServiceImpl.getCommentByUuid("e017de52-dc94-4b84-ac97-0ddd43139f2b", "a017de52-dc94-4b84-ac97-0ddd43139f2b");
		});
	}
	@Test
	void getCommentByUuidFailCommentTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.of(createPostModel()));
		Mockito.when(commentRepository.findByUuid(any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> {
			postServiceImpl.getCommentByUuid("e017de52-dc94-4b84-ac97-0ddd43139f2b", "a017de52-dc94-4b84-ac97-0ddd43139f2b");
		});
	}
	
	@Test
	void getPostByUuidTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.of(createPostModel()));
		PostModel response = postServiceImpl.getPostByUuid("a017de52-dc94-4b84-ac97-0ddd43139f2b");
		assertEquals(createPostModel().getContent(),response.getContent());
	}
	@Test
	void getPostByUuidFailTest() {
		Mockito.when(postRepository.findByUuid(any())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> {
			postServiceImpl.getPostByUuid("a017de52-dc94-4b84-ac97-0ddd43139f2b");
		});
	}

	

	private CommentModel createCommentModel() {
		CommentModel commentModel = new CommentModel();
		commentModel.setUuid(UUID.fromString("e017de52-dc94-4b84-ac97-0ddd43139f2b"));
		commentModel.setContent("comment test");
		return commentModel;
	}

	
	private PostModel createPostModel() {
		PostModel postModel = new PostModel();
		postModel.setUuid(UUID.fromString("e017de52-dc94-4b84-ac97-0ddd43139f2b"));
		postModel.setTitle("post title");
		postModel.setContent("post test");
		return postModel;
	}
}
