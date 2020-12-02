package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day2 {

    private static class Password {
        private int lowerLimit;
        private int upperLimit;
        private char policyChar;
        private String password;

        public Password(String input) {
            String[] inputSplit = input.split(" ");
            this.lowerLimit = Integer.parseInt(inputSplit[0].split("-")[0]);
            this.upperLimit = Integer.parseInt(inputSplit[0].split("-")[1]);

            this.policyChar = inputSplit[1].charAt(0);

            this.password = inputSplit[2];
        }

        public int getLowerLimit() {
            return lowerLimit;
        }

        public int getUpperLimit() {
            return upperLimit;
        }

        public char getPolicyChar() {
            return policyChar;
        }

        public String getPassword() {
            return password;
        }
    }

    private static List<Password> readFile(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);

        List<Password> passwordList = new ArrayList<>();
        for(String pwdString:input) {
            passwordList.add(new Password(pwdString));
        }

        return passwordList;
    }

    public static int part1(String fileName) {
        List<Password> passwordList = readFile(fileName);
        int[] counts = new int[26];
        int validPasswords = 0;

        for(Password password:passwordList) {
            Arrays.fill(counts,0);
            for(char c:password.getPassword().toCharArray()) {
                counts[c-'a']++;
            }
            if(counts[password.getPolicyChar()-'a'] >= password.getLowerLimit() && counts[password.getPolicyChar()-'a'] <= password.getUpperLimit()) {
                validPasswords++;
            }
        }

        return validPasswords;
    }

    public static int part2(String fileName) {
        List<Password> passwordList = readFile(fileName);
        int firstLocation = 0, secondLocation = 0;
        String pwd;
        int validPasswords = 0;

        for(Password password:passwordList) {
            firstLocation = password.getLowerLimit() - 1; //to account for zero indexing
            secondLocation = password.getUpperLimit() - 1;
            pwd = password.getPassword();

            if((pwd.charAt(firstLocation) == password.getPolicyChar() && pwd.charAt(secondLocation) != password.getPolicyChar()) ||
                    (pwd.charAt(firstLocation) != password.getPolicyChar() && pwd.charAt(secondLocation) == password.getPolicyChar())
            ) {
                validPasswords++;
            }
        }

        return validPasswords;
    }
}
