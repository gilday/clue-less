package edu.jhu.ep.butlerdidit.domain;

public class HallwaySpace extends GameBoardSpace {
	
	public static String hallwayIdFromRooms(Room room1, Room room2) {
		return String.format("%s-%s", room1.getSpaceId(), room2.getSpaceId());
	}

    public HallwaySpace(Room room, Room connectsTo) {
        super(HallwaySpace.hallwayIdFromRooms(room, connectsTo));

        connectSpace(room);
        connectSpace(connectsTo);
    }
}
