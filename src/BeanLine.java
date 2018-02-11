import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class BeanLine {

	public static final int NEGATIVE_ANGLE = 0;
	public static final int POSITIVE_ANGLE = 1;
	public static final int ZERO_ANGLE = 2;

	private Point one;
	private Point two;

	private Color color;
	private double length;
	private double xDiff;
	private double yDiff;
	private int size;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean flatX;
	private boolean flatY;
	private double angle;
	private double slope;
	private double bCoefficient;
	private double kC;
	private double sC;
	private int angleDir;
	
	public BeanLine(Point one, Point two, Color color, int size,double kC,double sC){
		setOne(one);
		setTwo(two);
		setxDiff();
		setyDiff();
		double s = getyDiff()/getxDiff();
		if(getyDiff()==0){
			setSlope(0);
		}else if(getxDiff()==0){
			setSlope(0);
		}else{
			setSlope(s);
		}
		setbCoefficient(getOne().getY()-(getSlope()*getOne().getX()));
		setLength();
		setColor(color);
		setSize(size);
		setkC(kC);
	    setsC(sC);
		if(getOne().getY()<getTwo().getY()){
			setUp(true);
			setDown(false);
		}else if(getOne().getY()>getTwo().getY()){
			setUp(false);
			setDown(true);
		}
		if(getOne().getY()==getTwo().getY()){
			setFlatX(true);
		}
		if(getOne().getX()==getTwo().getX()){
			setFlatY(true);
		}
		if(getOne().getX()>getTwo().getX()){
			setLeft(true);
			setRight(false);
		}else if(getOne().getX()<getTwo().getX()){
			setLeft(false);
			setRight(true);
		}
		setAngle(calculateAngle());
		
		if(getAngle()<0){
			setAngleDir(NEGATIVE_ANGLE);
			//System.out.println(getAngleDir());
		}else if(getAngle()>0&&getAngle()<90){
			setAngleDir(POSITIVE_ANGLE);
			//System.out.println(getAngleDir());
		}else if(getAngle()==90||getAngle()==0){
			setAngleDir(ZERO_ANGLE);
			//System.out.println(getAngleDir());
		}
	}

	public void draw(Graphics2D g){
		//System.out.println("draw Line");
		g.setColor(color);
		g.setStroke(new BasicStroke(size));
		//g.draw(new Line2D.Float(one.getX()-(size/2), one.getY()/*(-one.getY()+1000)*/-(size/2), two.getX()-(size/2), two.getY()/*(-two.getY()+1000)*/-(size/2)));
		//g.draw(new Line2D.Float(one.getX()-(size/2), (-one.getY()+1000)-(size/2), two.getX()-(size/2), (-two.getY()+1000)-(size/2)));
		//g.drawLine(one.getX()-(size/2), (-one.getY()+1000)-(size/2), two.getX()-(size/2), (-two.getY()+1000)-(size/2)));
		g.draw(new Line2D.Float(one.getX(), -one.getY()+700, two.getX(), -two.getY()+700));
		//g.fillRect(one.getX(), -one.getY()+1000, (int) getLength(), getSize());
	}
	
	public double distanceFromPoint(Salt salt){
		double distance;
		distance = Math.abs((getyDiff()*salt.getX())-(getxDiff()*salt.getY())+(getTwo().getX()*getOne().getY())-(getTwo().getY()*getOne().getX()));
		distance = distance / getLength();
		return distance;
	}
	
	public double calculateAngle(){
		if((two.getY()-one.getY())==0&&(two.getX()-one.getX())==0){
			return 0;
		}else if((two.getY()-one.getY())==0){
			return 0;
		}else if((two.getX()-one.getX())==0){
			return 90;
		}else{
			/*double dy = two.getY()-one.getY();
			double dx = two.getX()-one.getX();
			return Math.toDegrees(Math.atan(dy/dx));*/
			return Math.toDegrees(Math.atan(getyDiff()/getxDiff()));
		}
	}
	
	public boolean isPointOnLine(int x, int y){
		
		return false;
	}
	
	public Point getOne() {
		return one;
	}

	public void setOne(Point one) {
		this.one = one;
	}

	public Point getTwo() {
		return two;
	}

	public void setTwo(Point two) {
		this.two = two;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public String toString(){
		return one.getX()+","+one.getY()+" : "+two.getX()+","+two.getY();
	}


	public double getLength() {
		return length;
	}

	public void setLength(){
		double a,b;
		b = getxDiff()*getxDiff();
		a = getyDiff()*getyDiff();
		this.length = Math.sqrt(a+b);
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getxDiff() {
		return xDiff;
	}

	public void setxDiff(){
		this.xDiff = (getTwo().getX()-getOne().getX());
	}
	
	public void setxDiff(double xDiff) {
		this.xDiff = xDiff;
	}

	public double getyDiff() {
		return yDiff;
	}

	public void setyDiff(){
		this.yDiff = (getTwo().getY()-getOne().getY());
	}
	
	public void setyDiff(double yDiff) {
		this.yDiff = yDiff;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isFlatX() {
		return flatX;
	}

	public void setFlatX(boolean flatX) {
		this.flatX = flatX;
	}

	public boolean isFlatY() {
		return flatY;
	}

	public void setFlatY(boolean flatY) {
		this.flatY = flatY;
	}
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
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

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public double getbCoefficient() {
		return bCoefficient;
	}

	public void setbCoefficient(double bCoefficient) {
		this.bCoefficient = bCoefficient;
	}

	public int getAngleDir() {
		return angleDir;
	}

	public void setAngleDir(int angleDir) {
		this.angleDir = angleDir;
	}
}
