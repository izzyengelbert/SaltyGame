import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameWorld {

	//world
	public static final double G = 9.8;
	public static final double CD = 0.5;
	public static final double RHO = 1;
	private int width;
	private int height;
	private boolean start;
	private int defaultX = width/2;
	private int defaultY = height/2;
	
	//salt
	private int saltDefX = 550;
	private int saltDefY = 650;
	private double saltWeight = 0.2;
	private int saltSize = 3;
	private double saltRealSize = 0.05;
	private int saltIndex;
	
	//objects
	private ArrayList<Salt> salts;
	private ArrayList<Point> points;
	private ArrayList<BeanLine> lines;
	
	//mouse event
	private boolean mousePressed;
	private int mouseX;
	private int mouseY;
	
	//for BeanLine
	private boolean drawingLine;
	int PointIndex;
	Point prevPoint;
	private Point tempPoint;
	int stopDrawLine;
	int beanLineSize = 2;
	double beanKC = 0.01;
	double beanSC = 0.31;
	Color beanLineColor = Color.GREEN;
	
	//levels
	private Levels lvl;

	
	public GameWorld(int width, int height){
		this.width = width;
		this.height = height;
		setLvl(new Levels(Levels.LEVEL_3));
		lvl.setkC(beanKC);
		lvl.setsC(beanSC);
		setStart(false);
		init();
	}
	
	public GameWorld(int width, int height, int level){
		this.width = width;
		this.height = height;
		setLvl(new Levels(level));
		init();
	}
	
	private void init() {
		PointIndex=0;
		stopDrawLine=0;
		setStart(false);
		saltIndex = 0;
		lvl.initLevel();
		salts = new ArrayList<Salt>();
		points = new ArrayList<Point>();
		lines = new ArrayList<BeanLine>();
		lvl.setLevelComplete(false);
		
		for(int i=0;i<lvl.getNumberOfSalt();++i){
			salts.add(new Salt(saltDefX,saltDefY,saltSize,saltWeight,saltRealSize));
		}
	}

	public void update(){
		if(lvl.isLevelComplete()){
			salts.clear();
			points.clear();
			lines.clear();
			init();
		}else{
			lvl.update();
		}
		
		if(isStart()&&saltIndex<salts.size()){
			salts.get(saltIndex).setOnScreen(true);
			++saltIndex;
		}
		
		for(int i=0;i<salts.size();++i){
			salts.get(i).update(lines, lvl.getBlocks(),lvl.getFoods());
			if(salts.get(i).isInFood()){
				salts.remove(i);
			}
		}
		drawLine();
		for(int i=0;i<lines.size();++i){
			//System.out.println(i+" : "+lines.get(i).getAngle());
		}
		for(Block block : lvl.getBlocks()){
			//System.out.println(block.getLines());
		}
	}
	
	public void drawLine(){
		if(isMousePressed()){
			setTempPoint(new Point(getMouseX(), (-getMouseY()+height)));
			if(PointIndex==stopDrawLine){
				points.add(tempPoint);
				++PointIndex;
			}
		}else if(!isMousePressed()){
			setTempPoint(new Point(getMouseX(),(-getMouseY()+height)));
			if(PointIndex-1==stopDrawLine){
				points.add(tempPoint);
				lines.add(new BeanLine(points.get(PointIndex-1),points.get(PointIndex),beanLineColor,beanLineSize,beanKC,beanSC));
				++PointIndex;
				stopDrawLine=PointIndex;
			}
		}
	}
	
	public void draw(Graphics g){
		
		getLvl().draw(g, width, height);
		
		for(BeanLine line : lines){
			line.draw((Graphics2D)g);
		}
		for(Salt salt : salts){
			salt.draw(g);
		}
	}
	
	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	public ArrayList<BeanLine> getLines() {
		return lines;
	}

	public void setLines(ArrayList<BeanLine> lines) {
		this.lines = lines;
	}

	public Point getTempPoint() {
		return tempPoint;
	}

	public void setTempPoint(Point tempPoint) {
		this.tempPoint = tempPoint;
	}

	public int getDefaultX() {
		return defaultX;
	}

	public void setDefaultX(int defaultX) {
		this.defaultX = defaultX;
	}

	public int getDefaultY() {
		return defaultY;
	}

	public void setDefaultY(int defaultY) {
		this.defaultY = defaultY;
	}

	public boolean isDrawingLine() {
		return drawingLine;
	}

	public void setDrawingLine(boolean drawingLine) {
		this.drawingLine = drawingLine;
	}

	public Levels getLvl() {
		return lvl;
	}

	public void setLvl(Levels lvl) {
		this.lvl = lvl;
	}
	
}
