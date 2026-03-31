package com.hcmunre.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Đọc toàn bộ dữ liệu từ một sheet và trả về Object[][] cho TestNG DataProvider
     */
    public Object[][] getSheetData(String sheetName) {
        List<Object[]> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' không tồn tại trong file: " + filePath);
            }

            int rowCount = sheet.getLastRowNum();
            Row headerRow = sheet.getRow(0);
            int colCount = headerRow.getLastCellNum();

            // Bắt đầu từ dòng 1 (bỏ qua header)
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                Object[] rowData = new Object[colCount];
                for (int j = 0; j < colCount; j++) {
                    rowData[j] = getCellValue(row.getCell(j));
                }
                dataList.add(rowData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList.toArray(new Object[0][0]);
    }

    /**
     * Xử lý lấy giá trị Cell theo 4 kiểu dữ liệu yêu cầu: STRING, NUMERIC, BOOLEAN,
     * FORMULA
     * Và xử lý Cell null.
     */
    private Object getCellValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                // Trả về String để tránh lỗi .0 của số nguyên
                double numericValue = cell.getNumericCellValue();
                if (numericValue == (long) numericValue) {
                    return String.valueOf((long) numericValue);
                }
                return String.valueOf(numericValue);
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                // Trả về giá trị kết quả của công thức
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
