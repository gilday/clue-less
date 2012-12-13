package edu.jhu.ep.butlerdidit.domain;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

public class Deck 
{
    
    public static final Deck RopeCard = new Deck("rope_card");
    public static final Deck RevolverCard = new Deck("revolver_card");
    public static final Deck WrenchCard = new Deck("wrench_card");
    public static final Deck CandlestickCard = new Deck("candlestick_card");
    public static final Deck KnifeCard = new Deck("knife_card");
    public static final Deck LeadpipeCard = new Deck("leadpipe_card");
    
    public static final Deck ScarlettCard = new Deck("scarlett_card");
    public static final Deck PlumCard = new Deck("plum_card");
    public static final Deck PeacockCard = new Deck("peacock_card");
    public static final Deck GreenCard = new Deck("green_card");
    public static final Deck MustardCard = new Deck("mustard_card");
    public static final Deck WhiteCard = new Deck("white_card");
    
    public static final Deck BallCard = new Deck("ball_card");
    public static final Deck HallCard = new Deck("hall_card");
    public static final Deck StudyCard = new Deck("study_card");
    public static final Deck LoungeCard = new Deck("lounge_card");
    public static final Deck KitchenCard = new Deck("kitchen_card");
    public static final Deck ConservatoryCard = new Deck("conservatory_card"); 
    public static final Deck BilliardCard = new Deck("billiard_card");
    public static final Deck LibraryCard = new Deck("library_card");
    public static final Deck DiningCard = new Deck("dining_card"); 
	
	private String Card;
	
	public static final List<Deck> WeaponsDeck = new ArrayList<Deck>(6);
	public static final List<Deck> CharacterDeck = new ArrayList<Deck>(6);
	public static final List<Deck> RoomDeck = new ArrayList<Deck>(9);
    
	public static final List<Deck> AllDeck = new ArrayList<Deck>(21);

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
	private Deck(String Card)
	{
		this.Card = Card;
	}
    public String getCard() 
    {
        return Card;
    } 
}
