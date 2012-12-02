package edu.jhu.ep.butlerdidit.domain;

import android.graphics.Color;

public class ClueCharacter {

    public static final String MsScarletID = "Ms. Scarlet";
    public static final String ColMustardID = "Col. Mustard";
    public static final String MrsWhiteID = "Mrs. White";
    public static final String MrGreenID = "Mr. Green";
    public static final String MrsPeacockID = "Mrs. Peacock";
    public static final String ProfPlumID = "Prof. Plum";

    public static final ClueCharacter MsScarlet = new ClueCharacter(MsScarletID, Color.RED);
    public static final ClueCharacter ColMustard = new ClueCharacter(ColMustardID, Color.YELLOW);
    public static final ClueCharacter MrsWhite = new ClueCharacter(MrsWhiteID, Color.WHITE);
    public static final ClueCharacter MrGreen = new ClueCharacter(MrGreenID, Color.GREEN);
    public static final ClueCharacter MrsPeacock = new ClueCharacter(MrsPeacockID, Color.BLUE);
    public static final ClueCharacter ProfPlum = new ClueCharacter(ProfPlumID, Color.MAGENTA);

    private String name;
    public String getName() {
        return name;
    }

    private int color;
    public int getColor() {
        return color;
    }

    private ClueCharacter(String name, int color) {
        this.name = name;
        this.color = color;
    }

}
