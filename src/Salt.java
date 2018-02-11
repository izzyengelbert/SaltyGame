import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Salt {
	
	//salt movement
	private int x;
	private int y;
	private int vx;
	private int vy;
	private double realVx;
	private double realVy;
	private int vt;
	private BeanLine currentLine;
	double xRem = 0;
	double yRem = 0;
	
	
	
	//salt characteristics
	private int size;
	private double weight;
	private double realSize;
	private double realArea;
	private Color color = Color.WHITE;

	//flags
	private boolean landed;
	private boolean moving;
	private boolean onScreen = false;
	private boolean inFood = false;
	private boolean hit;
	
	//random movement
	private int xDef;
	static final int MAX_RANDOM = 20;
	static final int MIN_RANDOM = -20;
	int inARow = 10;
	int minus = 0;
	int plus = 0;
	
	public Salt(int x, int y, int size, double weight, double realSize){
		setLanded(false);
		setX(x);
		setxDef(x);
		setY(y);
		setSize(size);
		setWeight(weight);
		setRealSize(realSize);
		setMoving(true);
		setVx(0);
		calculateArea();
		calculateVt();
		setVy(getVt());
	}

	public void update(ArrayList<BeanLine> lines, ArrayList<Block> blocks, ArrayList<Food> foods){
		if(isOnScreen()){
			if((getCurrentLine()!=null)&&(getCurrentLine().getAngle()!=0)&&(getCurrentLine().getAngle()!=90)&&(isLanded())&&(!isHit())){
				movementOnInclinedPlane();
			}
			randomMovement();
			setY(getY()+getVy());
			setX(getX()+getVx());
			if(blocks.size()!=0){
				for(Block block : blocks){
					detectLines(block.getLines());
				}
			}
			if(foods.size()!=0){
				detectFood(foods);
			}
			if(lines.size()!=0){
				detectLines(lines);
			}
			checkMoving();
			
		}
	}
	
	public void draw(Graphics g){
		if(isOnScreen()){
			g.setColor(color);
			g.fillRect(x-(size/2), (-y+700)-(size/2), size, size);
		}
	}
	
	public void calculateVt(){
		if(getVy()==0&&!isLanded()){
			int vt = (int)Math.sqrt((2*(getWeight()/1000)*GameWorld.G)/(GameWorld.CD*GameWorld.RHO*1000*(getRealArea()/10000)));
			setVt(-vt);
			//System.out.println(vt);
		}
	}
	
	public void movementOnInclinedPlane(){
		double angle = Math.abs(currentLine.getAngle());
		double sinRad = Math.sin(angle*Math.PI/180);
		double cosRad = Math.cos(angle*Math.PI/180);
		double movF = /*0.2 + */Math.abs((getWeight()/1000)*GameWorld.G*sinRad);
		double normF = Math.abs((getWeight()/1000)*GameWorld.G*cosRad);
		double maksStaticF= currentLine.getsC()*normF;
		//System.out.println("angle : "+angle);
		//System.out.println(maksStaticF);
		//System.out.println(movF);
		if((movF>maksStaticF)&&!isHit()){
			setMoving(true);
		}else{
			setMoving(false);
		}
		if(isMoving()){
				double a = GameWorld.G*(sinRad-(currentLine.getkC()*cosRad));
				a = a/10;
			if(currentLine.getAngle()<0){
				double rvx = (a*cosRad)/2;
				setRealVx(rvx);
			}else if(currentLine.getAngle()>0){
				double rvx = (-a*cosRad)/2;
				setRealVx(rvx);
			}
			double rvy = (-a*sinRad)/2;
			setRealVy(rvy);
		}else if(!isMoving()){
			setRealVx(0);
			setRealVy(0);
		}
		
		if(isMoving()&&isLanded()){
			//System.out.println("vx : " + realVx);
			//System.out.println("vy : " + realVy);
			xRem += realVx;
			yRem += realVy;
			
			
			//X PART
			if(getCurrentLine().getAngle()<0){
				if(xRem>1&&xRem<2){
					setVx((int)xRem);
					xRem = xRem-(int)xRem;
				}else{
					setVx(0);
				}
			}else if(getCurrentLine().getAngle()>0){
				if(xRem<-1&&xRem>-2){
					setVx((int)xRem);
					xRem = xRem+((int)(-xRem));
				}else{
					setVx(0);
				}
			}
			//Y PART
			if(yRem>-2&&yRem<-1){
				setVy((int)yRem);
				yRem = yRem+((int)(-yRem));
			}else{
				setVy(0);
			}
			//System.out.println("xRem : "+xRem);
			//System.out.println("yRem : "+yRem);
			double distance = currentLine.distanceFromPoint(this);
			if(distance<2&&distance>1){
				if(Math.abs(currentLine.getAngle())<45){
					setY(getY()+(int)distance+1);
				}else if(Math.abs(currentLine.getAngle())>45){
					setY(getY()+(int)distance+1);
				}
			}else if(distance<1){
				if(Math.abs(currentLine.getAngle())<45){
					setY(getY()+(int)distance+2);
				}else if(Math.abs(currentLine.getAngle())>45){
					setY(getY()+(int)distance+2);
				}
			}
		}
		
	}
	
	public void checkMoving(){
		if(!isMoving()){
			setVx(0);
			setVy(0);
		}
	}
	
	public void randomMovement(){
		if(!isLanded()){
			Random rand = new Random();
			int n = rand.nextInt(10);
			if(n==0&&getX()>MIN_RANDOM+getxDef()&&getX()<MAX_RANDOM+getxDef()&&minus<=inARow){
				setX(getX()-1);
				++minus;
				if(minus==inARow){
					plus=0;
				}
			}else if(n==1&&getX()>MIN_RANDOM+getxDef()&&getX()<MAX_RANDOM+getxDef()&&plus<inARow){
				setX(getX()+1);
				++plus;
				if(plus==inARow){
					minus=0;
				}
			}
		}
	}
	
	public void detectFood(ArrayList<Food> foods){
		for(Food food : foods){
			if(!isInFood()){
				setInFood(food.detectSalt(this));
			}
		}
	}
	
	public void detectLines(ArrayList<BeanLine> lines){
		
		if(!this.isLanded()){
			//System.out.println("tes");
			for(BeanLine line : lines){
				double distance = line.distanceFromPoint(this);
				if(distance<(getVt()/2)){
					if(line.isLeft()&&(getX()<line.getOne().getX())&&(getX()>line.getTwo().getX())){
						setVy(-1);
					}else if(line.isRight()&&(getX()>line.getOne().getX())&&(getX()<line.getTwo().getX())){
						setVy(-1);
					}
				}else if(distance<line.getSize()+1){
					if(line.isLeft()&&(getX()<line.getOne().getX())&&(getX()>line.getTwo().getX())){
						setVy(0);
						setLanded(true);
						if(distance<2&&distance>1){
							setY(getY()+(int)distance+1);
							if(line.getAngle()<0){
								setX(getX()+(int)distance+1);
							}else{
								setX(getX()+(int)distance-2);
							}
						}else if(distance<1){
							setY(getY()+(int)distance+2);
							if(line.getAngle()<0){
								setX(getX()+(int)distance+2);
							}else{
								setX(getX()+(int)distance-2);
							}
						}
						setCurrentLine(line);
						break;
					}else if(line.isRight()&&(getX()>line.getOne().getX())&&(getX()<line.getTwo().getX())){
						setVy(0);
						setLanded(true);
						if(distance<2&&distance>1){
							setY(getY()+(int)distance+1);
							if(line.getAngle()<0){
								setX(getX()+(int)distance+1);
							}else{
								setX(getX()+(int)distance-2);
							}
						}else if(distance<1){
							setY(getY()+(int)distance+2);
							if(line.getAngle()<0){
								setX(getX()+(int)distance+2);
							}else{
								setX(getX()+(int)distance-2);
							}
						}
						if(line.getAngleDir()!=BeanLine.ZERO_ANGLE){
							setCurrentLine(line);
						}
						break;
					}
				}else{
					setVy(getVt());
					setLanded(false);
				}
			}
		}else{
			if(currentLine!=null){
				for(BeanLine line : lines){
					double distance = (int) line.distanceFromPoint(this);
					double pointLocation = (line.getSlope()*getX())+line.getbCoefficient();
					
					if((line!=currentLine)&&(line.getAngleDir()!=BeanLine.ZERO_ANGLE)&&((currentLine.getAngleDir()!=line.getAngleDir())||(getY()<pointLocation))){
						if(distance<3){
							if(line.isLeft()&&(getX()<line.getOne().getX())&&(getX()>line.getTwo().getX())){
								setMoving(false);
								setHit(true);
								break;
							}else if(line.isRight()&&(getX()>line.getOne().getX())&&(getX()<line.getTwo().getX())){
								setMoving(false);
								setHit(true);
								break;
							}else{
								setHit(false);
								setMoving(true);
							}
						}
					}else if(line!=getCurrentLine()&&distance<line.getSize()&&line.getAngleDir()!=BeanLine.ZERO_ANGLE){
						setCurrentLine(line);
					}else{
						if(distance<3){
							if(line.isLeft()&&line.isFlatX()&&(getX()<line.getOne().getX())&&(getX()>line.getTwo().getX())){
								setMoving(false);
								setHit(true);
								break;
							}else if(line.isRight()&&line.isFlatX()&&(getX()>line.getOne().getX())&&(getX()<line.getTwo().getX())){
								setMoving(false);
								setHit(true);
								break;
							}if(line.isUp()&&line.isFlatY()&&(getY()>line.getOne().getY())&&(getY()<line.getTwo().getY())){
								setMoving(false);
								setHit(true);
								break;
							}else if(line.isDown()&&line.isFlatY()&&(getY()<line.getOne().getY())&&(getY()>line.getTwo().getY())){
								setMoving(false);
								setHit(true);
								break;
							}else{
								setHit(false);
								setMoving(true);
							}
						}
					}
					
				}
				if(currentLine.isLeft()&&((x>currentLine.getOne().getX())||(x<currentLine.getTwo().getX()))){
					//System.out.println("test");
					setVx(0);
					setVy(0);
					setLanded(false);
				}else if(currentLine.isRight()&&((x<currentLine.getOne().getX())||(x>currentLine.getTwo().getX()))){
					setVx(0);
					setVy(0);
					setLanded(false);
				}else{
					setLanded(true);
				}
			}		
		}
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getRealSize() {
		return realSize;
	}

	public void setRealSize(double realSize) {
		this.realSize = realSize;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isOnScreen() {
		return onScreen;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}
	public void calculateArea(){
		setRealArea(realSize*realSize*6);
	}
	
	public double getRealArea() {
		return realArea;
	}
	
	public void setRealArea(double realArea) {
		this.realArea = realArea;
	}

	public int getVt() {
		return vt;
	}

	public void setVt(int vt) {
		this.vt = vt;
	}

	public boolean isLanded() {
		return landed;
	}

	public void setLanded(boolean landed) {
		this.landed = landed;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public int getxDef() {
		return xDef;
	}

	public void setxDef(int xDef) {
		this.xDef = xDef;
	}

	public BeanLine getCurrentLine() {
		return currentLine;
	}

	public void setCurrentLine(BeanLine currentLine) {
		this.currentLine = currentLine;
	}

	public double getRealVx() {
		return realVx;
	}

	public void setRealVx(double realVx) {
		this.realVx = realVx;
	}

	public double getRealVy() {
		return realVy;
	}

	public void setRealVy(double realVy) {
		this.realVy = realVy;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isInFood() {
		return inFood;
	}

	public void setInFood(boolean inFood) {
		this.inFood = inFood;
	}
}
