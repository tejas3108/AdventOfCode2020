package com.tejas.aoc2020.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileUtil {
    public static String[] readFileContent(String fileName) {
        ClassLoader classLoader = FileUtil.class.getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());
        String content = null;

        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.split("\n");
    }

    public static List<String> readAllLines(String fileName) {
        ClassLoader classLoader = FileUtil.class.getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());
        List<String> lines = null;

        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
