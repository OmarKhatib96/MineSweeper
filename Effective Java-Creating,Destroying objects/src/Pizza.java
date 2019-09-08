import java.util.*;

public abstract class Pizza {
public enum Topping {HAM,MUSHROOM,ONION,PEPPER,SAUSAGE}
final Set<Topping> toppings;

abstract static class Builder<T extends Builder<T>>
{
	EnumSet<Topping> toppings=EnumSet.noneOf(Topping.class);
	public T addTopping(Topping topping)
	{
		
		toppings.add(Objects.requireNonNull(topping));
		return self();
	}
	abstract Pizza build();
	protected abstract T self();
	//Subclasses must override this method to return "this" protected abstract T self();
}
Pizza(Builder<?> builder)
{
	toppings=builder.toppings.clone();
}

public static void main(String args [])
{
	NutritionFacts cocaCola=new NutritionFacts.Builder(240, 8).calories(100).sodium(35).carbohydrate(27).build();
	System.out.println(cocaCola);//Affichage de C(après surcharge)
	//Pizza creation
	NyPizza pizza=new NyPizza.Builder(NyPizza.Size.SMALL).addTopping(Topping.SAUSAGE).addTopping(Topping.ONION).build();
	
	//Ici pas possible de faire  NyPizza pizza=new NyPizza.Builder(SMALL).addTopping(SAUSAGE).addTopping(ONION).build();
	
	
}


	
}

