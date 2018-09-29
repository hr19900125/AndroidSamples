package com.hr.toy.utils;

import com.hr.toy.AppApplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static String readFileFromAssert(String filePath) throws IOException {
        InputStream is = AppApplication.getContext().getAssets().open(filePath);
        StringBuffer sb = new StringBuffer();
        FileUtils.readToBuffer(sb, is);
        return sb.toString();
    }

    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        readToBuffer(buffer, is);
    }

    public static void readToBuffer(StringBuffer buffer, InputStream is) throws IOException {
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }
}
