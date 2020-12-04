package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.*;

public class Day4 {
    private static final String[] reqdFields = {"byr","iyr","eyr","hgt","hcl","ecl","pid"};

    public static int part1(String fileName) {
        List<Map<String, String>> passports = parseBatchFile(fileName);
        int validPassports = 0;

        Set<String> requiredFields = new HashSet<>(Arrays.asList(reqdFields));
        for(Map<String, String> passport:passports) {
            if(passportHasRequiredFields(passport, requiredFields)) validPassports++;
        }

        return validPassports;
    }

    public static int part2(String fileName) {
        List<Map<String, String>> passports = parseBatchFile(fileName);
        int validPassports = 0;

        Set<String> requiredFields = new HashSet<>(Arrays.asList(reqdFields));
        for(Map<String, String> passport:passports) {
            if(passportHasRequiredFields(passport, requiredFields) && passportHasValidData(passport, requiredFields)) validPassports++;
        }

        return validPassports;
    }


    private static boolean passportHasRequiredFields(Map<String, String> data, Set<String> requiredFields) {
        for(String requiredField:requiredFields) {
            if(!data.containsKey(requiredField)) return false;
        }

        return true;
    }

    private static boolean passportHasValidData(Map<String, String> data, Set<String> requiredFields){
        try{
            int byr = Integer.parseInt(data.get("byr"));
            int iyr = Integer.parseInt(data.get("iyr"));
            int eyr = Integer.parseInt(data.get("eyr"));
            String hgt = data.get("hgt");
            String hcl = data.get("hcl");
            String ecl = data.get("ecl");
            String pid = data.get("pid");

            //check byr
            if(byr < 1920 || byr > 2002) return false;

            //check iyr
            if(iyr < 2010 || iyr > 2020) return false;

            //check eyr
            if(eyr < 2020 || eyr > 2030) return false;

            //check hgt
            if(!hgt.endsWith("cm") && !hgt.endsWith("in")) return false;
            int height = Integer.parseInt(hgt.substring(0, hgt.length()-2));
            if(hgt.endsWith("cm")) {
                if(height < 150 || height > 193) return false;
            }
            else {
                if(height < 59 || height > 76) return false;
            }

            //check hcl
            if(hcl.length() != 7 || !hcl.startsWith("#")) return false;
            Character[] validHcl = {'a','b','c','d','e','f'};
            Set<Character> hclSet = new HashSet<>(Arrays.asList(validHcl));
            for(int i=1;i<hcl.length();i++) {
                if(!Character.isDigit(hcl.charAt(i)) && !hclSet.contains(hcl.charAt(i))) return false;
            }

            //check ecl
            String[] validEcl = {"amb","blu","brn","gry","grn","hzl","oth"};
            Set<String> eclSet = new HashSet<>(Arrays.asList(validEcl));
            if(!eclSet.contains(ecl)) return false;

            //check pid
            if(pid.length() != 9) return false;
            for(int i=0;i<pid.length();i++) {
                if(!Character.isDigit(pid.charAt(i))) return false;
            }
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    private static List<Map<String, String>> parseBatchFile(String fileName) {
        List<String> lines =  FileUtil.readAllLines(fileName);
        List<Map<String, String>> passports = new ArrayList<>();
        List<String> currentPassport = new ArrayList<>();

        for(String line:lines) {
            if(line.trim().isEmpty()) {
                Map<String, String> passport = parsePassportString(currentPassport);
                if(passport != null) passports.add(passport);
                currentPassport.clear();
            }
            else {
                currentPassport.add(line);
            }
        }
        //handle last passport
        Map<String, String> passport = parsePassportString(currentPassport);
        if(passport != null) passports.add(passport);

        return passports;
    }

    private static Map<String, String> parsePassportString (List<String> content) {
        if(content.size() == 0) return null;
        Map<String, String> data = new HashMap<>();
        for(String dataString:content) {
            String[] arr = dataString.split(" ");
            for(String credentials:arr) {
                data.put(credentials.split(":")[0], credentials.split(":")[1]);
            }
        }

        return data;
    }
}
