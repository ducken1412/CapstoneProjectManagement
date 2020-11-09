package com.fa.test.utils;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.fa.dto.ProductDTO;
import com.fa.entity.Category;
import com.fa.entity.Orders;
import com.fa.entity.Users;
import com.fa.utils.SendEmailUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
class SendEmailUtilsTest {
	@Autowired
	SendEmailUtils sendEmailUtils;

	@MockBean
	public JavaMailSender emailSender;

	@Test
	void testSendMailOrder1() {
		MimeMessage mimeMessage = new MimeMessage((Session)null);
		Users user = null;
		Date now = new Date();
		Orders orders = new Orders(user, "address", "0123456789", "email@gmail.com", "customer", now, null, 0);
		orders.setId(1);
		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(new ProductDTO(1, "p1", new Category(), "", 100f, 10, 15, "Apple", 1, 1, "", null));
		productDTOs.add(new ProductDTO(2, "p2", new Category(), "", 100f, 10, 15, "Samsung", 1, 1, "", null));
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
		doNothing().when(emailSender).send(any(MimeMessage.class));
		sendEmailUtils.sendMail(orders, productDTOs);
	}

	@Test
	void testSendMailOrder2() {
		Users user = null;
		Date now = new Date();
		Orders orders = new Orders(user, "address", "0123456789", "email@gmail.com", "customer", now, null, 0);
		orders.setId(1);
		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(new ProductDTO(1, "p1", new Category(), "", 100f, 10, 15, "Apple", 1, 1, "", null));
		productDTOs.add(new ProductDTO(2, "p2", new Category(), "", 100f, 10, 15, "Samsung", 1, 1, "", null));
		MimeMessage mimeMessage = new MimeMessage((Session)null);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(RuntimeException.class).when(emailSender).send(any(MimeMessage.class));
		assertFalse(sendEmailUtils.sendMail(orders, productDTOs));
	}

	@Test
	void testSendMailOrder3() {
		Users user = null;
		Date now = new Date();
		Orders orders = new Orders(user, "address", "0123456789", "", "customer", now, null, 0);
		orders.setId(1);
		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(new ProductDTO(1, "p1", new Category(), "", 100f, 10, 15, "Apple", 1, 1, "", null));
		productDTOs.add(new ProductDTO(2, "p2", new Category(), "", 100f, 10, 15, "Samsung", 1, 1, "", null));
		assertFalse(sendEmailUtils.sendMail(orders, productDTOs));
	}

	@Test
	void testSendMailOrder4() {
		Users user = null;
		Date now = new Date();
		Orders orders = new Orders(user, "address", "0123456789", null, "", now, null, 0);
		orders.setId(1);
		List<ProductDTO> productDTOs = new ArrayList<>();
		productDTOs.add(new ProductDTO(1, "p1", new Category(), "", 100f, 10, 15, "Apple", 1, 1, "", null));
		productDTOs.add(new ProductDTO(2, "p2", new Category(), "", 100f, 10, 15, "Samsung", 1, 1, "", null));
		doThrow(UncheckedIOException.class).when(emailSender).send(any(MimeMessage.class));
		assertFalse(sendEmailUtils.sendMail(orders, productDTOs));
	}

}
