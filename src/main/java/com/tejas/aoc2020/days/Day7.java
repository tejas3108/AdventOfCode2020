package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.*;

public class Day7 {
    private static class BagContent {
        private String bagColor;
        private int bagCount;

        public BagContent(String bagColor, int bagCount) {
            this.bagColor = bagColor;
            this.bagCount = bagCount;
        }

        public BagContent(String rule) {
            this.bagCount = Integer.parseInt(rule.split(" ")[0]);
            this.bagColor = rule.split(" ")[1] + " " + rule.split(" ")[2];
        }

        public String getBagColor() {
            return bagColor;
        }

        public void setBagColor(String bagColor) {
            this.bagColor = bagColor;
        }

        public int getBagCount() {
            return bagCount;
        }

        public void setBagCount(int bagCount) {
            this.bagCount = bagCount;
        }

        @Override
        public int hashCode() {
            return Objects.hash(bagColor, bagCount);
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    static Map<String, List<BagContent>> comprisedOfMap = new HashMap<>();
    static Map<String, List<String>> containedInMap = new HashMap<>();
    static Map<String, Integer> bagCount = new HashMap<>();
    static final String INITIAL_COLOR = "shiny gold";

    public static int part1(String fileName) {
        Set<String> validOutermostBags = new HashSet<>();
        parseFile(fileName);
        Queue<String> queue = new LinkedList<>();
        for(String bag:containedInMap.get(INITIAL_COLOR)) {
            queue.add(bag);
        }
        while(!queue.isEmpty()) {
            String curBag = queue.poll();
            if(!validOutermostBags.contains(curBag)) {
                validOutermostBags.add(curBag);
                if(containedInMap.containsKey(curBag)) {
                    for(String bag:containedInMap.get(curBag)) {
                        queue.add(bag);
                    }
                }
            }
        }
        return validOutermostBags.size();
    }

    public static int part2(String fileName) {
        Set<String> visitedBags = new HashSet<>();
        parseFile(fileName);
        Queue<BagContent> queue = new LinkedList<>();
        return totalBagsNeeded(INITIAL_COLOR);
    }

    private static int totalBagsNeeded(String bagColor) {
        if(comprisedOfMap.get(bagColor).size() == 0) return 0;
        if(bagCount.containsKey(bagColor)) return bagCount.get(bagColor);
        int bagsNeeded = 0;
        for(BagContent bag:comprisedOfMap.get(bagColor)) {
            bagsNeeded = bagsNeeded + bag.getBagCount() + (bag.getBagCount() * totalBagsNeeded(bag.getBagColor()));
        }
        bagCount.put(bagColor, bagsNeeded);
        return bagsNeeded;
    }

    private static void parseFile(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);
        String parentBag;
        List<BagContent> childBags;
        for(String rule:input) {
            childBags = new ArrayList<>();
            String[] ruleParts = rule.split(" ");
            parentBag = ruleParts[0] + " " + ruleParts[1];
            //vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            //faded blue bags contain no other bags.
            String[] childBagRule = rule.split("contain ");
            if(!childBagRule[1].startsWith("no other bags")) {
                for(String childBag:childBagRule[1].split(",")) {
                    BagContent bag = new BagContent(childBag.trim());
                    childBags.add(bag);
                    if(!containedInMap.containsKey(bag.getBagColor())) {
                        containedInMap.put(bag.getBagColor(), new ArrayList<>());
                    }
                    containedInMap.get(bag.getBagColor()).add(parentBag);
                }
            }
            comprisedOfMap.put(parentBag, childBags);
        }
    }
}
