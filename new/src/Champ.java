
import java.util.*;

public class Champ {
	//Attributs par d�fauts
	private final static int NBMINES=3;
	private final static int DIMEASY=5;
	private final static int DIMMEDIUM=10;
	private final static int DIMHARD=20;
	

	
	private final String NomParDefaut="GAME";
	 private String name;
	private static boolean[][] tabMines;//Tableau repr�sentatif du champs		
	Random alea=new Random();
	private  int dimX;
	private int dimY;
	private  int nbmines;
	
					
		
	public   void InitialisationChamp(int dimensionX,int dimensionY, int nbrmines)
	{
		dimX=dimensionX;
		dimY=dimensionY;
		nbmines=nbrmines;
		tabMines=new boolean [dimX][dimY];
		placeMines(nbmines);		
	}
	
	
	
	public Champ()//constructeur par d�faut
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
		if (lev.getLevel()==lvl.EASY)
			InitialisationChamp(10,10,5);
		else if(lev.getLevel()==lvl.MEDIUM)
			InitialisationChamp(20,20,6);	
		else if(lev.getLevel()==lvl.HARD)
			InitialisationChamp(40,40,26);
		
		
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
	
	public  void placeMines(int nbrmines)//Va placer al�atoirement les mines sur le champs
	{
		for(int i=0;i<nbrmines;i++)
		{
			int x=alea.nextInt(dimX);
			int y=alea.nextInt(dimY);
			tabMines[x][y]=true;//true si la case contient une mines
			
		}
	}
	
	public void affText()//Cette m�thode va nous afficher notre champs de mines
	{
		System.out.println("Notre champs de mines est le suivant:");
		
		for(int j=0;j<dimY;j++)
		{
			for(int i=0;i<dimX;i++) {
				if(tabMines[i][j]==true)//Si pr�sence d'une mine
					System.out.print("x  ");
				else//si pas de pr�sence de mines 
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
	 
	//Destructeur
	protected void finalize() throws Throwable
	{
		super.finalize();
	}
	
	
	

}