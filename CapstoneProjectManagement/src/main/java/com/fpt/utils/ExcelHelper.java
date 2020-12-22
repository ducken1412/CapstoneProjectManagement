
package com.fpt.utils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import com.fpt.dto.TaskDetailsDTO;
import com.fpt.entity.TaskDetails;
import com.fpt.entity.Users;
import com.fpt.service.TaskDetailsService;
import org.apache.catalina.User;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "Sheet1";


    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<TaskDetails> excelToStatistics(Sheet sheet) {
        /*Workbook workbook = new XSSFWorkbook(is);

        Sheet sheet = workbook.getSheet(SHEET);*/

        Iterator<Row> rows = sheet.iterator();

        List<TaskDetails> taskDetails = new ArrayList<TaskDetails>();

        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            boolean checkTask = true;

            Iterator<Cell> cellsInRow = currentRow.iterator();

            TaskDetails taskDetail = new TaskDetails();
            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();
                String header = sheet.getRow(0).getCell(cellIdx).getStringCellValue();
                if (header == null) {
                    checkTask = false;
                    cellIdx++;
                    break;
                }
                switch (header.toLowerCase().trim()) {
                    case "summary":
                        taskDetail.setSummary(getDataFromExcel(currentCell));
                        break;

                    case "assignee":
                        taskDetail.setAssignee(getDataFromExcel(currentCell));
                        break;

                    case "timetracking":
                        if(getDataFromExcel(currentCell) != null){
                            taskDetail.setTimeTracking(Integer.parseInt(getDataFromExcel(currentCell).replace("%", "")));
                        }else {
                            taskDetail.setTimeTracking(0);
                        }
                        break;

                    case "status":
                        taskDetail.setStatus(getDataFromExcel(currentCell));
                        break;

                    case "time spent + remaining estimate":
                        taskDetail.setTimeSpent(getDataFromExcel(currentCell));
                        break;
                    case "issue type":
                        if(!"Task".trim().equalsIgnoreCase(getDataFromExcel(currentCell))){
                            checkTask = false;
                        }
                        break;
                    case "start date":
                        Date startDate =  DateUtils.parseDate(getDataFromExcel(currentCell),
                            new String[] { "yyyy-MM-dd","dd/MM/yyyy","yyyy/MM/dd","dd-MM-yyyy" });
                        taskDetail.setStartDate(startDate);
                        break;
                    case "end date":
                        Date endDate =  DateUtils.parseDate(getDataFromExcel(currentCell),
                                new String[] { "yyyy-MM-dd","dd/MM/yyyy","yyyy/MM/dd","dd-MM-yyyy" });
                        taskDetail.setEndDate(endDate);
                        break;
                    default:
                        break;
                }
                cellIdx++;
            }
            if (checkTask) {
                taskDetails.add(taskDetail);

            }

        }
        // workbook.close();
        return taskDetails;
    }

    public static List<Users> excelToUsers(Sheet sheet) {

        Iterator<Row> rows = sheet.iterator();

        List<Users> usersList = new ArrayList<Users>();

        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            boolean checkTask = true;

            Iterator<Cell> cellsInRow = currentRow.iterator();

            Users users = new Users();
            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCellCheck = currentRow.getCell(cellIdx);
                String header = sheet.getRow(0).getCell(cellIdx).getStringCellValue();
                if (header == null) {
                    checkTask = false;
                    cellIdx++;
                    break;
                }
                if (currentCellCheck == null){
                    cellIdx++;
                    continue;
                }

                Cell currentCell = cellsInRow.next();



                switch (header.toLowerCase().trim()) {
                    case "id":
                        users.setId(getDataFromExcel(currentCell));
                        break;

                    case "description":
                        users.setDescription(getDataFromExcel(currentCell));
                        break;

                    case "email":
                        users.setEmail(getDataFromExcel(currentCell));
                        break;

                    case "first name":
                        users.setFirstName(getDataFromExcel(currentCell));
                        break;

                    case "gender":
                        if(getDataFromExcel(currentCell).equalsIgnoreCase("male")){
                            users.setGender(1);
                        }else {
                            users.setGender(0);
                        }
                        break;
                    case "last name":
                        users.setLastName(getDataFromExcel(currentCell));
                        break;
                    case "phone":
                        users.setPhone(getDataFromExcel(currentCell));
                        break;
                    case "user name":
                        users.setUsername(getDataFromExcel(currentCell));
                        break;
                    case "address":
                        users.setAddress(getDataFromExcel(currentCell));
                        break;
                    case "birth date":
                        Date birthDate =  DateUtils.parseDate(getDataFromExcel(currentCell),
                                new String[] { "yyyy-MM-dd","dd/MM/yyyy","yyyy/MM/dd","dd-MM-yyyy" });
                        users.setBirthDate(birthDate);
                        break;

                    default:
                        break;
                }
                cellIdx++;
            }
            if (checkTask) {
                usersList.add(users);

            }

        }
        // workbook.close();
        return usersList;
    }

    private static String getDataFromExcel(Cell cell) {
        Object result;
        switch (cell.getCellType()) {
            case STRING:
                result = cell.getStringCellValue();
                break;
            case NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case FORMULA:
                result = cell.getCellFormula();
                break;
            default:
                return null;
        }
        return result.toString().trim();
    }

    public static List<String> checkErrorExcelUserImport(Sheet sheet) {
        Iterator<Row> rows = sheet.iterator();
        int countId = 0;
        int countUserName = 0;
        int countAddress = 0;
        int countEmail = 0;
        int countFirstName= 0;
        int countGender = 0;
        int countLastName= 0;
        int countPhone = 0;
        List<String> errorList =  new ArrayList<>();

        while (rows.hasNext()) {
            int cellIdx = 0;
            Row currentRow = rows.next();
            Iterator<Cell> cellsInRow = currentRow.iterator();
            while (cellsInRow.hasNext()) {
                Cell currentCellCheck = currentRow.getCell(cellIdx);

                if(cellIdx > 9){
                    break;
                }
                String header = sheet.getRow(0).getCell(cellIdx).getStringCellValue();
                if (header == null) {
                    cellIdx++;
                    break;
                }

                if (currentCellCheck == null && !header.toLowerCase().trim().equalsIgnoreCase("description") &&
                        !header.toLowerCase().trim().equalsIgnoreCase("birth date") ){
                    errorList.add("Row :" +currentRow.getRowNum()+ " Column: "+header+ " is not null");
                    cellIdx++;
                    continue;
                }


                switch (header.toLowerCase().trim()) {
                    case "id":
                        countId = countId + 1;
                        break;

                    case "user name":
                        countUserName = countUserName + 1;
                        break;

                    case "address":
                        countAddress = countAddress + 1;
                        break;

                    case "email":
                        countEmail = countEmail + 1;
                        break;

                    case "first name":
                        countFirstName = countFirstName + 1;
                        break;
                    case "gender":
                        if(currentRow.getRowNum() !=0 && !getDataFromExcel(currentCellCheck).equalsIgnoreCase("male") &&
                                !getDataFromExcel(currentCellCheck).equalsIgnoreCase("female")){
                            errorList.add("Row :" +currentRow.getRowNum()+ " Column: "+header+ " must male or female");
                        }
                        countGender = countGender + 1;
                        break;

                    case "last name":
                        countLastName = countLastName + 1;
                        break;
                    case "phone":
                        countPhone = countPhone + 1;
                        break;
                    default:
                        break;
                }
                cellIdx++;
            }
        }

        String errorMess="";
        if(countUserName == 0){
            errorMess = errorMess +"user name,";
        }
        if(countAddress == 0){
            errorMess = errorMess +"address,";
        }
        if(countFirstName == 0){
            errorMess = errorMess +"First Name,";
        }
        if(countLastName == 0){
            errorMess = errorMess +"Last Name,";
        }
        if(countEmail == 0){
            errorMess = errorMess +"Email,";
        }
        if(countGender == 0){
            errorMess = errorMess +"Gender,";
        }
        if(countPhone == 0){
            errorMess = errorMess +"Phone,";
        }
        if(countId == 0){
            errorMess = errorMess +"Id,";
        }
        if(errorMess!= ""){
            errorList.add("You are missing the following columns: " + errorMess);
        }
        return errorList;
    }

    public static List<String> checkErrorExcelImport(Sheet sheet) {
        Iterator<Row> rows = sheet.iterator();

        int countSummary = 0;
        int countAssignee = 0;
        int countTimetracking = 0;
        int countTimespent = 0;
        int countstatus = 0;
        int countIssueType = 0;
        int countStartDate = 0;
        int countEndDate = 0;
        List<String> errorList =  new ArrayList<>();
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            Iterator<Cell> cellsInRow = currentRow.iterator();
            boolean checkTask = true;
            String columnIndex = "";
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();
                String header = sheet.getRow(0).getCell(currentCell.getColumnIndex()).getStringCellValue();

                switch (header.toLowerCase().trim()) {
                    case "summary":
                        countSummary = countSummary + 1;
                        break;

                    case "assignee":
                        countAssignee = countAssignee + 1;
                        break;

                    case "timetracking":
                        countTimetracking = countTimetracking + 1;
                        break;

                    case "status":
                        countstatus = countstatus + 1;
                        break;

                    case "time spent + remaining estimate":
                        countTimespent = countTimespent + 1;
                        break;
                    case "issuetype":
                        if(!"Task".trim().equalsIgnoreCase(getDataFromExcel(currentCell))){
                            checkTask = false;
                            break;
                        }
                        countIssueType = countIssueType + 1;
                        break;

                    case "start date":
                        countStartDate = countStartDate + 1;
                        break;
                    case "end date":
                        countEndDate = countEndDate + 1;
                        break;
                    default:
                        break;
                }
                if(getDataFromExcel(currentCell) == null){
                    if(checkTask) {
                        columnIndex = columnIndex + currentCell.getColumnIndex() + " , ";
                    }
                }

            }

            if(checkTask && columnIndex != "") {
                errorList.add("Row :" +currentRow.getRowNum()+ " Column: "+columnIndex + " is not null");
            }

        }

        String errorMess="";
        if(countSummary == 0){
            errorMess = errorMess +"summary,";
        }
        if(countAssignee == 0){
            errorMess = errorMess +"Assignee,";
        }
        if(countAssignee == 0){
            errorMess = errorMess +"TimeTracking,";
        }
        if(countAssignee == 0){
            errorMess = errorMess +"status,";
        }
        if(countAssignee == 0){
            errorMess = errorMess +"Time Spent + Remaining Estimate,";
        }
        if(countAssignee == 0){
            errorMess = errorMess +"Issue type,";
        }
        if(countAssignee == 0){
            errorMess = errorMess +"Start Date,";
        }
        if(countAssignee == 0){
            errorMess = errorMess +"End Date,";
        }
        errorList.add("You are missing the following columns: " + errorMess);
        return errorList;
    }
}
