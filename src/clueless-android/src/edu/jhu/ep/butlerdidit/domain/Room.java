package edu.jhu.ep.butlerdidit.domain;

public class Room extends GameBoardSpace {

    public static final String STUDY = "Study";
    public static final String HALL = "Hall";
    public static final String LOUNGE = "Lounge";
    public static final String BILLIARDROOM = "Billiard Room";
    public static final String DININGROOM = "Dining Room";
    public static final String KITCHEN = "Kitchen";
    public static final String BALLROOM = "Ball Room";
    public static final String CONSERVATORY = "Conservatory";
    public static final String LIBRARY = "Library";

    public Room(String spaceId) {
        super(spaceId);
    }
}
