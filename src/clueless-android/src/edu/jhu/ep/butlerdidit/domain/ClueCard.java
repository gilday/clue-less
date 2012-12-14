package edu.jhu.ep.butlerdidit.domain;

public class ClueCard 
{
	private String name;

	ClueCard(String card)
	{
		this.name = card;
	}
	
    public String getCard() 
    {
        return name;
    } 
}
