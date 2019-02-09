package game;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.base.Preconditions;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Horde
{
	private String name;
	private int cost;
	private Map<String, Integer> mapMonstersQuantity; //String:name of the monster, Integer:number of monsters
	
	
	public Horde() {
		//Empty constructor for jackson
	}
	
	
	
	public Horde(String name, int cost, Map<String, Integer> mapMonstersQuantity) {
		Preconditions.checkArgument(name.length() > 0, "name length was %s but expected strictly positive", name.length());
		Preconditions.checkArgument(cost > 0, "cost was %s but expected strictly positive", cost);
		for(Map.Entry<String, Integer> entry : mapMonstersQuantity.entrySet())
		{
			Preconditions.checkArgument(entry.getValue() > 0, "number of monsters was %s but expected strictly positive", entry.getValue());
		}
		this.name = name;
		this.cost = cost;
		this.mapMonstersQuantity = mapMonstersQuantity;
	}



	public String getName() {
		return name;
	}
	public int getCost() {
		return cost;
	}
	public Map<String, Integer> getMapMonstersQuantity() {
		return mapMonstersQuantity;
	}

}
