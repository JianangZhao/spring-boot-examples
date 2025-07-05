package com.neo.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class WrietExcel {
    public static void main(String[] args) {
        // 定义表头和数据列数一致的数组
        String[] headers = {"ID", "姓名", "年龄", "部门", "入职日期"};

        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("数据表");


        // 1. 写入表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);

            // 设置表头样式
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(headerStyle);
        }

        // 1. 先写入数据部分
        // 假设写入10行数据
        for (int i = 1; i <= 10; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 5; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue("数据 " + (i + 1) + "-" + (j + 1));
            }
        }

        // 获取当前sheet的最后一行(数据部分的最后一行)
        int lastDataRowAuto = sheet.getLastRowNum();
        System.out.println("自动识别数据最后一行"+lastDataRowAuto);

        // 2. 在数据下方写入其他内容
        // 计算数据最后一行之后的行号
        int lastDataRow = 9; // 0-based索引，假设数据写到第10行(索引9)

        // 在数据下方空一行
        int startRowForOtherContent = lastDataRow + 2;

        // 写入标题
        Row titleRow = sheet.createRow(startRowForOtherContent);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("其他信息");

        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(
                startRowForOtherContent, // 起始行
                startRowForOtherContent, // 结束行
                0,                      // 起始列
                4                       // 结束列
        ));

        // 设置标题样式
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short)14);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCell.setCellStyle(titleStyle);

        // 写入其他内容
        Row contentRow1 = sheet.createRow(startRowForOtherContent + 1);
        contentRow1.createCell(0).setCellValue("备注1: 这是数据下方的附加信息");

        Row contentRow2 = sheet.createRow(startRowForOtherContent + 2);
        contentRow2.createCell(0).setCellValue("备注2: 数据生成日期: " + new java.util.Date());

        // 自动调整列宽
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // ===== 第二个Sheet =====
        Sheet secondSheet = workbook.createSheet("详细数据");

        // 创建表头
        String[] headers2 = {"月份", "销售额", "增长率"};
        Row headerRow2 = secondSheet.createRow(0);

        // 表头样式
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // 设置表头
        for (int i = 0; i < headers2.length; i++) {
            Cell cell = headerRow2.createCell(i);
            cell.setCellValue(headers2[i]);
            cell.setCellStyle(headerStyle);
        }

        // 添加数据
        Object[][] data = {
                {"1月", 125000, "12.5%"},
                {"2月", 118000, "-5.6%"},
                {"3月", 145000, "22.9%"}
        };

        for (int i = 0; i < data.length; i++) {
            Row row = secondSheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j].toString());
            }
        }

        // 自动调整列宽
        for (int i = 0; i < headers2.length; i++) {
            secondSheet.autoSizeColumn(i);
        }

        // 写入文件
        try (FileOutputStream outputStream = new FileOutputStream("C:\\Users\\zhaojianang\\ideaPorject\\spring-boot-examples\\spring-boot-hello\\output.xlsx")) {
            workbook.write(outputStream);
            System.out.println("Excel文件已生成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
