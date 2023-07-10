package com.beyond.watchservice.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: FileUploadUtils
 * @Description: 文件上传工具类之一
 * @author: JJ
 * @date: 2022/2/8 1:25 PM
 */

public class FileUploadUtils {

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int FILE_NAME_MAX = 100;

    /**
     * 上传文件后缀名是否为pdf
     */
    private static final String PDF = "pdf";


    /**
     * 按照默认的配置上传文件
     *
     * @param file 文件
     * @return 文件名
     * @throws IOException
     */
    public static final File upload(String defaultBaseFile,MultipartFile file) throws Exception {

        try {

            //调用合法性校验
            assertAllowed(file);
            //调用文件名处理
            String newFileName = encodingFileName(file);
            //调用绝对路径文件
            File descFile = getAbsoluteFile(defaultBaseFile,newFileName);
            //将接收到的文件传输到给定的目标文件
            file.transferTo(descFile);
            return descFile;

        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * 绝对路径文件
     * @param uploadDir
     * @param fileName
     * @return File
     * @throws
     */
    private static final File getAbsoluteFile(String uploadDir, String fileName)  {

        File descFile = new File(uploadDir + File.separator + fileName);

        //测试此抽象路径名表示的文件或目录是否存在
        if (!descFile.exists()) {
            //创建此抽象路径名指定的目录，包括创建必需但不存在的父目录
            descFile.mkdirs();
        }
        return descFile;

    }





    /**
     * 对文件名特殊处理一下
     *
     * @param file 文件
     * @return
     */
    private static String encodingFileName(MultipartFile file) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datePath = simpleDateFormat.format(new Date());
        return datePath + "-" + UUID.randomUUID().toString() + "." + PDF;
    }


    /**
     * 文件合法性校验
     *
     * @param file 上传的文件
     * @return
     */
    private static void assertAllowed(MultipartFile file)
            throws Exception {

        if (file.getOriginalFilename() != null) {
            int fileNameLength = file.getOriginalFilename().length();

            if (fileNameLength > FILE_NAME_MAX) {
                throw new Exception("文件名过长");
            }

            if (!file.getOriginalFilename().endsWith(PDF)){
                throw new Exception("请上传PDF文件");
            }
        }

        long size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new Exception("文件过大");
        }

    }


    /**
     * 删除文件
     * @param filePath 文件路径
     * @return 是否成功
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


}
