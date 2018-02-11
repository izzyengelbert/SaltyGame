import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Block extends JPanel {
   private int rectX;
   private int rectY;
   private int rectWidth;
   private int rectHeight;
   private Point one;
   private Point two;
   private Point three;
   private Point four;
   private double kC;
   private double sC;
   private ArrayList<BeanLine> lines = new ArrayList<BeanLine>();
   
   
   protected void draw(Graphics g) {
      // draw the rectangle here
      g.setColor(Color.BLACK);
      g.fillRect(rectX,-rectY+700,rectWidth,rectHeight);
    
   }

   public Block(int x,int y, int width, int height, double kC, double sC){
	    rectX = x;
	    rectY = y;
	    rectWidth = width;
	    rectHeight = height;
	    setOne(new Point(x, y));
	    setTwo(new Point(x+width, y));
	    setThree(new Point(x+width, y-height));
	    setFour(new Point(x, y-height));
	    setkC(kC);
	    setsC(sC);
	    
	    lines.add(new BeanLine(getOne(),getTwo(),Color.BLACK,2,getkC(),getsC()));
		lines.add(new BeanLine(getTwo(),getThree(),Color.BLACK,2,getkC(),getsC()));
		lines.add(new BeanLine(getThree(),getFour(),Color.BLACK,2,getkC(),getsC()));
		lines.add(new BeanLine(getFour(),getOne(),Color.BLACK,2,getkC(),getsC()));
		
	   }

public int getRectX() {
	return rectX;
}

public void setRectX(int rectX) {
	this.rectX = rectX;
}

public int getRectY() {
	return rectY;
}

public void setRectY(int rectY) {
	this.rectY = rectY;
}

public int getRectWidth() {
	return rectWidth;
}

public void setRectWidth(int rectWidth) {
	this.rectWidth = rectWidth;
}

public int getRectHeight() {
	return rectHeight;
}

public void setRectHeight(int rectHeight) {
	this.rectHeight = rectHeight;
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

public Point getThree() {
	return three;
}

public void setThree(Point three) {
	this.three = three;
}

public Point getFour() {
	return four;
}

public void setFour(Point four) {
	this.four = four;
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

public ArrayList<BeanLine> getLines() {
	return lines;
}

public void setLines(ArrayList<BeanLine> lines) {
	this.lines = lines;
}
   
}