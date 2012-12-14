package edu.jhu.ep.butlerdidit.domain;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    public static final ClueCard RopeCard = new ClueCard("rope_card");
    public static final ClueCard RevolverCard = new ClueCard("revolver_card");
    public static final ClueCard WrenchCard = new ClueCard("wrench_card");
    public static final ClueCard CandlestickCard = new ClueCard("candlestick_card");
    public static final ClueCard KnifeCard = new ClueCard("knife_card");
    public static final ClueCard LeadpipeCard = new ClueCard("leadpipe_card");
    
    public static final ClueCard ScarlettCard = new ClueCard("scarlett_card");
    public static final ClueCard PlumCard = new ClueCard("plum_card");
    public static final ClueCard PeacockCard = new ClueCard("peacock_card");
    public static final ClueCard GreenCard = new ClueCard("green_card");
    public static final ClueCard MustardCard = new ClueCard("mustard_card");
    public static final ClueCard WhiteCard = new ClueCard("white_card");
    
    public static final ClueCard BallCard = new ClueCard("ball_card");
    public static final ClueCard HallCard = new ClueCard("hall_card");
    public static final ClueCard StudyCard = new ClueCard("study_card");
    public static final ClueCard LoungeCard = new ClueCard("lounge_card");
    public static final ClueCard KitchenCard = new ClueCard("kitchen_card");
    public static final ClueCard ConservatoryCard = new ClueCard("conservatory_card"); 
    public static final ClueCard BilliardCard = new ClueCard("billiard_card");
    public static final ClueCard LibraryCard = new ClueCard("library_card");
    public static final ClueCard DiningCard = new ClueCard("dining_card"); 
	
	public static final List<ClueCard> WeaponsDeck = new ArrayList<ClueCard>(6);
	public static final List<ClueCard> CharacterDeck = new ArrayList<ClueCard>(6);
	public static final List<ClueCard> RoomDeck = new ArrayList<ClueCard>(9);
    
	public static final List<ClueCard> AllDeck = new ArrayList<ClueCard>(21);

    static {
    	WeaponsDeck.add(RopeCard);
    	WeaponsDeck.add(RevolverCard);
    	WeaponsDeck.add(WrenchCard);
    	WeaponsDeck.add(CandlestickCard);
    	WeaponsDeck.add(KnifeCard);
    	WeaponsDeck.add(LeadpipeCard);
    	
    	CharacterDeck.add(ScarlettCard);
    	CharacterDeck.add(PlumCard);
    	CharacterDeck.add(PeacockCard);
    	CharacterDeck.add(GreenCard);
    	CharacterDeck.add(MustardCard);
    	CharacterDeck.add(WhiteCard);
    	
    	RoomDeck.add(BallCard);
    	RoomDeck.add(HallCard);
    	RoomDeck.add(StudyCard);
    	RoomDeck.add(LoungeCard);
    	RoomDeck.add(KitchenCard);
    	RoomDeck.add(ConservatoryCard);
    	RoomDeck.add(BilliardCard);
    	RoomDeck.add(LibraryCard);
    	RoomDeck.add(DiningCard);
    	
    	AllDeck.addAll(WeaponsDeck);
    	AllDeck.addAll(CharacterDeck);
    	AllDeck.addAll(RoomDeck);
    }
}
