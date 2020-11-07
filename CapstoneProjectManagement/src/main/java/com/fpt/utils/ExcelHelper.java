
package com.fpt.utils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fpt.dto.TaskDetailsDTO;
import com.fpt.entity.TaskDetails;
import com.fpt.service.TaskDetailsService;
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
        try {
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
                            taskDetail.setTimeTracking(Integer.parseInt(getDataFromExcel(currentCell).replace("%", "")));
                            break;

                        case "status":
                            taskDetail.setStatus(getDataFromExcel(currentCell));
                            break;

                        case "timespent+remainingestimate":
                            taskDetail.setTimeSpent(getDataFromExcel(currentCell));
                            break;
                        case "issuetype":
                            checkTask = false;
                            break;
                        case "startdate":
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            taskDetail.setStartDate(df.parse(getDataFromExcel(currentCell)));
                            break;
                        case "enddate":
                            DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                            taskDetail.setEndDate(df1.parse(getDataFromExcel(currentCell)));
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
        } catch (ParseException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
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
                throw new IllegalStateException("Unexpected value: " + cell.getCellType());
        }
        return result.toString();
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

                    case "timespent+remainingestimate":
                        countTimespent = countTimespent + 1;
                        break;
                    case "issuetype":
                        countIssueType = countIssueType + 1;
                        checkTask = false;
                        break;
                    case "startdate":

                        countStartDate = countStartDate + 1;
                        break;
                    case "enddate":
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
