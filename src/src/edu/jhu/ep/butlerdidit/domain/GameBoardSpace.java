package edu.jhu.ep.butlerdidit.domain;

import java.util.List;
import java.util.Vector;

public class GameBoardSpace {

    protected String spaceId;
    public String getSpaceId() {
        return spaceId;
    }

    private List<GameBoardSpace> navigationTargets;
    public List<GameBoardSpace> getNavigationTargets() {
        return navigationTargets;
    }

    public GameBoardSpace(String spaceId) {
        navigationTargets = new Vector<GameBoardSpace>();
        this.spaceId = spaceId;
    }

    public void connectSpace(GameBoardSpace otherSpace) {
        otherSpace.navigationTargets.add(this);
        this.navigationTargets.add(otherSpace);
    }

}
