package com.soft.level;

public class Level {

    public static final int [] LEVEL_ONE = {
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,4,0,0,0,0,0,3,0,0,
            0,0,0,0,1,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            1,1,1,1,0,1,1,1,1,1,
            1,1,1,1,0,1,1,1,1,1,
            1,1,1,1,0,1,1,1,1,1,
            1,1,1,1,0,1,1,1,1,1
    };
    public static final int [] LEVEL_TWO = {};
    public static final int [] LEVEL_THREE = {};
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
