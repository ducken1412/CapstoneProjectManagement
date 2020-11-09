package com.fa.utils;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.fa.dto.ProductDTO;
import com.fa.entity.Orders;
import com.fa.entity.Users;

@Component
public class SendEmailUtils {

	@Autowired
	private JavaMailSender emailSender;

	public void sendMail(Users user, String code) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			boolean multipart = true;
			MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
			String htmlMsg = "<h3>Im testing send a HTML email</h3>"
					+ "<a href=\"http://localhost:8080/confirmRegister?code=" + code + "&id=" + user.getId()
					+ "\">Click here to confirm register</a>";
			message.setContent(htmlMsg, "text/html");
			helper.setTo(user.getEmail()); 
			helper.setSubject("Mobile Store register email for :" + user.getUserName());
			this.emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean sendMail(Orders orders, List<ProductDTO> products) {
		if (orders.getEmail() == null || ("").equals(orders.getEmail())) {
			return false;
		}
		try {
			MimeMessage message = emailSender.createMimeMessage();
			boolean multipart = true;
			MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
			StringBuilder content = new StringBuilder();
			content.append("<style type=\"text/css\">");
			content.append(".tg  {border-collapse:collapse;border-spacing:0;}");
			content.append(
					".tg td{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;");
			content.append("overflow:hidden;padding:10px 5px;word-break:normal;}");
			content.append(
					".tg th{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;");
			content.append("font-weight:normal;overflow:hidden;padding:10px 5px;word-break:normal;}");
			content.append(".tg .tg-0pky{border-color:inherit;text-align:left;vertical-align:top}");
			content.append(".tg .tg-0lax{text-align:left;vertical-align:top}");
			content.append("</style>");
			content.append("<h2>Your order number is: " + orders.getId() + "<h2>");
			content.append("<p>Your order number is processing<p>");
			content.append("<p>Customer: " + orders.getCustomerName() + "<p>");
			content.append("<p>Phone: " + orders.getPhone() + "<p>");
			content.append("<p>Email: " + orders.getEmail() + "<p>");
			content.append("<p>Address: " + orders.getAddress() + "<p>");
			content.append("<p>Order date: " + orders.getCreatedDate() + "<p>");

			content.append("<table class=\"tg\" border=\"1\" style=\"border-collapse: collapse;>\"");
			content.append("<thead>");
			content.append("<tr>");
			content.append("<th class=\"tg-0pky\">ID</th>");
			content.append("<th class=\"tg-0lax\">Product</th>");
			content.append("<th class=\"tg-0lax\">Price</th>");
			content.append("<th class=\"tg-0lax\">Discount</th>");
			content.append("<th class=\"tg-0lax\">Quantity</th>");
			content.append("<th class=\"tg-0lax\">Total</th>");
			content.append("</tr>");
			content.append("</thead>");
			content.append("<tbody>");
			float total = 0;
			float totalAll = 0;
			for (ProductDTO item : products) {
				content.append("<tr>");
				// content.append("<td class=\"tg-0lax\"><img
				// src=\""+Constants.CONTEXT_PATH+item.getImageMain()+"\" alt=\"product image
				// "+item.getName()+"\" width=\"109\" height=\"167\"></td>");
				content.append("<td class=\"tg-0lax\"><a href=\"" + Constants.CONTEXT_PATH + "/detail/" + item.getId()
						+ "\" target=\"_blank\" rel=\"noopener noreferrer\">" + item.getId() + "</a><br></td>");
				content.append("<td class=\"tg-0lax\"><a href=\"" + Constants.CONTEXT_PATH + "/detail/" + item.getId()
						+ "\" target=\"_blank\" rel=\"noopener noreferrer\">" + item.getName() + "</a><br></td>");
				content.append("<td class=\"tg-0lax\"><span style=\"font-weight:700;font-style:normal\"> $"
						+ FormatMoney.formartToUS(item.getPrice()) + "</span><br></td>");
				content.append("<td class=\"tg-0lax\">" + item.getDiscount() + "%</td>");
				content.append("<td class=\"tg-0lax\">" + item.getQuantity() + "</td>");
				total = item.getPrice() * (100 - item.getDiscount()) * item.getQuantity() / 100.0f;
				content.append("<td class=\"tg-0lax\"><span style=\"font-weight:700;font-style:normal\">$ "
						+ FormatMoney.formartToUS(FormatMoney.round(total)) + "</span><br><br></td>");
				content.append("</tr>");
				totalAll += total;
			}
			content.append("<tr>");
			content.append("<td class=\"tg-0lax\" colspan=\"5\"><span style=\"font-weight:bold\">Total:</span></td>");
			content.append("<td class=\"tg-0lax\"><span style=\"font-weight:bold\">$"
					+ FormatMoney.formartToUS(FormatMoney.round(totalAll)) + "</span></td>");
			content.append("</tr>");
			content.append("</tbody>");
			content.append("</table>");
			String htmlMsg = content.toString();
			message.setContent(htmlMsg, "text/html; charset=utf-8");
			helper.setTo(orders.getEmail()); 
			helper.setSubject("Mobile Store GR 2 : Create a order " + orders.getId() + " success");
			this.emailSender.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
