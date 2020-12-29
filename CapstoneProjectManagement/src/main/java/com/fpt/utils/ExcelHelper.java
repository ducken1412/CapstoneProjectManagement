
package com.fpt.utils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import com.fpt.dto.CapstoneProjectDetailBody;
import com.fpt.dto.CapstoneProjectPagingBodyDTO;
import com.fpt.dto.TaskDetailsDTO;
import com.fpt.dto.UserManagementDTO;
import com.fpt.entity.TaskDetails;
import com.fpt.entity.Users;
import com.fpt.service.TaskDetailsService;
import org.apache.catalina.User;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
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


    public static final int COLUMN_INDEX_SUBJECT_CODE = 0;
    public static final int COLUMN_INDEX_NAME_CAP      = 1;
    public static final int COLUMN_INDEX_NAME_VI      = 2;
    public static final int COLUMN_INDEX_NAME_CHANGING	= 3;
    public static final int COLUMN_INDEX_NAME_CHANGING_VI = 4;
    public static final int COLUMN_INDEX_NAME_ABBREVIATION = 5;
    public static final int COLUMN_INDEX_CAP_DESCRIPTION = 6;
    public static final int COLUMN_INDEX_CAP_STATUS = 7;

    private static CellStyle cellStyleFormatNumber = null;

    public static final int COLUMN_INDEX_USERNAME = 0;
    public static final int COLUMN_INDEX_ROLE      = 1;
    public static final int COLUMN_INDEX_NAME_STUDENT     = 2;
    public static final int COLUMN_INDEX_PHONE	= 3;
    public static final int COLUMN_INDEX_EMAIL = 4;
    public static final int COLUMN_STATUS_CAP_DETAIL = 5;
    public final String excelFilePath = "D:/demo/books.xlsx";

    public static final int COLUMN_INDEX_Student_ID = 0;
    public static final int COLUMN_INDEX_Username     = 1;
    public static final int COLUMN_INDEX_NAME_Full_Name     = 2;
    public static final int COLUMN_INDEX_Gender	= 3;
    public static final int COLUMN_INDEX_Created_Date = 4;
    public static final int COLUMN_STATUS_Email = 5;
    public static final int COLUMN_STATUS_Capstone_Project = 5;
    public static final int COLUMN_STATUS_Status = 6;
    public static final int COLUMN_STATUS_Semester = 7;
    public static final int COLUMN_STATUS_Site = 8;




    public static void writeExcel(List<CapstoneProjectPagingBodyDTO> capstoneProjectPagingBodyDTO, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Manage Capstone Project"); // Create sheet with sheet name

        int rowIndex = 0;



        // Write header Cap
        writeHeader(sheet, rowIndex);
        rowIndex++;

        for (CapstoneProjectPagingBodyDTO capstoneProject : capstoneProjectPagingBodyDTO) {

            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data cap on row
            writeCapstone(sheet,capstoneProject, row);
            rowIndex ++;
            writeHeaderProjectDetail(sheet,rowIndex);
            rowIndex++;
            for (CapstoneProjectDetailBody capstoneProjectDetail : capstoneProject.getDetail()) {
                Row rowCapDetail = sheet.createRow(rowIndex);
                writeCapstoneDetail(capstoneProjectDetail,rowCapDetail);
                rowIndex++;
            }
            if(capstoneProject.getDetail() == null || capstoneProject.getDetail().size() == 0) {
                rowIndex ++;
            }

        }


        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }


    public static void writeExcelUser(List<UserManagementDTO> userManagementDTOS, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Users"); // Create sheet with sheet name

        int rowIndex = 0;


        // Write header User
        writeHeaderUser(sheet, rowIndex);
        rowIndex++;

        for (UserManagementDTO userManagementDTO : userManagementDTOS) {

            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data user on row
            writeManageUser(sheet,userManagementDTO, row);
            rowIndex ++;

        }

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }

    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Write header with format of manage user
    private static void writeHeaderUser(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_Student_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Student ID");

        cell = row.createCell(COLUMN_INDEX_Username);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Username");

        cell = row.createCell(COLUMN_INDEX_NAME_Full_Name);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Full Name");

        cell = row.createCell(COLUMN_INDEX_Gender);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Gender");

        cell = row.createCell(COLUMN_INDEX_Created_Date);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Created Date");

        cell = row.createCell(COLUMN_STATUS_Email );
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Email");

        cell = row.createCell(COLUMN_STATUS_Capstone_Project);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Capstone Project");

        cell = row.createCell(COLUMN_STATUS_Status );
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Status");

        cell = row.createCell(COLUMN_STATUS_Semester );
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Semester ");

        cell = row.createCell(COLUMN_STATUS_Site);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Site");

    }

    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_SUBJECT_CODE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("SUBJECT CODE");

        cell = row.createCell(COLUMN_INDEX_NAME_CAP);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NAME CAPSTONE PROJECT");

        cell = row.createCell(COLUMN_INDEX_NAME_VI);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NAME VI");

        cell = row.createCell(COLUMN_INDEX_NAME_CHANGING);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NAME CHANGING");

        cell = row.createCell(COLUMN_INDEX_NAME_CHANGING_VI);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NAME CHANGING VI");

        cell = row.createCell(COLUMN_INDEX_NAME_ABBREVIATION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("NAME_ABBREVIATION");

        cell = row.createCell(COLUMN_INDEX_CAP_DESCRIPTION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("DESCRIPTION");

        cell = row.createCell(COLUMN_INDEX_CAP_STATUS);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("STATUS CAPSTONE PROJECT");

    }



    // Write header with format
    private static void writeHeaderProjectDetail(Sheet sheet, int rowIndex) {
        // create CellStyle
        //CellStyle cellStyle = createStyleForHeaderCapDetail(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_USERNAME);
        //cell.setCellStyle(cellStyle);
        cell.setCellValue("USERNAME");

        cell = row.createCell(COLUMN_INDEX_ROLE);
        //cell.setCellStyle(cellStyle);
        cell.setCellValue("ROLE");

        cell = row.createCell(COLUMN_INDEX_NAME_STUDENT);
       // cell.setCellStyle(cellStyle);
        cell.setCellValue("NAME");

        cell = row.createCell(COLUMN_INDEX_PHONE);
        //cell.setCellStyle(cellStyle);
        cell.setCellValue("PHONE");

        cell = row.createCell(COLUMN_INDEX_EMAIL);
        //cell.setCellStyle(cellStyle);
        cell.setCellValue("EMAIL");

        cell = row.createCell(COLUMN_STATUS_CAP_DETAIL );
        //cell.setCellStyle(cellStyle);
        cell.setCellValue("STATUS");

    }

    // Write data Manage User
    private static void writeManageUser(Sheet sheet,UserManagementDTO user, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");
            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }


        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_Student_ID);
        cell.setCellValue(user.getId());

        cell = row.createCell(COLUMN_INDEX_Username);
        cell.setCellValue(user.getUserName());

        cell = row.createCell(COLUMN_INDEX_NAME_Full_Name);
        cell.setCellValue(user.getFirstName() + " " + user.getLastName());

        cell = row.createCell(COLUMN_INDEX_Gender);
        if(user.getGender() == 1) {
            cell.setCellValue("Male");
        }else  {
            cell.setCellValue("Female");
        }

        cell = row.createCell(COLUMN_INDEX_Created_Date);
        cell.setCellValue(user.getCreatedDate());

        cell = row.createCell(COLUMN_STATUS_Email );
        cell.setCellValue(user.getEmail());

        cell = row.createCell(COLUMN_STATUS_Capstone_Project);
        cell.setCellValue(user.getNameCapstone());

        cell = row.createCell(COLUMN_STATUS_Status);
        cell.setCellValue(user.getStatus());


        cell = row.createCell(COLUMN_STATUS_Semester );
        cell.setCellValue(user.getSemester());

        cell = row.createCell(COLUMN_STATUS_Site);
        cell.setCellValue(user.getSite());
    }

    // Write data
    private static void writeCapstone(Sheet sheet,CapstoneProjectPagingBodyDTO cap, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");
            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }
        CellStyle cellStyle = createStyleForHeaderCapDetail(sheet);


        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_SUBJECT_CODE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cap.getSubjectCode());

        cell = row.createCell(COLUMN_INDEX_NAME_CAP);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cap.getName());

        cell = row.createCell(COLUMN_INDEX_NAME_VI);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cap.getName_vi());

        cell = row.createCell(COLUMN_INDEX_NAME_CHANGING);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cap.getNameChanging());

        cell = row.createCell(COLUMN_INDEX_NAME_CHANGING_VI);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cap.getNameChangingVi());

        cell = row.createCell(COLUMN_INDEX_NAME_ABBREVIATION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cap.getName_abbreviation());

        cell = row.createCell(COLUMN_INDEX_CAP_DESCRIPTION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cap.getDescription());

        cell = row.createCell(COLUMN_INDEX_CAP_STATUS);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(cap.getNameStatus());
    }

    // Write data
    private static void writeCapstoneDetail(CapstoneProjectDetailBody capDetail, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");
            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        // Create cells
        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_USERNAME);
        cell.setCellValue(capDetail.getUser_name());

        cell = row.createCell(COLUMN_INDEX_ROLE);
        cell.setCellValue(capDetail.getRolename());

        cell = row.createCell(COLUMN_INDEX_NAME_STUDENT);
        cell.setCellValue(capDetail.getFirst_name() + " " +capDetail.getLast_name());

        cell = row.createCell(COLUMN_INDEX_PHONE);
        cell.setCellValue(capDetail.getPhone());

        cell = row.createCell(COLUMN_INDEX_EMAIL);
        cell.setCellValue(capDetail.getEmail());

        cell = row.createCell(COLUMN_STATUS_CAP_DETAIL );
        cell.setCellValue(capDetail.getNameStatus());
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeaderCapDetail(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }



    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
}
