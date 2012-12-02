package edu.jhu.ep.butlerdidit.domain;

public class HallwaySpace extends GameBoardSpace {

    public HallwaySpace(Room room, Room connectsTo) {
        super(String.format("%s-%s", room.getSpaceId(), connectsTo.getSpaceId()));

        connectSpace(room);
        connectSpace(connectsTo);
    }
}
