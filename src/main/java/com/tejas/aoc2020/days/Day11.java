package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Day11 {
    public static class SeatLayout {
        private char[][] grid;
        private int occupiedSeatCount;

        public char[][] getGrid() {
            return grid;
        }

        public SeatLayout(char[][] grid) {
            this.grid = grid;
        }

        public int getOccupiedSeatCount() {
            for(int i=0;i<grid.length;i++) {
                for(int j=0;j<grid[0].length;j++) {
                    if(grid[i][j] == '#') occupiedSeatCount++;
                }
            }

            return occupiedSeatCount;
        }

        public boolean equals(SeatLayout inputGrid) {
            for(int i=0;i<grid.length;i++) {
                for(int j=0;j<grid[0].length;j++) {
                    if(grid[i][j] != inputGrid.getGrid()[i][j]) return false;
                }
            }

            return true;
        }
    }
    public static int part1(String fileName) {
        return execute(fileName,true);
    }

    public static int part2(String fileName) {
        return execute(fileName,false);
    }

    private static int execute(String fileName, boolean isPart1) {
        char[][] inputGrid = readFile(fileName);
        SeatLayout currentLayout = new SeatLayout(inputGrid);
        SeatLayout nextState= new SeatLayout(getNextState(currentLayout.getGrid(), isPart1));

        while(true) {
            if(currentLayout.equals(nextState)) break;
            currentLayout = nextState;
            nextState = new SeatLayout(getNextState(currentLayout.getGrid(), isPart1));
        }

        return nextState.getOccupiedSeatCount();
    }

    private static char[][] getNextState(char[][] currentState, boolean isPart1) {
        char[][] nextState = new char[currentState.length][currentState[0].length];
        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0},{-1,1},{1,1},{1,-1},{-1,-1}};
        int neighborOccupiedCount;
        for(int i=0;i<currentState.length;i++) {
            for(int j=0;j<currentState[0].length;j++) {
                neighborOccupiedCount = 0;
                for(int[] dir:dirs) {
                    if(isPart1) {
                        if(isOccupiedNeighborPart1(i,j,dir,currentState.length,currentState[0].length,currentState)){
                            neighborOccupiedCount++;
                        }
                    }
                    else{
                        if(isOccupiedNeighborPart2(i,j,dir,currentState.length,currentState[0].length,currentState)){
                            neighborOccupiedCount++;
                        }
                    }
                }
                if(currentState[i][j] == 'L' && neighborOccupiedCount==0) nextState[i][j] = '#';
                else if(isPart1 && currentState[i][j] == '#' && neighborOccupiedCount>=4) nextState[i][j] = 'L';
                else if(!isPart1 && currentState[i][j] == '#' && neighborOccupiedCount>=5) nextState[i][j] = 'L';
                else nextState[i][j] = currentState[i][j];
            }
        }

        return nextState;
    }

    private static boolean isOccupiedNeighborPart1(int row, int col, int[] dir, int rows, int cols, char[][] currentState) {
        return (!isOutOfRange(row+dir[0], col+dir[1], rows, cols) && currentState[row+dir[0]][col+dir[1]] == '#');
    }

    private static boolean isOccupiedNeighborPart2(int row, int col, int[] dir, int rows, int cols, char[][] currentState) {
        if(isOutOfRange(row+dir[0], col+dir[1], rows, cols) || currentState[row+dir[0]][col+dir[1]] == 'L') return false;
        if(currentState[row+dir[0]][col+dir[1]] == '#') return true;
        return isOccupiedNeighborPart2(row+dir[0], col+dir[1], dir, rows, cols, currentState);
    }

    private static boolean isOutOfRange(int x, int y, int rows, int cols) {
        return x < 0 || x >= rows || y < 0 || y >= cols;
    }

    private static char[][] readFile(String fileName) {
        List<String> input = FileUtil.readAllLines(fileName);
        char[][] grid = new char[input.size()][input.get(0).length()];
        int row = 0;
        for(String line:input) {
            for(int i=0;i<line.length();i++) {
                grid[row][i] = line.charAt(i);
            }
            row++;
        }

        return grid;
    }
}
