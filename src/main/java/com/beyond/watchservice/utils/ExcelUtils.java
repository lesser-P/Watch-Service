package com.beyond.watchservice.utils;

import com.beyond.watchservice.model.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtils {
    private static final String XLSX = "xlsx";

    public static void checkFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            //文件为空
            log.error("Excel文件不存在!");
            throw new Exception("Excel文件不存在!");
        } else {
            //存在，判断后缀名是否为XLSX
            if (!file.getOriginalFilename().endsWith(XLSX)) {
                log.error("Excel文件不是xlsx格式");
                throw new IOException(file.getOriginalFilename() + "文件不是xlsx格式");
            }
        }

    }

    public static List<UserVo> extractDataFormExcel(MultipartFile file) {

        List<UserVo> userVos = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
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
                return null;
            }


            // 从第二行开始遍历数据行
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row dataRow = sheet.getRow(rowIndex);
                Cell weChatCell = dataRow.getCell(weChatColumnIndex);
                Cell contentCell = dataRow.getCell(contentColumnIndex);

                // 提取微信号和内容值
                String weChat = weChatCell.getStringCellValue();
                String content = contentCell.getStringCellValue();
                UserVo userVo = new UserVo();

                userVo.setVx(weChat);
                userVo.setContent(content);

                userVos.add(userVo);

                // 处理提取到的微信号和内容
                System.out.println("微信号：" + weChat + "，内容：" + content);

            }
            return userVos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userVos;
    }
}

