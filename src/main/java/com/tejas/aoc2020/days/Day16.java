package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.*;

public class Day16 {
    private static class Range {
        private int lowerBound1;
        private int upperBound1;
        private int lowerBound2;
        private int upperBound2;

        public Range(int lowerBound1, int upperBound1, int lowerBound2, int upperBound2) {
            this.lowerBound1 = lowerBound1;
            this.upperBound1 = upperBound1;
            this.lowerBound2 = lowerBound2;
            this.upperBound2 = upperBound2;
        }

        public boolean isInRange(int number) {
            return (number >= lowerBound1 && number <= upperBound1) || (number >= lowerBound2 && number <= upperBound2);
        }
    }

    private static class TicketRule {
        private String ruleName;
        private Range range;
        private int position;

        public TicketRule(String line) {
            this.position = -1;
            this.ruleName = line.split(":")[0];
            String ranges = line.split(":")[1].trim();
            String range1 = ranges.split(" ")[0];
            String range2 = ranges.split(" ")[2];
            range = new Range(Integer.parseInt(range1.split("-")[0]), Integer.parseInt(range1.split("-")[1]), Integer.parseInt(range2.split("-")[0]), Integer.parseInt(range2.split("-")[1]));
        }

        public Range getRange() {
            return range;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public String getRuleName() {
            return ruleName;
        }
    }

    private static class Ticket {
        private List<Integer> numbers;

        public Ticket(String line) {
            numbers = new ArrayList<>();
            for(String number:line.split(",")) {
                numbers.add(Integer.parseInt(number));
            }
        }

        public List<Integer> getNumbers() {
            return numbers;
        }
    }

    private static class Column {
        private List<Integer> numbers;
        private int colNumber;
        private int rulesMatch;

        public Column(List<Integer> numbers, int colNumber) {
            this.numbers = numbers;
            this.colNumber = colNumber;
            this.rulesMatch = 0;
        }

        public List<Integer> getNumbers() {
            return numbers;
        }

        public void setNumbers(List<Integer> numbers) {
            this.numbers = numbers;
        }

        public int getColNumber() {
            return colNumber;
        }

        public void setColNumber(int colNumber) {
            this.colNumber = colNumber;
        }

        public int getRulesMatch() {
            return rulesMatch;
        }

        public void setRulesMatch(int rulesMatch) {
            this.rulesMatch = rulesMatch;
        }
    }

    private static List<TicketRule> ticketRules;
    private static Ticket myTicket;
    private static List<Ticket> nearbyTickets;

    private static void parseFile(String fileName) {
        ticketRules = new ArrayList<>();
        nearbyTickets = new ArrayList<>();
        List<String> lines = FileUtil.readAllLines(fileName);
        int blankLineCount = 0;
        for(String line:lines) {
            if(line.trim().isEmpty()) {
                blankLineCount++;
            }
            else {
                switch (blankLineCount) {
                    case 0: {
                        ticketRules.add(new TicketRule(line));
                        break;
                    }
                    case 1: {
                        if(!line.startsWith("your ticket")) {
                            myTicket = new Ticket(line);
                        }
                        break;
                    }
                    case 2: {
                        if(!line.startsWith("nearby tickets")) {
                            nearbyTickets.add(new Ticket(line));
                        }
                        break;
                    }
                }
            }
        }
    }

    public static int part1(String fileName, boolean removeTicket) {
        parseFile(fileName);
        int sum = 0;
        for(int i=0;i<nearbyTickets.size();i++) {
            Ticket nearbyTicket = nearbyTickets.get(i);
            for(int number:nearbyTicket.getNumbers()) {
                int invalidCount = 0;
                for(TicketRule rule:ticketRules) {
                    if(!rule.getRange().isInRange(number)) {
                        invalidCount++;
                    }
                }
                if(invalidCount == ticketRules.size()) {
                    if(removeTicket) {
                        nearbyTickets.remove(i);
                        i--;
                        break;
                    }
                    else sum += number;
                }
            }
        }
        return sum;
    }

    public static double part2(String fileName) {
        part1(fileName, true);
        Double multiply = 1D;
        Queue<Column> queue = new PriorityQueue<>((a, b) -> a.getRulesMatch() - b.getRulesMatch());

        for(int col = 0;col<myTicket.getNumbers().size();col++) {
            List<Integer> columnNumbers = new ArrayList<>();
            for(Ticket nearbyTicket:nearbyTickets) {
                columnNumbers.add(nearbyTicket.getNumbers().get(col));
            }
            Column column = new Column(columnNumbers, col);
            int rulesMatch = 0;
            //go over all rules to see which ones match
            for(int i=0;i<ticketRules.size();i++) {
                int validCount = 0;
                for(int colNumber:columnNumbers) {
                    if(ticketRules.get(i).getRange().isInRange(colNumber)) validCount++;
                }
                if(validCount == columnNumbers.size()) {
                    rulesMatch++;
                }
            }
            column.setRulesMatch(rulesMatch);
            queue.add(column);
        }

        while(!queue.isEmpty()) {
            Column column = queue.poll();
            List<Integer> columnNumbers = column.getNumbers();
            for(int i=0;i<ticketRules.size();i++) {
                int validCount = 0;
                for(int colNumber:columnNumbers) {
                    if(ticketRules.get(i).getRange().isInRange(colNumber)) validCount++;
                }
                if(validCount == columnNumbers.size() && ticketRules.get(i).getPosition() == -1) {
                    ticketRules.get(i).setPosition(column.getColNumber());
                }
            }
        }
        for(TicketRule rule:ticketRules) {
            if(rule.getRuleName().startsWith("departure")) multiply *= myTicket.getNumbers().get(rule.getPosition());
        }
        return multiply;
    }
}
