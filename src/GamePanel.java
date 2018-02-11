import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{

	private static final int WIDTH = 1200;
	private static final int HEIGHT = 700;
	
	//private int time = 1000/30;
	
	
	private BufferedImage img;
	//private Graphics g;
	
	int canvasHeight;
	
	private boolean running = false;
	
	private GameWorld gw; //= new GameWorld(WIDTH,HEIGHT);
	/*
	 * editan liv
	 */
	private MainFrame frame;
	private int currentLevel;
	//private boolean[] completion = new boolean[6];
	
	public GamePanel(int width, int height, MainFrame frame) {
		/*
		 * editan liv
		 */
		this.frame = frame;
		/*
		 * udahan
		 */
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
		this.setVisible(true);
		this.addMouseListener(new MouseListener(){
			/*
			 * ini liv lagi
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					frame.jumpLevel(0);
			}
			/*
			 * sangat penting
			 */
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					gw.setMousePressed(true);
					gw.setDrawingLine(true);
					gw.setMouseX(e.getX());
					gw.setMouseY(e.getY());
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				gw.setMousePressed(false);
				gw.setDrawingLine(false);
				gw.setMouseX(e.getX());
				gw.setMouseY(e.getY());
			}
			
		});
		this.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent e) {
				//gw.setMouseX(e.getX());
				//gw.setMouseY(e.getY());
			}
			@Override
			public void mouseMoved(MouseEvent e) {}
			
		});
		
		canvasHeight = getHeight() - getInsets().top;
		img = (BufferedImage)createImage(getWidth(),getHeight());
		img = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
		if(img==null){
			System.out.println("image is null");
		}
		
	}

	public void startGame(int lvl){
		gw = new GameWorld(WIDTH, HEIGHT, lvl);
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void init(){
		running = true;
	}
	
	/*
	 * apalagi method ini oho
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
	@Override
	public void run() {
		init();
		while(running){
			update();
			render();
			printScreen();
			//System.out.println("tes");
			if(!gw.isStart()){
				try {
					Thread.sleep(3000);
					//Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				gw.setStart(true);
			}else{
				try {
					//Thread.sleep(time);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(gw.getLvl().getCompletion()[5]){
				frame.jumpLevel(0);
			}
		}
	}
	
	public void update(){
		
		gw.update();
		currentLevel = gw.getLvl().getCurrentLevel();
		frame.setCurrentLevel(gw.getLvl().getLastLevel());
		frame.setCompletion(gw.getLvl().getLastLevel(),true);
		//frame.setCompletion(level, arg);
	}
	
	public void render(){
		if(img != null)
		{				
			//System.out.println("render");
			//get graphics of the image where coordinate and function will be drawn
			Graphics g = img.getGraphics();
		
			//clear screen
			g.setColor(Color.white);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			gw.draw(g);
			
		}
	}
	
	public void printScreen()
	{
		try
		{
			Graphics g = getGraphics();
			if(img != null && g != null)
			{
				//System.out.println("printscreen");
				g.drawImage(img, 0, 0, null);
			}
			
			// Sync the display on some systems.
			// (on Linux, this fixes event queue problems)
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch(Exception ex)
		{
			System.out.println("Graphics error: " + ex);    
		}		
	}
	
	public int getCurrentLevel(){
		return currentLevel;
	}
	
	public void setCurrentLevel(int currentLevel){
		this.currentLevel = currentLevel;
	}

	/*public boolean[] getCompletion() {
		return completion;
	}

	public void setCompletion(boolean[] completion) {
		this.completion = completion;
	}*/
	
}
