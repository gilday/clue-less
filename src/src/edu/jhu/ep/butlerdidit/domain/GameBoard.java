package edu.jhu.ep.butlerdidit.domain;

import org.apache.http.impl.io.ContentLengthInputStream;

import java.util.*;

public class GameBoard {

    private List<Player> players;

    private Dictionary<String, GameBoardSpace> spaces;
    public Dictionary<String, GameBoardSpace> getSpaces() {
        return spaces;
    }

    public GameBoard(List<Player> players) {
        this.players = players;

        createSpaces();
    }

    public List<GameBoardSpace> getPossibleMoves(Player player) {
        GameBoardSpace currentSpace = spaces.get(player.getLocation());
        List<GameBoardSpace> possibleMoves = new Vector<GameBoardSpace>();

        for(GameBoardSpace space : currentSpace.getNavigationTargets()) {
            if(space instanceof HallwaySpace) {
                boolean isOccupied = false;
                for(Player otherPlayer : players) {
                    if(otherPlayer.getLocation().equals(space.getSpaceId())) {
                        isOccupied = true;
                        break;
                    }
                }
                if(!isOccupied) possibleMoves.add(space);
            } else {
                possibleMoves.add(space);
            }
        }
        return possibleMoves;
    }

    public boolean isPlayerAbleToMoveToSpace(Player player, GameBoardSpace space) {
        List<GameBoardSpace> possibleMoves = getPossibleMoves(player);

        for(GameBoardSpace target : possibleMoves) {
            if(target.getSpaceId() == space.getSpaceId()) return true;
        }
        return false;
    }

    private void createSpaces() {
        spaces = new Hashtable<String, GameBoardSpace>();

        Room study = new Room(Room.STUDY);
        Room hall = new Room(Room.HALL);
        HallwaySpace hallway = new HallwaySpace(study, hall);
        addSpaceToBoard(study);
        addSpaceToBoard(hall);
        addSpaceToBoard(hallway);

        Room lounge = new Room(Room.LOUNGE);
        hallway = new HallwaySpace(hall, lounge);
        addSpaceToBoard(lounge);
        addSpaceToBoard(hallway);

        Room diningRoom = new Room(Room.DININGROOM);
        hallway = new HallwaySpace(lounge, diningRoom);
        addSpaceToBoard(diningRoom);
        addSpaceToBoard(hallway);

        Room kitchen = new Room(Room.KITCHEN);
        hallway = new HallwaySpace(diningRoom, kitchen);
        addSpaceToBoard(kitchen);
        addSpaceToBoard(hallway);

        Room ballroom = new Room(Room.BALLROOM);
        hallway = new HallwaySpace(kitchen, ballroom);
        addSpaceToBoard(ballroom);
        addSpaceToBoard(hallway);

        Room conservatory = new Room(Room.CONSERVATORY);
        hallway = new HallwaySpace(ballroom, conservatory);
        addSpaceToBoard(conservatory);
        addSpaceToBoard(hallway);

        Room library = new Room(Room.LIBRARY);
        hallway = new HallwaySpace(conservatory, library);
        addSpaceToBoard(library);
        addSpaceToBoard(hallway);
        hallway = new HallwaySpace(library, study);
        addSpaceToBoard(hallway);

        Room billiardRoom = new Room(Room.BILLIARDROOM);
        addSpaceToBoard(billiardRoom);
        addSpaceToBoard(new HallwaySpace(billiardRoom, library));
        addSpaceToBoard(new HallwaySpace(billiardRoom, hall));
        addSpaceToBoard(new HallwaySpace(billiardRoom, diningRoom));
        addSpaceToBoard(new HallwaySpace(billiardRoom, ballroom));

        // Connect secret passages
        conservatory.connectSpace(lounge);
        study.connectSpace(kitchen);

        // Create start spaces
        GameBoardSpace msScarlet = new GameBoardSpace(ClueCharacter.MsScarletID);
        msScarlet.connectSpace(spaces.get("Hall-Lounge"));
        addSpaceToBoard(msScarlet);

        GameBoardSpace colMustard = new GameBoardSpace(ClueCharacter.ColMustardID);
        colMustard.connectSpace(spaces.get("Lounge-Dining Room"));
        addSpaceToBoard(colMustard);

        GameBoardSpace mrsWhite = new GameBoardSpace(ClueCharacter.MrsWhiteID);
        mrsWhite.connectSpace(spaces.get("Ball Room-Kitchen"));
        addSpaceToBoard(mrsWhite);

        GameBoardSpace mrGreen = new GameBoardSpace(ClueCharacter.MrGreenID);
        mrGreen.connectSpace(spaces.get("Conservatory-Ball Room"));
        addSpaceToBoard(mrGreen);

        GameBoardSpace mrsPeacock = new GameBoardSpace(ClueCharacter.MrsPeacockID);
        mrsPeacock.connectSpace(spaces.get("Conservatory-Library"));
        addSpaceToBoard(mrsPeacock);

        GameBoardSpace profPlum = new GameBoardSpace(ClueCharacter.ProfPlumID);
        profPlum.connectSpace(spaces.get("Library-Study"));
        addSpaceToBoard(profPlum);
    }

    private void addSpaceToBoard(GameBoardSpace space) {
        spaces.put(space.getSpaceId(), space);
    }
}
