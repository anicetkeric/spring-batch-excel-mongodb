package com.springbatch.excel.tutorial.support.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @param <T> generic Class
 * @author aek
 */
public abstract class AbstractExcelPoi<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractExcelPoi.class.getName());

    /**
     * @param filePath file path
     * @param aList list of object
     */
    public abstract void write(String filePath, List<T> aList);

    /**
     * @param filePathStr file path location
     * @param rowMapper RowMapper object
     * @return list of <T>
     */
    public List<T> read(String filePathStr, final RowMapper<T> rowMapper) {
        List<T> bookList = new ArrayList<>();

        try (Workbook workbook = getWorkbook(filePathStr)) {

            Sheet sheet = workbook.getSheetAt(0);

                sheet.forEach(row -> {
                    // skip header
                    if (row.getRowNum() != 0){
                        try {
                            bookList.add(rowMapper.transformerRow(row));
                        } catch (ParseException exception) {
                            LOGGER.log(Level.WARNING, "RowMapper Parse exception");
                        }
                    }
                });

            return bookList;
        } catch (IOException e) {
            throw new ExcelFileException(String.format("Cannot read the file: %s",e.getMessage()));
        }
    }

    /**
     * @param filePathStr path of file
     * @return Workbook
     */
    private Workbook getWorkbook(String filePathStr) {
        Workbook workbook;

        try{
            try (FileInputStream inputStream = new FileInputStream(new File(filePathStr))) {
                if (filePathStr.endsWith("xlsx")) {
                    workbook = new XSSFWorkbook(inputStream);
                } else {
                    throw new ExcelFileException("The specified file is not Excel file");
                }
            }
        }catch(SecurityException | IOException e){
            throw new ExcelFileException("The specified file is not Excel file");
        }

        return workbook;
    }




    /**
     * Create header of file
     * @param sheet excel sheet
     * @param headers list string of header
     * @param rowStyle style of header row
     */
    public void createHeaderRow(Sheet sheet, List<String> headers, CellStyle rowStyle) {

        if(!headers.isEmpty()){
            // Create a Row
            Row row = sheet.createRow(0);
            // Create cells
            for (int i = 0; i < headers.size(); i++) {
                createCell(row, i, headers.get(i), rowStyle);
                sheet.autoSizeColumn(i);
            }
        }

    }

    /**
     * @param row file row
     * @param columnCount index of column
     * @param value the value to will set
     * @param style cel row
     * @return Cell value
     */
    public Cell createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }


        if(style != null){
            cell.setCellStyle(style);
        }

        return cell;
    }


}
