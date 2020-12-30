package com.fpt.controller;


import com.fpt.common.SendingMail;
import com.fpt.dto.CommentDTO;
import com.fpt.dto.ManageUserDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
import com.fpt.utils.Constant;
import com.fpt.utils.ExcelHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.Role;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class ManageUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private CapstoneProjectDetailService capstoneProjectDetailService;

    @Autowired
    private CapstoneProjectService capstoneProjectService;

    @Autowired
    private HistoryRecordService recordService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskDetailsService taskDetailsService;
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private NotificationDetailService notificationDetailService;

    @Autowired
    private SemestersService semestersService;
    @Autowired
    private SitesService sitesService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StatusService statusService;

    public boolean checkFormartExcel = true;
    public boolean checkSemester = true;
    public boolean checkSuccess = false;
    @GetMapping("ad/manageuser")
    public String manageUser(Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        Users user = userService.findByEmail(principal.getName());

        try {

            List<Semesters> semesters = semestersService.findAll();
            model.addAttribute("semesters", semesters);
            List<Sites> sites =  sitesService.findAll();
            model.addAttribute("sites", sites);
            ManageUserDTO manageUserDTO = new ManageUserDTO();
            model.addAttribute("manageUserDTO", manageUserDTO);

        } catch (Exception e) {

        }
        return "admin/manage-user";
    }


    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public String addUser(MultipartFile file, ManageUserDTO manageUserForm, Model model, BindingResult result, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) throws ParseException {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Semesters> semestersScreen = semestersService.findAll();
        model.addAttribute("semesters", semestersScreen);
        List<Sites> sitesScreen =  sitesService.findAll();
        model.addAttribute("sites", sitesScreen);
        ManageUserDTO manageUserDTO = new ManageUserDTO();
        model.addAttribute("manageUserDTO", manageUserDTO);

        try {
            Semesters semesters = new Semesters();
            if(manageUserForm.getIsUpdateSemester() ==1){
                Date startDate =new SimpleDateFormat("mm/DD/yyyy").parse(manageUserForm.getStartDate());
                Date endDate =new SimpleDateFormat("mm/DD/yyyy").parse(manageUserForm.getEndDate());
                Date startDateRegister = new SimpleDateFormat("mm/DD/yyyy").parse(manageUserForm.getStartDateRegister());
                Date endDateRegister = new SimpleDateFormat("mm/DD/yyyy").parse(manageUserForm.getEndDateRegister());
                semesters.setName(manageUserForm.getNameSemester());
                semesters.setStartDate(startDate);
                semesters.setEndDate(endDate);
                semesters.setStartRegisterCapstone(startDateRegister);
                semesters.setEndRegisterCapstone(endDateRegister);
                semesters = semestersService.saveSemesters(semesters);
            } else {
                semesters = semestersService.findById(manageUserForm.getSemester());
            }
            //Import Excel
            if (file != null && !file.isEmpty()) {
                String message = "";

                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                String SHEET = "Sheet1";
                Sheet sheet = workbook.getSheet(SHEET);
                if (ExcelHelper.hasExcelFormat(file)) {
                    List<String> errorList = ExcelHelper.checkErrorExcelUserImport(sheet);
                    if (errorList != null && errorList.size() > 0) {
                        workbook.close();
                        model.addAttribute("checkUpdateError", true);
                        model.addAttribute("checkUpdateSuccess", false);
                        model.addAttribute("errorList", errorList);
                        workbook.close();
                        return "admin/manage-user";
                    }
                    List<Users> usersList = ExcelHelper.excelToUsers(sheet);
                    Status status = null;
                    Sites sites = sitesService.findById(manageUserForm.getSite());
                    List<Roles> roleList = roleService.findAll();

                    if(manageUserForm.getRole() == 2){
                        status = statusService.getStatusById(manageUserForm.getStatus());
                    }else {
                        status = statusService.getStatusById(18);
                    }
                    List<Users> usersListTemp = new ArrayList<>();

                    for (Users users : usersList) {
                        List<UserRoles> userRolesList = new ArrayList<>();
                        users.setSemester(semesters);
                        users.setSite(sites);

                        for(Roles roles : roleList){
                            if(manageUserForm.getRole() == 6) {
                                if (roles.getName().equals(Constant.ROLE_HEAD_DB) || roles.getName().equals(Constant.ROLE_LECTURERS_DB)) {
                                    UserRoles userRoles = new UserRoles();
                                    UserRoleKey userRoleKey = new UserRoleKey();
                                    userRoleKey.setRole(roles);
                                    userRoleKey.setUser(users);
                                    userRoles.setUserRoleKey(userRoleKey);
                                    userRolesList.add(userRoles);
                                }
                            }else {
                                if(roles.getId().equals(manageUserForm.getRole())){
                                    UserRoles userRoles = new UserRoles();
                                    UserRoleKey userRoleKey = new UserRoleKey();
                                    userRoleKey.setRole(roles);
                                    userRoleKey.setUser(users);
                                    userRoles.setUserRoleKey(userRoleKey);
                                    userRolesList.add(userRoles);
                                    break;
                                }
                            }

                        }
                        users.setRoleUser(userRolesList);
                        if(status != null) {
                            users.setStatus(status);
                        }
                        Date date = new Date();
                        users.setCreatedDate(date);
                        users.setImage("/image/profile/defaultAvatar.jpg");
                        usersListTemp.add(users);
                    }
                    userService.saveAll(usersListTemp);
                } else {
                    workbook.close();
                    model.addAttribute("checkUpdateError", true);
                    model.addAttribute("checkUpdateSuccess", false);
                    model.addAttribute("formartExcel", "You must upload the correct template file.");
                    return "admin/manage-user";
                }
                workbook.close();
            }else {
                model.addAttribute("checkUpdateError", true);
                model.addAttribute("checkUpdateSuccess", false);
                model.addAttribute("formartExcel", "You must upload the correct template file.");
                return "admin/manage-user";
            }
        } catch (Exception e) {
            model.addAttribute("checkUpdateError", true);
            model.addAttribute("checkUpdateSuccess", false);
            model.addAttribute("checkSemester", "Semester must not blank and date of semester correct format.");
            return "admin/manage-user";

        }
        model.addAttribute("checkUpdateError", false);
        model.addAttribute("checkUpdateSuccess", true);
        if(manageUserForm.getRole() == 2) {
            model.addAttribute("checkClickDetail", true);
        }else {
            model.addAttribute("checkClickDetail", false);
        }
        return "admin/manage-user";
    }

}
