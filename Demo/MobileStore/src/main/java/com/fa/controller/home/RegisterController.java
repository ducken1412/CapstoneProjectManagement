package com.fa.controller.home;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fa.common.RegisterConstant;
import com.fa.entity.UserRegister;
import com.fa.entity.Users;
import com.fa.service.MailServiceImpl;
import com.fa.service.RoleService;
import com.fa.service.UserRegisterServiceImpl;
import com.fa.service.UserService;
import com.fa.utils.EncrytedPasswordUtils;
import com.fa.utils.SendEmailUtils;
import com.fa.validator.RegisterValidator;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRegisterServiceImpl userRegisterService;

	@Autowired
	public RoleService roleService;

	@Autowired
	public MailServiceImpl mailService;

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	public SendEmailUtils sendMailUtils;

	@Autowired
	private RegisterValidator registerValidator;

	/**
	 * Redirect to register page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(Model model) {
		model.addAttribute("user", new Users());
		return "home/register1";
	}

	/**
	 * Register action
	 * @param model
	 * @param user
	 * @param br
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model, @Valid @ModelAttribute("user") Users user, BindingResult br) {
		// BackEnd validation
		registerValidator.validate(user, br);
		if (br.hasErrors()) {
			model.addAttribute("message", RegisterConstant.INVALID_REGISTER);
			System.out.println("Error message: " + br);
			return "home/register1";
		} else {
			if (userService.findByEmailAndStatus(user.getEmail(), 1) == null
					&& userService.findByUserNameAndStatus(user.getUserName(), 1) == null) {
				Date date = new Date();
				EncrytedPasswordUtils encrypt = new EncrytedPasswordUtils();
				// Save user
				user.setCreateDate(date);
				user.setStatus(0);
				user.setEncrytedPassword(encrypt.encrytePassword(user.getEncrytedPassword()));				
				userService.saveUser(user);				
				// Create code
				int code = (int) (Math.random() * 1000);
				String encryptedCode = encrypt.encrytePassword(String.valueOf(code));
				// Send email				
				sendMailUtils.sendMail(user, encryptedCode);				
				// Register success,active in 5 minutes
				model.addAttribute("message", RegisterConstant.REGISTER_SUCCESS);				
				UserRegister userRegister = new UserRegister();
				userRegister.setDate(date);
				userRegister.setId(user.getId());
				userRegister.setCode(encryptedCode);
				userRegisterService.saveUserRegister(userRegister);
				return "login/loginPage";
			}
			if (userService.findByEmailAndStatus(user.getEmail(), 1) != null
					&& userService.findByUserNameAndStatus(user.getUserName(), 1) == null) {
				model.addAttribute("message", RegisterConstant.EMAIL_IN_USE);
				return "home/register1";
			}
			if (userService.findByEmailAndStatus(user.getEmail(), 1) == null
					&& userService.findByUserNameAndStatus(user.getUserName(), 1) != null) {
				model.addAttribute("message", RegisterConstant.USERNAME_IN_USE);
				return "home/register1";
			} else {
				model.addAttribute("message", RegisterConstant.USERNAME_EMAIL_IN_USE);
				return "home/register1";
			}
		}
	}

	/**
	 * Confirm email register
	 * @param model
	 * @param id
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/confirmRegister", method = RequestMethod.GET)
	public String confirmRegister(Model model, @RequestParam("id") String id, @RequestParam("code") String code) {
		int confirmEmail = mailService.confirmEmail(id, code);
		if (confirmEmail == 1) {
			model.addAttribute("message", RegisterConstant.ACTIVE_SUCCESS);
			return "login/loginPage";
		}
		if (confirmEmail == 2) {
			model.addAttribute("message", RegisterConstant.ACTIVE_EXPIRE);
			return "login/loginPage";
		} else {
			model.addAttribute("message", RegisterConstant.EMAIL_IN_USE);
			return "login/loginPage";
		}
	}

}
