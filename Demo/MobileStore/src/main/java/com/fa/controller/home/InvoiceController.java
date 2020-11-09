package com.fa.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InvoiceController {
	@RequestMapping(value = {"/invoice"}, method = RequestMethod.GET)
    public String getInvoice(Model model) {
		return "home/invoice";
	}
}
