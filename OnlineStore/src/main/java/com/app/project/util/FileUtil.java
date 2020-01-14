package com.app.project.util;

import java.io.IOException;

public interface FileUtil {

    String fileContent(String path) throws IOException;

    void exportJson(String path,String content) throws IOException;
}
