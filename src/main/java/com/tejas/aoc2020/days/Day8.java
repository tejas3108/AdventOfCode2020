package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Day8 {
    private static List<Instruction> instructionList;
    private static int accumulator;
    private static final String NO_OPERATION = "nop";
    private static final String ACCUMULATE = "acc";
    private static final String JUMP = "jmp";

    private static class Instruction {
        private String operation;
        private int argument;
        private int executionCount;

        public Instruction(String operation, int argument) {
            this.operation = operation;
            this.argument = argument;
            this.executionCount = 0;
        }

        public String getOperation() {
            return operation;
        }

        public int getArgument() {
            return argument;
        }

        public int getExecutionCount() {
            return executionCount;
        }

        public void setExecutionCount(int executionCount) {
            this.executionCount = executionCount;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }
    }

    public static int part1(String fileName) {
        readCode(fileName);
        execute();
        return accumulator;
    }

    public static int part2(String fileName) {
        readCode(fileName);
        execute();
        //store copy of list after first execution
        List<Instruction> instructionListCopy = new ArrayList<>();
        for(Instruction instruction:instructionList) {
            instructionListCopy.add(instruction);
        }

        for(int i=instructionListCopy.size()-1;i>=0;i--) {
            if(instructionListCopy.get(i).getExecutionCount() > 0 && (instructionListCopy.get(i).getOperation().equals(JUMP) || instructionListCopy.get(i).getOperation().equals(NO_OPERATION))){
                readCode(fileName);
                if(instructionList.get(i).getOperation().equals(JUMP)) instructionList.get(i).setOperation(NO_OPERATION);
                else instructionList.get(i).setOperation(JUMP);
                if(execute() == -1) {
                    int x = 1;
                    break;
                }
            }
        }

        return accumulator;
    }

    private static int execute() {
        int instructionIndex = 0;
        while(instructionIndex < instructionList.size()) {
            Instruction curInstruction = instructionList.get(instructionIndex);
            if(curInstruction.getExecutionCount() > 0) return instructionIndex;
            curInstruction.setExecutionCount(curInstruction.getExecutionCount()+1);
            switch (curInstruction.getOperation()) {
                case NO_OPERATION:
                    instructionIndex++;
                    break;
                case ACCUMULATE:
                    accumulator += curInstruction.getArgument();
                    instructionIndex++;
                    break;
                case JUMP:
                    instructionIndex += curInstruction.getArgument();
                    break;
            }
        }
        return -1;
    }

    private static void readCode(String fileName) {
        init();
        String[] input = FileUtil.readFileContent(fileName);
        for(String instruction:input) {
            instructionList.add(new Instruction(instruction.split(" ")[0], Integer.parseInt(instruction.split(" ")[1])));
        }
    }

    private static void init() {
        instructionList = new ArrayList<>();
        accumulator = 0;
    }
}
