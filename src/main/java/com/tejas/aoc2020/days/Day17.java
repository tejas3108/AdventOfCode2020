package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.*;

public class Day17 {
    private static class Cube {
        private int x;
        private int y;
        private int z;
        private int active;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getActive() {
            return active;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public void setActive(int active) {
            this.active = active;
        }

        public Cube(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.active = 0;
        }

        public Cube(int x, int y, int z, char activeChar) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.active = activeChar == '.' ? 0:1;
        }

        @Override
        public int hashCode() {
            return (String.valueOf(x) + String.valueOf(y) + String.valueOf(z)).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null) return false;
            if(!(obj instanceof Cube)) return false;
            if(obj == this) return true;
            return ((Cube)obj).getX() == x && ((Cube)obj).y == y && ((Cube)obj).z == z;
        }
    }

    private static int[][] _26Dir = new int[][]{
            {0,0,1},
            {0,1,0},
            {0,1,1},
            {1,0,0},
            {1,0,1},
            {1,1,0},
            {1,1,1},
            {0,0,-1},
            {0,-1,0},
            {0,1,-1},
            {0,-1,1},
            {0,-1,-1},
            {-1,0,0},
            {-1,0,1},
            {1,0,-1},
            {-1,0,-1},
            {1,-1,0},
            {-1,1,0},
            {-1,-1,0},
            {1,1,-1},
            {1,-1,-1},
            {1,-1,1},
            {-1,1,1},
            {-1,1,-1},
            {-1,-1,1},
            {-1,-1,-1}
    };

    private static final int CYCLES = 1;

    public static int part1(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);
        Map<Integer, Cube> curState = new HashMap<>();
        Map<Integer, Cube> nextState = new HashMap<>();
        for(int i=0;i<input.length;i++) {
            for(int j=0;j<input[i].length();j++) {
                nextState.put(getHashCode(i,j,0), new Cube(i,j,0,input[i].charAt(j)));
            }
        }
        int cycle = 0;
        while(cycle < CYCLES) {
            curState = nextState;
            nextState = new HashMap<>();
            for(Cube cube:curState.values()) {
                int localActiveCount = 0;
                for(int[] dir:_26Dir) {
                    Cube neighbor = new Cube(cube.getX()+dir[0], cube.getY()+dir[1], cube.getZ()+dir[2]);
                    int hashCode = getHashCode(neighbor.getX(),neighbor.getY(),neighbor.getZ());
                    if(!curState.containsKey(hashCode)) nextState.put(hashCode, neighbor);
                    else {
                        if(curState.get(hashCode).getActive() == 1) localActiveCount++;
                    }
                }
                if(cube.getActive() == 1 && localActiveCount != 2 && localActiveCount != 3) {
                    cube.setActive(0);
                }
                else if(cube.getActive() == 0 && localActiveCount == 3) {
                    cube.setActive(1);
                }
                nextState.put(cube.hashCode(), cube);
            }
            cycle++;
        }
        return getActiveCount(nextState.values());
    }

    private static int getActiveCount(Collection<Cube> cubeSet) {
        int count = 0;
        for(Cube cube:cubeSet) {
            if(cube.getActive() == 1) count++;
        }
        return count;
    }

    private static int getHashCode(int x, int y, int z) {
        return (String.valueOf(x) + String.valueOf(y) + String.valueOf(z)).hashCode();
    }
}
