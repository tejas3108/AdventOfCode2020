package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {
    private static final char[] dirs = new char[] {'N','E','S','W'};
    static int curDir = 1;
    static int horizontalOffset = 0;
    static int verticalOffset = 0;

    static int waypointHorizontalDir = 1; //East
    static int waypointVerticalDir = 0; //North
    static int waypointHorizontalDistance = 10;
    static int waypointVerticalDistance = 1;

    public static int part1(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);

        for(String instruction:input) {
            char action = instruction.charAt(0);
            int distance = Integer.parseInt(instruction.substring(1));
            if(action == 'N' || action == 'S') {
                moveHorizontal(action, distance);
            }
            else if(action == 'E' || action == 'W') {
                moveVertical(action, distance);
            }
            else if(action == 'L') {
                curDir = rotateLeft(curDir, distance);
            }
            else if(action == 'R') {
                curDir = rotateRight(curDir, distance);
            }
            else moveForward(distance);
        }

        return Math.abs(horizontalOffset) + Math.abs(verticalOffset);
    }

    public static int part2(String fileName) {
        horizontalOffset = 0;
        verticalOffset = 0;
        String[] input = FileUtil.readFileContent(fileName);

        for(String instruction:input) {
            char action = instruction.charAt(0);
            int distance = Integer.parseInt(instruction.substring(1));
            if(action == 'N' || action == 'S') {
                moveWaypointVertical(action, distance);
            }
            else if(action == 'E' || action == 'W') {
                moveWaypointHorizontal(action, distance);
            }
            else if(action == 'L') {
                rotateWaypointLeft(waypointHorizontalDir, waypointVerticalDir, distance);
            }
            else if(action == 'R') {
                rotateWaypointRight(waypointHorizontalDir, waypointVerticalDir, distance);
            }
            else moveShipTowardsWaypoint(distance);
        }

        return Math.abs(horizontalOffset) + Math.abs(verticalOffset);
    }

    private static void moveHorizontal(char moveDir, int moveDistance) {
        if(moveDir == 'N') horizontalOffset += moveDistance;
        else horizontalOffset -= moveDistance;
    }

    private static void moveVertical(char moveDir, int moveDistance) {
        if(moveDir == 'E') verticalOffset += moveDistance;
        else verticalOffset -= moveDistance;
    }

    private static void moveForward(int moveDistance){
        if(dirs[curDir] == 'N' || dirs[curDir] == 'S') {
            moveHorizontal(dirs[curDir], moveDistance);
        }
        else if(dirs[curDir] == 'E' || dirs[curDir] == 'W') {
            moveVertical(dirs[curDir], moveDistance);
        }
    }

    private static int rotateRight(int curDirection, int angle) {
        angle %= 360;
        int moveIndex = angle / 90;
        return (curDirection + moveIndex) % dirs.length;
    }

    private static int rotateLeft(int curDirection, int angle) {
        angle %= 360;
        int moveIndex = angle / 90;
        while(moveIndex > 0) {
            curDirection--;
            if(curDirection < 0) curDirection = dirs.length-1;
            moveIndex--;
        }
        return curDirection;
    }

    private static void rotateWaypointLeft(int waypointHorizontalDirection, int waypointVerticalDirection, int angle) {
        waypointHorizontalDir = rotateLeft(waypointHorizontalDirection, angle);
        waypointVerticalDir = rotateLeft(waypointVerticalDirection, angle);
        angle %= 360;
        int moveIndex = angle / 90;
        if(moveIndex %2 != 0) {
            int tmp = waypointHorizontalDir;
            waypointHorizontalDir = waypointVerticalDir;
            waypointVerticalDir = tmp;

            tmp = waypointHorizontalDistance;
            waypointHorizontalDistance = waypointVerticalDistance;
            waypointVerticalDistance = tmp;
        }
    }

    private static void rotateWaypointRight(int waypointHorizontalDirection, int waypointVerticalDirection, int angle) {
        waypointHorizontalDir = rotateRight(waypointHorizontalDirection, angle);
        waypointVerticalDir = rotateRight(waypointVerticalDirection, angle);
        angle %= 360;
        int moveIndex = angle / 90;
        if(moveIndex %2 != 0) {
            int tmp = waypointHorizontalDir;
            waypointHorizontalDir = waypointVerticalDir;
            waypointVerticalDir = tmp;

            tmp = waypointHorizontalDistance;
            waypointHorizontalDistance = waypointVerticalDistance;
            waypointVerticalDistance = tmp;
        }
    }
    private static void moveWaypointHorizontal(char moveDir, int moveDistance) {
        if(moveDir == 'E') waypointHorizontalDistance += moveDistance;
        else waypointHorizontalDistance -= moveDistance;
        if(waypointHorizontalDistance < 0) {
            waypointHorizontalDir = 3;
            waypointHorizontalDistance = -waypointHorizontalDistance;
        }
        else {
            waypointHorizontalDir = 1;
        }
    }

    private static void moveWaypointVertical(char moveDir, int moveDistance) {
        if(moveDir == 'N') waypointVerticalDistance += moveDistance;
        else waypointVerticalDistance -= moveDistance;
        if(waypointVerticalDistance < 0) {
            waypointVerticalDir = 2;
            waypointVerticalDistance = -waypointVerticalDistance;
        }
        else {
            waypointVerticalDir = 0;
        }
    }

    private static void moveShipTowardsWaypoint(int timesToMove){
        if(dirs[waypointHorizontalDir] == 'E') {
            horizontalOffset += (timesToMove * waypointHorizontalDistance);
        }
        else if(dirs[waypointHorizontalDir] == 'W') {
            horizontalOffset -= (timesToMove * waypointHorizontalDistance);
        }
        if(dirs[waypointVerticalDir] == 'N') {
            verticalOffset += (timesToMove * waypointVerticalDistance);
        }
        else if(dirs[waypointVerticalDir] == 'S') {
            verticalOffset -= (timesToMove * waypointVerticalDistance);
        }
    }
}
