package com.app.project.util;

import java.io.*;

public class FileUtilImpl implements FileUtil {


    @Override
    public String fileContent(String path) throws IOException {

        String line;
        StringBuilder sb = new StringBuilder();

        File file = new File(path);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        while((line = bufferedReader.readLine()) != null){

            sb.append(line).append(System.lineSeparator());
        }

        bufferedReader.close();

        return sb.toString().trim();
    }

    @Override
    public void exportJson(String path, String content) throws IOException {

        File file = new File(path);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(content);
        bufferedWriter.close();

    }
}
