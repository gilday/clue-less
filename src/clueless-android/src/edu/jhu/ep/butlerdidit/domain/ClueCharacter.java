package edu.jhu.ep.butlerdidit.domain;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

public class ClueCharacter {

    public static final String MsScarletID = "Ms. Scarlett";
    public static final String ColMustardID = "Col. Mustard";
    public static final String MrsWhiteID = "Mrs. White";
    public static final String MrGreenID = "Mr. Green";
    public static final String MrsPeacockID = "Mrs. Peacock";
    public static final String ProfPlumID = "Prof. Plum";

    public static final ClueCharacter MsScarlett = new ClueCharacter(MsScarletID, Color.RED);
    public static final ClueCharacter ColMustard = new ClueCharacter(ColMustardID, Color.YELLOW);
    public static final ClueCharacter MrsWhite = new ClueCharacter(MrsWhiteID, Color.WHITE);
    public static final ClueCharacter MrGreen = new ClueCharacter(MrGreenID, Color.GREEN);
    public static final ClueCharacter MrsPeacock = new ClueCharacter(MrsPeacockID, Color.BLUE);
    public static final ClueCharacter ProfPlum = new ClueCharacter(ProfPlumID, Color.MAGENTA);
    
    public static final List<ClueCharacter> All = new ArrayList<ClueCharacter>(6);
    
    static {
    	All.add(MsScarlett);
    	All.add(ColMustard);
    	All.add(MrsWhite);
    	All.add(MrGreen);
    	All.add(MrsPeacock);
    	All.add(ProfPlum);
    }

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
