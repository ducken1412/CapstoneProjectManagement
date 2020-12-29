package com.fpt.test.service;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fpt.entity.Files;
import com.fpt.entity.HistoryRecords;
import com.fpt.repository.FilesRepository;
import com.fpt.service.FilesService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class FilesServiceTest {

	@Autowired
	private FilesService filesService;
	@MockBean
	private FilesRepository filesRepository;
	/*@Test
	public void testdeleteHistoryRecordsuccess() {
		Integer id=1;
		Files file = new Files();
		when(filesRepository.save(file)).thenReturn(file);
		assertEquals(true, filesService.saveFiles(file));
	}
	@Test
	public void testdeleteHistoryRecordfail() {
		
		Files file = new Files();
		when(filesRepository.save(file)).thenThrow(NullPointerException.class);
		assertEquals(false, filesService.saveFiles(file));
}*/
	@Test
	public void testdeleteAllByPostId() {
		Integer postId=2;
		Integer file = new Integer(2);
		when(filesRepository.deleteAllByPostId(postId)).thenReturn(file);
		assertEquals(file, filesService.deleteAllByPostId(postId));
	}

	@Test
	public void testgetFileByPostId() {
		Integer Id=2;
		 List<Files> list= new ArrayList<Files>();
			when(filesRepository.getFileByPostId(Id)).thenReturn(list);
			assertEquals(list, filesService.getFileByPostId(Id));
	
	}
}
