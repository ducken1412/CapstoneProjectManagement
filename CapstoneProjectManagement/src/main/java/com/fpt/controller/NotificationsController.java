package com.fpt.controller;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fpt.dto.NotificationDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class NotificationsController {

	@Autowired
	public NotificationsService notificationsService;

	@Autowired
	public UserService userService;

	@Autowired
	public CapstoneProjectService capstoneProjectService;

	@Autowired
	public NotificationDetailService notificationDetailService;

	@Autowired
	public CapstoneProjectDetailService capstoneProjectDetailService;

	@Autowired
	public HistoryRecordService recordService;

	@RequestMapping(value = "/notifications")
	public String getAllNotications(Model model, Principal principal, HttpServletRequest request) {
		if(principal == null) {
			return "redirect:/login";
		}
		Users user = userService.findByEmail(principal.getName());
		String user_id_login = user.getId();

		String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
		//get notification public
		//List<NotificationDTO> notification = notificationsService.getTitle();
		String linkNews = baseUrl + "list-news/";
		List<Notifications> notification = notificationsService.getTop5NotificationsByCreatedDate();
		model.addAttribute("notifications", notification);
		model.addAttribute("linkNews",linkNews);
		int count_noti_detail = notificationDetailService.countNotificationDetailByUserId(user_id_login);
		boolean check_noti_detail = false;
		if(count_noti_detail != 0){
			check_noti_detail = true;
		}
		//load notification by user id
		//List<NotificationDetails> notificationDetails = notificationDetailService.getIdNotification(user_id_login);
		List<NotificationDetails> notificationDetails = notificationDetailService.getIdNotificationByTop5(user_id_login);
		ArrayList<Notifications> noti = new ArrayList<>();
		for (NotificationDetails notidetail: notificationDetails
			 ) {
			int noti_id = notidetail.getNotification().getId();
			Notifications n =  notificationsService.getNotificationById(noti_id);
			noti.add(n);

		};
		Collections.sort(noti, new Comparator<Notifications>() {
			@Override
			public int compare(Notifications o1, Notifications o2) {
				return o2.getId()-o1.getId();
			}
		});
			model.addAttribute("notificationByUser", noti);
			model.addAttribute("user_id",user_id_login);
		model.addAttribute("check_noti_detail",check_noti_detail);
		String linkUserNews = baseUrl + "list-news-user/" + user_id_login;
		model.addAttribute("link_user", linkUserNews);
		return "home/notifications";
	}

	@RequestMapping(value = "/td/notification", method = RequestMethod.GET)
	public String notification(Model model,Principal principal) {
		if(principal == null) {
			return "redirect:/login";
		}
		model.addAttribute("notificationsDTO", new NotificationDTO());
		Notifications notifications = new Notifications();
		List<CapstoneProjects> capstoneProjects = capstoneProjectService.getAllProject();
		model.addAttribute("capstoneProjects", capstoneProjects);
		return "home/add-notification";
	}

	@RequestMapping(value = "/add-notification", method = RequestMethod.POST)
	public String saveNotification(@Valid NotificationDTO dto , BindingResult result, Model model, Principal principal) {

		if(principal == null) {
			return "redirect:/login";
		}
			if(result.hasErrors()) {
				model.addAttribute("notificationsDTO", new NotificationDTO());
				model.addAttribute("notificationsDTO",dto);
				List<CapstoneProjects> capstoneProjects = capstoneProjectService.getAllProject();
				model.addAttribute("capstoneProjects", capstoneProjects);
			return "home/add-notification";
		}
			Users user_login = userService.findByEmail(principal.getName());
			String user_id_login = user_login.getId();
			HistoryRecords records = new HistoryRecords();
			Date date = new Date();
			Notifications notifications = new Notifications();
		//add notification all user
			if(dto.getUser_id() == null && dto.getProject_id() == null){
				notifications.setTitle(dto.getTitle());
				notifications.setType("all");
				notifications.setContent(dto.getContent());
				notifications.setCreated_date(date);
				notificationsService.addNotification(notifications);
				records.setUser(user_login);
				records.setContent("Create notificaton");
				records.setNotification(notifications);
				records.setCreatedDate(date);
				recordService.save(records);
			}

		//add notifitcation for one user
		if (dto.getUser_id() != null){
			NotificationDetails notificationDetails = new NotificationDetails();
			notifications.setType("private");
			notifications.setTitle(dto.getTitle());
			notifications.setContent(dto.getContent());
			notifications.setCreated_date(date);
			notificationsService.addNotification(notifications);
			int noti_id = notifications.getId();
			notificationDetails.setNotification(notificationsService.getOneNoification(noti_id));
			String user_id = dto.getUser_id();
			//System.out.println(user_id);
			notificationDetails.setUser(userService.findById(user_id));
			String type = "private";
			notificationDetails.setType(type);
			notificationDetailService.addNotificationDetail(notificationDetails);
			records.setUser(user_login);
			records.setContent("Create notificaton");
			records.setNotification(notifications);
			records.setCreatedDate(date);
			recordService.save(records);
		}

		if(dto.getProject_id() != null) {
			notifications.setTitle(dto.getTitle());
			notifications.setType("private");
			notifications.setContent(dto.getContent());
			notifications.setCreated_date(date);
			notificationsService.addNotification(notifications);
			int project_id = dto.getProject_id();
			int noti_id = notifications.getId();
			List<CapstoneProjectDetails> capstoneProjectDetails = capstoneProjectDetailService.getDetailByCapstoneProjectId(project_id);
			NotificationDetails notificationDetails;
			for (int i = 0; i < capstoneProjectDetails.size(); i++) {
				notificationDetails = new NotificationDetails();
				notificationDetails.setNotification(notificationsService.getOneNoification(noti_id));
				notificationDetails.setUser(capstoneProjectDetails.get(i).getUser());
				notificationDetails.setType("private");
				notificationDetailService.addNotificationDetail(notificationDetails);
				records.setUser(user_login);
				records.setContent("Create notificaton");
				records.setNotification(notifications);
				records.setCreatedDate(date);
				recordService.save(records);
			}
		}
		return "redirect:/notification";
	}

	@RequestMapping(value = "/notification-detail/{id}", method = RequestMethod.GET)
	public String notificationDetail(@PathVariable("id") int id, Model model,Principal principal){
		if(principal == null) {
			return "redirect:/login";
		}
		Notifications notification = notificationsService.getOneNoification(id);
		if(notification == null){
			return "home/error";
		}
		System.out.println(notification.getTitle());
		model.addAttribute("notification", notification);
		return "home/notification-detail";
	}

	@RequestMapping(value = "/list-news")
	public String listNews(Model model, @RequestParam("page") Optional<Integer> page,
						   @RequestParam("size") Optional<Integer> size, Principal principal){
		if(principal == null) {
			return "redirect:/login";
		}
		//phan trang
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(20);
		Page<Notifications> notificationsPage = notificationsService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
		model.addAttribute("notificationsPage", notificationsPage);
		int totalPages = notificationsPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "home/list-news";
	}

	@RequestMapping(value = "/list-news-user/{id}")
	public String listNewsUser(@PathVariable("id") String id, Model model, @RequestParam("page") Optional<Integer> page,
							   @RequestParam("size") Optional<Integer> size, Principal principal){
		if(principal == null) {
			return "redirect:/login";
		}
		Users user = userService.findByEmail(principal.getName());
		String userId = user.getId();

		//phan trang
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(20);
		Page<Notifications> notificationsPage = notificationsService.getAllTitlePagginByUserId(PageRequest.of(currentPage - 1, pageSize),userId);
		model.addAttribute("notificationsPage", notificationsPage);
		int totalPages = notificationsPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("userId",userId);
		return "home/list-news-user";
	}
}
