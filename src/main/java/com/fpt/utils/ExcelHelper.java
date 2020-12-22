
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

import com.fpt.dto.CapstoneProjectPagingBodyDTO;
import com.fpt.dto.TaskDetailsDTO;
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

    public static final int COLUMN_INDEX_USERNAME = 0;
    public static final int COLUMN_INDEX_ROLE      = 1;
    public static final int COLUMN_INDEX_NAME_STUDENT     = 2;
    public static final int COLUMN_INDEX_PHONE	= 3;
    public static final int COLUMN_INDEX_EMAIL = 4;
    public static final int COLUMN_STATUS_CAP_DETAIL = 5;

    private static CellStyle cellStyleFormatNumber = null;



    public static void writeExcel(List<CapstoneProjectPagingBodyDTO> capstoneProjectPagingBodyDTO, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("Manage Capstone Project"); // Create sheet with sheet name

        int rowIndex = 0;

        // Write header
        writeHeader(sheet, rowIndex);

        // Write data
        rowIndex++;
        for (CapstoneProjectPagingBodyDTO capstoneProject : capstoneProjectPagingBodyDTO) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            ///writeBook(users, row);
            rowIndex++;
        }


        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }

    // Create dummy data
    /*private static List<Book> getBooks() {
        List<Book> listBook = new ArrayList<>();
        Book book;
        for (int i = 1; i <= 5; i++) {
            book = new Book(i, "Book " + i, i * 2, i * 1000);
            listBook.add(book);
        }
        return listBook;
    }*/

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


        /*cell = row.createCell(COLUMN_INDEX_PRICE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Price");

        cell = row.createCell(COLUMN_INDEX_QUANTITY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Quantity");

        cell = row.createCell(COLUMN_INDEX_TOTAL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Total money");*/
    }

    // Write data
    private static void writeBook(Users users, Row row) {
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

        //Cell cell = row.createCell(COLUMN_INDEX_ID);
        /*cell.setCellValue(book.getId());

        cell = row.createCell(COLUMN_INDEX_TITLE);
        cell.setCellValue(book.getTitle());

        cell = row.createCell(COLUMN_INDEX_PRICE);
        cell.setCellValue(book.getPrice());
        cell.setCellStyle(cellStyleFormatNumber);

        cell = row.createCell(COLUMN_INDEX_QUANTITY);
        cell.setCellValue(book.getQuantity());*/
        // Create cell formula
        // totalMoney = price * quantity
        /*cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
        cell.setCellStyle(cellStyleFormatNumber);
        int currentRow = row.getRowNum() + 1;
        String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
        String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);*/
        //cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
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
