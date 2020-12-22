package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fpt.entity.Posts;
import com.fpt.entity.Users;
import com.fpt.repository.PostRepository;
import com.fpt.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
	@Autowired
	private PostService postService;
	@MockBean
	private PostRepository postRepository;
	@Test
	public void testfindById() {
		Integer id=2;
		Posts post= new Posts();
		post.setId(2);
		Optional<Posts> optionalpost = Optional.of(post);
		when(postRepository.findById(id)).thenReturn(optionalpost);
		assertEquals(post, postService.findById(id));
	}
    @Test
	public void testgetSemesterByUserId() {
		 List<Posts> list = new ArrayList<>();
		
		when(postRepository.findByOrderByIdDesc()).thenReturn(list);
		assertEquals(list, postService.findAll());
	}
	
	  @Test
		public void testfindAuthorByPostId() {
			Users user= new Users();
			Integer postId=1;
			
			when(postRepository.findAuthorByPostId(postId)).thenReturn(user);
			assertEquals(user, postService.findAuthorByPostId(postId));
		}
}
	
	


