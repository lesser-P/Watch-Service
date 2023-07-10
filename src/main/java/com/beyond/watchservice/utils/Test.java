package com.beyond.watchservice.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        String filePath = "/Users/yeninghui/Desktop/testUploadFile/test.xlsx";

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)))) {
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            // 获取标题行
            Row headerRow = sheet.getRow(0);
            int weChatColumnIndex = -1;
            int contentColumnIndex = -1;

            // 查找微信号和内容列的索引
            for (Cell cell : headerRow) {
                String cellValue = cell.getStringCellValue();
                if (cellValue.equalsIgnoreCase("微信号")) {
                    weChatColumnIndex = cell.getColumnIndex();
                } else if (cellValue.equalsIgnoreCase("内容")) {
                    contentColumnIndex = cell.getColumnIndex();
                }
            }

            if (weChatColumnIndex == -1 || contentColumnIndex == -1) {
                // 未找到微信号或内容列，处理错误逻辑
                System.out.println("未找到微信号或内容列");
                return;
            }

            // 从第二行开始遍历数据行
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row dataRow = sheet.getRow(rowIndex);
                Cell weChatCell = dataRow.getCell(weChatColumnIndex);
                Cell contentCell = dataRow.getCell(contentColumnIndex);

                // 提取微信号和内容值
                String weChat = weChatCell.getStringCellValue();
                String content = contentCell.getStringCellValue();

                // 处理提取到的微信号和内容
                System.out.println("微信号：" + weChat + "，内容：" + content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

