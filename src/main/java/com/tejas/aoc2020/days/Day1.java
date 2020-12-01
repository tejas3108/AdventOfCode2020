package com.tejas.aoc2020.days;

import com.oracle.tools.packager.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class Day1 {
    public static int part1(String fileName) {
        ClassLoader classLoader = Day1.class.getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());

        //File is found
        System.out.println("File Found : " + file.exists());

        //Read File Content
        String content = null;
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] arr = content.split("\n");
        System.out.println(Arrays.toString(arr));

        return 0;
    }
}
