package com.fa.test.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fa.repository.ProductRepository;
@SpringBootTest
class ProducRepositoryTest {
	@Autowired
	private ProductRepository productRepository;
	
	

}
