package com.fpt.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.entity.Posts;
import com.fpt.repository.PostRepository;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;

	@Override
	@Cacheable(value="postCache")
	public Posts findById(Integer id) {
		return postRepository.findById(id).orElse(null);
	}

	@Override
	@CacheEvict(value="postCache", allEntries = true) 
	public boolean deletePost(Integer id) {
		try {
			postRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	@CacheEvict(value="postCache",allEntries = true) 
	public boolean save(Posts post) {
		try {
			postRepository.save(post);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	@Cacheable(value="postCache")
	public List<Posts> findAll() {
		return postRepository.findByOrderByIdDesc();
	}

	@Override
	@Cacheable(value="postCache")
	public Page<Posts> findPaginated(Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		Pageable secondPageWithFiveElements = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
		return postRepository.findAll(secondPageWithFiveElements);
	}
}