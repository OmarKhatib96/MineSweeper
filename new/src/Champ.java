
import java.util.*;

public class Champ {
	//Attributs par défauts
	private final static int NBMINES=0;
	private final static int DIMEASY=2;
	private final static int DIMMEDIUM=2;
	private final static int DIMHARD=2;
	private final static int NBMINESEASY=3;
	private final static int NBMINESMEDIUM=5;
	private final static int NBMINESHARD=10;


				
	
	
	
	private final String NomParDefaut="GAME";
	 private String name;
	private static boolean[][] tabMines;//Tableau représentatif du champs		
	Random alea=new Random();
	private  int dimX;
	private int dimY;
	private  int nbmines=NBMINES;
	
					
		
	public   void InitialisationChamp(int dimensionX,int dimensionY, int nbrmines)
	{
		dimX=dimensionX;
		dimY=dimensionY;
		nbmines=nbrmines;
		tabMines=new boolean [dimX][dimY];
		placeMines();		
	}
	
	
	
	public Champ()//constructeur par défaut
	{
		
		this.dimX=DIMEASY;
		this.dimY=DIMEASY;
		this.nbmines=NBMINES;
		this.name=NomParDefaut;
		InitialisationChamp(dimX,dimY,nbmines);
	}

	
	public Champ(String name,Level lev)
	{
		this.name=name;
		newPartie(lev);
		/*this.name=name;
		if (lev.getLevel()==lvl.EASY)
			InitialisationChamp(10,10,5);
		else if(lev.getLevel()==lvl.MEDIUM)
			InitialisationChamp(20,20,6);	
		else if(lev.getLevel()==lvl.HARD)
			InitialisationChamp(40,40,26);
		*/
		
	}
	public boolean [][]getTab() {
		
		return tabMines;
	}

	public int getDimensionX() {
		
		return dimX;
		
	}
	
	public int getDimensionY()
	{
		
		return dimY;
	}
	
	public int getNbMines()
	{
		
		return nbmines;
	}
	
	public  void placeMines()//Va placer aléatoirement les mines sur le champs
	{
		
		for(int i=0;i<tabMines.length;i++)
			for(int j=0;j<tabMines[0].length;j++)
				tabMines[i][j]=false;

			
		for(int i=nbmines;i>0;)
		{
			int x=alea.nextInt(tabMines.length);
			int y=alea.nextInt(tabMines[0].length);
			
				if(!tabMines[x][y]) {
					tabMines[x][y]=true;
					i--;
				}
			
			
		}
	}
	
	public void affText()//Cette méthode va nous afficher notre champs de mines
	{
		System.out.println("Notre champs de mines est le suivant:");
		
		for(int j=0;j<dimY;j++)
		{
			for(int i=0;i<dimX;i++) {
				if(tabMines[i][j]==true)//Si présence d'une mine
					System.out.print("x  ");
				else//si pas de présence de mines 
					System.out.print("0  ");
								
			}
        System.out.println();
		}
		
		
	}
	
	
	public   String toString() {//Surcharge de toString 
		StringBuilder str = new StringBuilder();
        str.append("Le nom de notre mine est : ").append(this.name).append("\n");
        str.append("La taille de notre mines est: ").append(this.dimX).append("x").append(this.dimY).append("\n");
        str.append("Il y a  : ").append(this.nbmines).append(" mines dans notre champ de mines\n ");
        //Mettre le niveau aussi
        return str.toString();
	} 	
	
	public int  nbr_mines(int x, int y)
	{
		int nbMines=0;
		int borneMinI,borneMinJ,borneMaxI,borneMaxJ;
		borneMinI=x==0 ?0:x-1;
		borneMinJ=y==0 ?0:y-1;
		borneMaxI=x==tabMines.length-1? tabMines.length: x+2;
		borneMaxJ=y==tabMines[0].length-1?tabMines[0].length:y+2;
		for(int i=borneMinI;i<borneMaxI;i++)
		
				for(int j=borneMinJ;j<borneMaxJ;j++) 
				
					if(!(i==x && j==y) &&tabMines[i][j])
						nbMines++;
				
		
		
		return nbMines;
	}
	
	
	 public boolean Ismin(int x, int y)
	   {
		   return tabMines[x][y];
		   
		   
	   }
	 
	 public  void newPartie(Level lev) {
		 
		 	//this.name=name;
			if (lev.getLevel()==lvl.EASY) {
				nbmines=NBMINESEASY;
				InitialisationChamp(5,4,nbmines);
				
			}
			else if(lev.getLevel()==lvl.MEDIUM) {
				nbmines=NBMINESMEDIUM;	
				InitialisationChamp(10,10,nbmines);	
				
			}
			else if(lev.getLevel()==lvl.HARD) {
				nbmines=NBMINESHARD;

				InitialisationChamp(15,15,nbmines);

			}
		 
		 
	 }
	 
	 public void newPartie() {
		 
		 
		 
	 }
	 
	//Destructeur
	protected void finalize() throws Throwable
	{
		super.finalize();
	}
	
	
	

}
