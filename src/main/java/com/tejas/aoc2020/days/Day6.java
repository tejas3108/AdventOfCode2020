package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day6 {
    public static int part1(String fileName) {
        List<String> lines =  FileUtil.readAllLines(fileName);
        int questionsAnswered = 0;
        List<String> answersByGroup = new ArrayList<>();
        for(String line:lines) {
            if(line.trim().isEmpty()) {
               questionsAnswered += parseGroupQuestions(answersByGroup, false);
                answersByGroup.clear();
            }
            else {
                answersByGroup.add(line);
            }
        }
        //handle last group
        questionsAnswered += parseGroupQuestions(answersByGroup, false);

        return questionsAnswered;
    }

    public static int part2(String fileName) {
        List<String> lines =  FileUtil.readAllLines(fileName);
        int questionsAnswered = 0;
        List<String> answersByGroup = new ArrayList<>();
        for(String line:lines) {
            if(line.trim().isEmpty()) {
                questionsAnswered += parseGroupQuestions(answersByGroup, true);
                answersByGroup.clear();
            }
            else {
                answersByGroup.add(line);
            }
        }
        //handle last group
        questionsAnswered += parseGroupQuestions(answersByGroup, true);

        return questionsAnswered;
    }

    private static int parseGroupQuestions(List<String> answersByGroup, boolean isAnsweredByAllFlag) {
        int count = 0;
        int[] questionsCount = new int[26];
        for(String answer:answersByGroup) {
            for(char question:answer.toCharArray()){
                if(questionsCount[question - 'a'] == 0) count++;
                questionsCount[question - 'a']++;
            }
        }
        //part 1
        if(!isAnsweredByAllFlag) return count;

        //part 2 computation
        count = 0;
        for(int answeredCount:questionsCount) {
            if(answeredCount == answersByGroup.size()) count++;
        }

        return count;
    }
}
