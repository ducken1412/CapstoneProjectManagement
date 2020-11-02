package com.fa.test.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.utils.Word;

@RunWith(SpringRunner.class)
@SpringBootTest
class WordTest {
	@Test
	void testGetWords1() {
		int nWords = 10;
		String origin = "";
		String expected = "";
		String actual = Word.getWords(origin, nWords);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetWords2() {
		int nWords = 10;
		String origin = null;
		String expected = "";
		String actual = Word.getWords(origin, nWords);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetWords3() {
		int nWords = 2;
		String origin = "Origin   word need to get word";
		String expected = "Origin word...";
		String actual = Word.getWords(origin, nWords);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetWords4() {
		int nWords = 6;
		String origin = "Origin  word need to get word";
		String expected = "Origin word need to get word";
		String actual = Word.getWords(origin, nWords);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetWords5() {
		int nWords = 7;
		String origin = " Origin   word need to get word  ";
		String expected = "Origin word need to get word";
		String actual = Word.getWords(origin, nWords);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetWords8() {
		int nWords = 0;
		String origin = " Origin   word need to get word  ";
		String expected = "Origin...";
		String actual = Word.getWords(origin, nWords);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetWords9() {
		int nWords = -1;
		String origin = " Origin   word need to get word  ";
		String expected = "Origin...";
		String actual = Word.getWords(origin, nWords);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetChar1() {
		int nChars = 5;
		String origin = null;
		String expected = "";
		String actual = Word.getChars(origin, nChars);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetChar2() {
		int nChars = 5;
		String origin = "";
		String expected = "";
		String actual = Word.getChars(origin, nChars);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetChar3() {
		int nChars = 9;
		String origin = "123456789";
		String expected = "123456789";
		String actual = Word.getChars(origin, nChars);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetChar4() {
		int nChars = 5;
		String origin = "123456789";
		String expected = "12345...";
		String actual = Word.getChars(origin, nChars);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetChar5() {
		int nChars = 0;
		String origin = "123456789";
		String expected = "...";
		String actual = Word.getChars(origin, nChars);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetChar6() {
		int nChars = -1;
		String origin = "123456789";
		try {
			Word.getChars(origin, nChars);
			assertFalse(true);
		} catch (Exception e) {
			assertTrue(true);
		}
	
	}
	
	

}
