package com.soft.level;

public class Level {

    public static final int [] LEVEL_ONE = {
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,4,0,0,0,0,0,3,0,0,
            0,0,0,0,1,0,0,0,0,0,
            0,0,0,0,0,0,1,0,0,0,
            0,1,0,0,0,0,0,0,0,0,
            0,0,0,0,0,1,1,1,1,1,
            0,0,0,0,0,1,1,1,1,1,
            1,1,1,1,0,1,1,1,1,1,
            1,1,1,1,0,1,1,1,1,1
    };
    public static final int [] LEVEL_TWO = {
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,4,0,0,0,0,3,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,1,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,1,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,1,1,1,1,
            1,1,1,1,0,0,0,0,0,0
    };
    public static final int [] LEVEL_THREE = {
            9,7,9,9,3,9,9,9,6,6,6,6,9,9,9,9,9,9,6,9,9,9,9,9,5,
            9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,
            7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,
            6,0,0,0,0,0,0,0,6,9,6,0,0,0,0,0,0,0,0,0,0,0,0,0,9,
            9,0,0,6,9,9,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,7,
            9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,
            4,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,9,
            9,0,0,0,0,0,0,0,9,0,0,0,0,0,0,9,9,9,0,0,0,10,10,10,9,
            9,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,6,7,8,
            9,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,
            4,9,9,9,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,9,
            8,0,0,0,0,0,9,6,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,
            9,0,0,0,0,0,0,0,0,0,0,3,0,9,0,0,9,9,0,0,0,0,0,0,9,
            9,10,10,10,9,0,0,0,0,0,0,9,2,9,0,0,0,0,0,0,0,10,10,10,9,
            3,6,7,9,8,11,11,11,11,11,11,7,7,6,11,11,11,11,11,11,11,9,9,9,3

    };
    public static final int [] LEVEL_FOUR = {};
    public static final int [] LEVEL_FIVE = {};
    public static final int [] LEVEL_SIX = {};


    public int[] getLevelData(int levelNumber) {
        return switch (levelNumber) {
            case 2 -> LEVEL_TWO;
            case 3 -> LEVEL_THREE;
            case 4 -> LEVEL_FOUR;
            case 5 -> LEVEL_FIVE;
            case 6 -> LEVEL_SIX;
            default -> LEVEL_ONE;
        };
    }

}
