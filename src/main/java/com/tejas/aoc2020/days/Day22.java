package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.*;

public class Day22 {
    private static Queue<Integer> player1;
    private static Queue<Integer> player2;
   // private static Set<GameConfiguration> set = new HashSet<>();

    private static class GameConfiguration {
        private String configString;
        public GameConfiguration(Queue<Integer> p1, Queue<Integer> p2) {
            Queue<Integer> p1Clone = new LinkedList<>(p1);
            Queue<Integer> p2Clone = new LinkedList<>(p2);

            StringBuffer sb = new StringBuffer("");
            while(!p1Clone.isEmpty()) {
                sb.append(String.valueOf(p1Clone.poll())).append(",");
            }
            sb.append(":");
            while(!p2Clone.isEmpty()) {
                sb.append(String.valueOf(p2Clone.poll())).append(",");
            }

            configString = sb.toString();
        }

        @Override
        public int hashCode() {
            return Objects.hash(configString);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null) return false;
            if(!(obj instanceof GameConfiguration)) return false;
            return configString.equals(((GameConfiguration)obj).configString);
        }
    }

    public static double part1(String fileName) {
        init(fileName);
        double sum = 0D;

        Queue<Integer> winner = regularRound(player1, player2) ? player1 : player2;

        while(!winner.isEmpty()) {
            sum += (winner.peek() * winner.size());
            winner.poll();
        }

        return sum;
    }

    public static double part2(String fileName) {
        init(fileName);
        double sum = 0D;

        Queue<Integer> winner = recursionGameWinner(player1, player2, new HashSet<>()) ? player1 : player2;

        while(!winner.isEmpty()) {
            sum += (winner.peek() * winner.size());
            winner.poll();
        }

        return sum;
    }

    //returns true if player1 is the winner
    private static boolean recursionGameWinner(Queue<Integer> player1Q, Queue<Integer> player2Q, Set<GameConfiguration> set) {
        GameConfiguration curConfig = new GameConfiguration(player1Q, player2Q);
        if(set.contains(curConfig)) {
            return true;
        }
        set.add(curConfig);
        if(player1Q.isEmpty()) {
            return false;
        }
        if(player2Q.isEmpty()) {
            return true;
        }
        int number1 = player1Q.poll();
        int number2 = player2Q.poll();
        boolean isPlayer1Winner = false;
        if(player1Q.size() >= number1 && player2Q.size() >= number2) {
            //build clones and call recursively
            Queue<Integer> player1Dup = buildCloneForSize(player1Q, number1);
            Queue<Integer> player2Dup = buildCloneForSize(player2Q, number2);
            isPlayer1Winner = recursionGameWinner(player1Dup, player2Dup, new HashSet<>());
        }
        else {
            //regular round
            isPlayer1Winner = number1 > number2;
        }

        if(isPlayer1Winner) {
            player1Q.add(number1);
            player1Q.add(number2);
        }
        else {
            player2Q.add(number2);
            player2Q.add(number1);
        }

        return recursionGameWinner(player1Q, player2Q, set);
    }

    private static Queue<Integer> buildCloneForSize(Queue<Integer> input, int size) {
        Queue<Integer> tmp = new LinkedList<>(input);
        Queue<Integer> dup = new LinkedList<>();
        while(size > 0) {
            dup.add(tmp.poll());
            size--;
        }
        return dup;
    }

    private static boolean regularRound(Queue<Integer> player1Q, Queue<Integer> player2Q) {
        boolean isPlayer1Winner = false;
        int number1, number2;

        while(!player1Q.isEmpty() && !player2Q.isEmpty()) {
            number1 = player1Q.poll();
            number2 = player2Q.poll();

            isPlayer1Winner = number1 > number2;

            if(isPlayer1Winner) {
                player1Q.add(number1);
                player1Q.add(number2);
            }
            else {
                player2Q.add(number2);
                player2Q.add(number1);
            }
        }

        return isPlayer1Winner;
    }

    private static void init(String fileName) {
        List<String> lines = FileUtil.readAllLines(fileName);
        player1 = new LinkedList<>();
        player2 = new LinkedList<>();
        boolean isPlayer1 = true;
        for(String line:lines) {
            if(line.startsWith("Player")) continue;
            if(line.trim().isEmpty()) isPlayer1 = false;
            else {
                if(isPlayer1) player1.add(Integer.parseInt(line));
                else player2.add(Integer.parseInt(line));
            }
        }
    }
}
