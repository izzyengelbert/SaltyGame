import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Food {

	private int size;
	//private int length;
	private int x;
	private int y;
	private int originX;
	private int originY;
	private BufferedImage image;
	private int reqSalt;
	private boolean satisfied;
	
	
	public Food(int size,int x, int y, BufferedImage image, int reqSalt){
		setX(x);
		setY(y);
		setReqSalt(reqSalt);
		setSize(size);
		//setLength(size);
		setImage(image);
	}
	
	public Food(int size,int x, int y, int reqSalt){
		setX(x);
		setY(y);
		setReqSalt(reqSalt);
		setSize(size);
		//setLength(size);
		try {
			setImage(image = ImageIO.read(getClass().getResource("/burger.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Food(int size,int x, int y, BufferedImage image){
		setX(x);
		setY(y);
		setReqSalt(100);
		setSize(size);
		//setLength(size);
		setImage(image);
		setSatisfied(false);
	}
	
	public Food(int x, int y, BufferedImage image){
		setSize(25);
		//setLength(25);
		setX(x);
		setY(y);
		setImage(image);
		setSatisfied(false);
	}
	
	public Food(BufferedImage image){
		setSize(25);
		//setLength(25);
		setImage(image);
		setSatisfied(false);
	}
	
	public void draw(Graphics g){
		//g.setColor(Color.MAGENTA);
		//g.fillRect(x, y, size, size);
		g.drawImage(image, x, y, size, size, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", 0, 26));
		g.drawString(Integer.toString(getReqSalt()), x+(size/4), y+(size/2));
	}
	
	public void update(){
		if(getReqSalt()==0){
			setSatisfied(true);
		}
	}
	
	public boolean detectSalt(Salt salt){
		int foodY = -getY()+700;
		if((salt.getX()>getX())&&(salt.getX()<getX()+getSize())&&(salt.getY()<foodY)&&(salt.getY()>foodY-getSize())){
			//System.out.println(getReqSalt());
			if(getReqSalt()>0){
				setReqSalt(getReqSalt()-1);
			}
			return true;
		}else{
			return false;
		}
		
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
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

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public int getReqSalt() {
		return reqSalt;
	}
	public void setReqSalt(int reqSalt) {
		this.reqSalt = reqSalt;
	}
	/*public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}*/

	public boolean isSatisfied() {
		return satisfied;
	}

	public void setSatisfied(boolean satisfied) {
		this.satisfied = satisfied;
	}

	public int getOriginX() {
		return originX;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}
	
	
}
