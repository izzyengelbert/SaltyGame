import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Levels {	

	public static final int LEVEL_MENU = 0;
	public static final int LEVEL_1 = 1;
	public static final int LEVEL_2 = 2;
	public static final int LEVEL_3 = 3;
	public static final int LEVEL_4 = 4;
	public static final int LEVEL_5 = 5;
	private int currentLevel;
	//public static final int SALT_TARGET = 100;
	private ArrayList<Food> foods;
	private int numberOfSalt;
	private boolean levelComplete;
	private ArrayList<Block> blocks;
	private double kC;
	private double sC;
	private boolean[] completion = new boolean[6];
	private boolean gameOver;
	private int lastLevel;
	private BufferedImage salt;
	
	public Levels(int lvl){
		try {
			salt = ImageIO.read(getClass().getResource("/salt.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setCurrentLevel(lvl);
		setFoods(new ArrayList<Food>());
		setBlocks(new ArrayList<Block>());
	}

	public void initLevel(){
		foods.clear();
		blocks.clear();
		
		if (getCurrentLevel() == LEVEL_1)
		{
			setNumberOfSalt(1000);
			blocks.add(new Block(1,-460+700,586,10,kC,sC));
			foods.add(new Food(70,450,-310+700,100));
		}
		else if (getCurrentLevel() == LEVEL_2)
		{
			setNumberOfSalt(1000);
			blocks.add(new Block(1,-339+700,599,13,kC,sC));
			blocks.add(new Block(682,-417+700,514,16,kC,sC));	
			foods.add(new Food(70,450,-430+700,100));
			foods.add(new Food(70,698,-345+700,100));
		}
		else if (getCurrentLevel() == LEVEL_3)
		{
			setNumberOfSalt(1000);
			blocks.add(new Block(806,-600+700,100,100,kC,sC));
			blocks.add(new Block(300,-200+700,200,100,kC,sC));	
			foods.add(new Food(70,818,-168+700,100));
			foods.add(new Food(70,600,-100+700,100));
			foods.add(new Food(70,400,-100+700,100));
		}
		else if (getCurrentLevel() == LEVEL_4)
		{
			setNumberOfSalt(1000);
			blocks.add(new Block(1,-446+700,432,11,kC,sC));
			blocks.add(new Block(665,-463+700,534,11,kC,sC));
			foods.add(new Food(70,354,-321+700,100));
			foods.add(new Food(70,559,-100+700,100));
			foods.add(new Food(70,704,-300+700,100));
		}
		else if (getCurrentLevel() == LEVEL_5)
		{
			setNumberOfSalt(1);
			foods.add(new Food(70,447,-100+700,1));
			//blocks.add(new Block(500,-500+700,500,10,kC,sC));
		}
	}
	
	public void update(){
		if(checkFoodSatisfaction()){
			setLevelComplete(true);
			completion[getCurrentLevel()] = true;
			if(getCurrentLevel()+1<=5){
				setLastLevel(getCurrentLevel()+1);
				setCurrentLevel(getCurrentLevel()+1);
			}else{
				setLastLevel(getCurrentLevel());
				setGameOver(true);
			}
		}else{
			if(isGameOver()){
				completion[getCurrentLevel()] = true;
				setLastLevel(getCurrentLevel());
				setCurrentLevel(LEVEL_MENU);
			}
			setLevelComplete(false);
		}
		
	}
	
	public void checkSaltNumber(ArrayList<Salt> salts){
		if(salts.size()==0&&!checkFoodSatisfaction()){
			setGameOver(true);
		}
	}
	
	public boolean checkFoodSatisfaction(){
		int satisfied = 0;
		for(Food food : foods){
			food.update();
			if(food.isSatisfied()){
				++satisfied;
			}
		}
		if(satisfied==foods.size()){
			return true;
		}else{
			return false;
		}
	}
	
	public void draw (Graphics g,int width, int height)
	{
		if (getCurrentLevel() == LEVEL_1)
		{
			g.setColor(new Color(213, 138, 135));
			g.fillRect(0, 0, width, height);
			
		}
		else if (getCurrentLevel() == LEVEL_2)
		{
			g.setColor(new Color(241, 215, 214));
			g.fillRect(0, 0, width, height);
		
		}
		else if (getCurrentLevel() == LEVEL_3)
		{
			g.setColor(new Color(149, 191, 190));
			g.fillRect(0, 0, width, height);
			
		}
		else if (getCurrentLevel() == LEVEL_4)
		{
			g.setColor(new Color(125, 178, 168));
			g.fillRect(0, 0, width, height);
		
		}
		else if (getCurrentLevel() == LEVEL_5)
		{
			g.setColor(new Color(213, 138, 135));
			g.fillRect(0, 0, width, height);
		}
		g.drawImage(salt, 550, -700+700, 50, 50, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", 0, 26));
		g.drawString("Level : " + getCurrentLevel(), 50, -50+700);
		for(Food food : foods){
			food.draw(g);
		}
		for(Block obs : blocks){
			obs.draw(g);
		}
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public ArrayList<Food> getFoods() {
		return foods;
	}

	public void setFoods(ArrayList<Food> foods) {
		this.foods = foods;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}
	
	public int getNumberOfSalt() {
		return numberOfSalt;
	}

	public void setNumberOfSalt(int numberOfSalt) {
		this.numberOfSalt = numberOfSalt;
	}

	public boolean isLevelComplete() {
		return levelComplete;
	}

	public void setLevelComplete(boolean levelComplete) {
		this.levelComplete = levelComplete;
	}

	public double getkC() {
		return kC;
	}

	public void setkC(double kC) {
		this.kC = kC;
	}

	public double getsC() {
		return sC;
	}

	public void setsC(double sC) {
		this.sC = sC;
	}

	public boolean[] getCompletion() {
		return completion;
	}

	public void setCompletion(boolean[] completion) {
		this.completion = completion;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getLastLevel() {
		return lastLevel;
	}

	public void setLastLevel(int lastLevel) {
		this.lastLevel = lastLevel;
	}
	
	
}