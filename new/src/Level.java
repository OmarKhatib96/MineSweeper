
/*
 * Enumération des différents niveaux disponibles
 */
 enum lvl {
	    EASY,
	    MEDIUM,
	    HARD
	}
public class Level {

	
	
	private lvl level;
	
	public Level()//Constructeur par défaut
	{
		
		
	}
	
	public Level(lvl l)//Constructeur surchargé
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
