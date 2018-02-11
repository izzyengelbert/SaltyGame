import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * This source code is written as an assignment for final project
 * in class Math and Physics for Games Accelerated Term 2016/2017.
 * For educational purpose only.
 */

/**
 * @author Livia Lohanda
 * The main frame for The Salty Game
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8435152190020302326L;
	private static final int FRAME_WIDTH = 1200;
	private static final int FRAME_HEIGHT = 700;
	private static final int FRAME_X = 50;
	private static final int FRAME_Y = 50;
	private static final String title = "The Salty Game";
	private CardLayout layout = new CardLayout();
	private Container content;
	private ArrayList<JPanel> screen = new ArrayList<JPanel>();
	private int currentLevel = 0;
	private boolean[] completion = new boolean[6];
	private Menu menu;
	private GamePanel gp;
	
	
	public MainFrame () throws IOException {
		// set frame
		setTitle(title);
		setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		this.setLocationRelativeTo(null);
		
		
		// set content pane
		content = getContentPane();
		content.setLayout(layout);
		
		//add levels
		completion[0] = true;
		completion[1] = true;
		//add menu pane
		menu = new Menu(this);
		screen.add(menu);
		content.add("MENU", screen.get(0));
		
		content.add("START", startScreen());
		layout.show(content, "START");
	}
	
	public JPanel startScreen() {
		JPanel startScreen = new JPanel();
		startScreen.setBackground(new Color(197, 231, 225));
		startScreen.setLayout(new BoxLayout(startScreen, BoxLayout.Y_AXIS));
		startScreen.add(Box.createVerticalGlue());
		
		JLabel title = new JLabel(this.title);
		title.setFont(new Font("Calibri", Font.BOLD, 50));
		title.setForeground(new Color(85, 141, 140));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.CENTER);
		title.setAlignmentX(CENTER_ALIGNMENT);
		startScreen.add(title);
		
		JLabel tip = new JLabel ("Right click to main menu");
		tip.setFont(new Font("Calibri", Font.BOLD, 20));
		tip.setForeground(new Color(137, 49, 47));
		tip.setHorizontalAlignment(SwingConstants.CENTER);
		tip.setVerticalAlignment(SwingConstants.CENTER);
		tip.setAlignmentX(CENTER_ALIGNMENT);
		startScreen.add(tip);
		
		JLabel tip2 = new JLabel ("Left click to start");
		tip2.setFont(new Font("Calibri", Font.BOLD, 20));
		tip2.setForeground(new Color(137, 49, 47));
		tip2.setHorizontalAlignment(SwingConstants.CENTER);
		tip2.setVerticalAlignment(SwingConstants.CENTER);
		tip2.setAlignmentX(CENTER_ALIGNMENT);
		startScreen.add(tip2);
		startScreen.add(Box.createVerticalGlue());
		
		startScreen.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					jumpLevel(1);
				else if (e.getButton() == MouseEvent.BUTTON3)
					if(currentLevel!=0){
						jumpLevel(0);
					}else{
						jumpLevel(6);
					}
					
			}
		});
		
		return startScreen;
	}
	
	public void jumpLevel(int i) {
		switch(i) {
			case 1:
				gp = new GamePanel(getWidth(),getHeight(),this);
				content.add("GAMEPANEL", gp);
				gp.startGame(i);
				layout.show(content, "GAMEPANEL");
				break;
			case 2:
				gp = new GamePanel(getWidth(),getHeight(),this);
				content.add("GAMEPANEL", gp);
				gp.startGame(i);
				layout.show(content, "GAMEPANEL");
				break;
			case 3:
				gp = new GamePanel(getWidth(),getHeight(),this);
				content.add("GAMEPANEL", gp);
				gp.startGame(i);
				layout.show(content, "GAMEPANEL");
				break;
			case 4:
				gp = new GamePanel(getWidth(),getHeight(),this);
				content.add("GAMEPANEL", gp);
				gp.startGame(i);
				layout.show(content, "GAMEPANEL");
				break;
			case 5:
				gp = new GamePanel(getWidth(),getHeight(),this);
				content.add("GAMEPANEL", gp);
				gp.startGame(i);
				layout.show(content, "GAMEPANEL");
				break;
			case 0:
				gp.setRunning(false);
				gp = null;
				content.remove(2);
				layout.show(content, "MENU");
				break;
			default:
				layout.show(content, "MENU");
				break;
		}
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	public int setCurrentLevel(int currentLevel) {
		return currentLevel;
	}
	public void setCompletion(int level, boolean arg) {
		completion[level] = arg;
		menu.setCertainButton(level, arg);
	}
	
	public boolean getCompletion(int level) {
		return completion[level];
	}
	
	public void resetCompletion() {
		for (int i = 2; i < 6; ++i)
			completion[i] = false;
	}

	/**
	 * run the application here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame window = null;
				try {
					window = new MainFrame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				window.setVisible(true);
			}
		});
	}
	
	public CardLayout getLayout() {
		return layout;
	}

	public int getHeight() {
		return FRAME_HEIGHT;
	}

	public int getWidth() {
		return FRAME_WIDTH;
	}
	
	public void changeScreen (String label) {
		layout.show(content, label);
	}
	
	public boolean[] isComplete() {
		return completion;
	}

}
