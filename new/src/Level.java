
/*
 * Enum�ration des diff�rents niveaux disponibles
 */
 enum lvl {
	    EASY,
	    MEDIUM,
	    HARD
	}
public class Level {

	
	
	private lvl level;
	
	public Level()//Constructeur par d�faut
	{
		
		
	}
	
	public Level(lvl l)//Constructeur surcharg�
	{
		
	level=l;	
	}
	
	public void setLevel(lvl l)//level setter
	{
		level=l;
	}
	public lvl getLevel()//level getter
	{
		
		return level;
		
	}
	//Destructeur
	protected void finalize() throws Throwable
	{
		super.finalize();
	}
			
	
	
}
