import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel implements MouseListener {
	
	private Color bg = new Color(244, 207, 206);
	private MainFrame frame;
	private JPanel buttonComplex = new JPanel(null, true);
	private BoxLayout layout; 
	private static final int BUT_COMP_SPACE = 15;
	private static final int BUT_SIZE = 100;
	private static final Dimension BUT_COMP_DIM = new Dimension((BUT_SIZE*3+BUT_COMP_SPACE*2), 
			(BUT_SIZE*2+BUT_COMP_SPACE));
	private JButton[] button = new JButton[6];
	
	public Menu(MainFrame frame) {
		this.frame = frame;
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		setBackground(bg);
		add(Box.createVerticalGlue());
		add(setButtonComplex());
		add(Box.createVerticalGlue());
	}
	
	public JPanel setButtonComplex() {
		buttonComplex.setPreferredSize(BUT_COMP_DIM);
		buttonComplex.setMinimumSize(BUT_COMP_DIM);
		buttonComplex.setMaximumSize(BUT_COMP_DIM);
		buttonComplex.setLayout(new GridLayout(2, 3, BUT_COMP_SPACE, BUT_COMP_SPACE));
		buttonComplex.setBackground(bg);
		genButton();
		return buttonComplex;
	}
	
	public void genButton() {
		button[0] = new JButton("exit");
		button[0].setIcon(new ImageIcon(getClass().getResource("/img/button_exit.png")));
		button[0].setBorderPainted(false);
		button[0].addMouseListener(this);
		buttonComplex.add(button[0]);
		for (int i = 1; i < 6; ++i) {
			button[i] = new JButton(""+i);
			button[i].setIcon(new ImageIcon(getClass().getResource("/img/button"+i+".png")));
			button[i].setBorderPainted(false);
			button[i].addMouseListener(this);
			button[i].setEnabled(frame.getCompletion(i));
			buttonComplex.add(button[i]);
		}
	}
	
	public void setCertainButton(int index, boolean arg) {
		button[index].setEnabled(arg);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		JButton obj = (JButton) arg0.getSource();
		if (obj.isEnabled()) {
			if (obj == button[0])
				System.exit(0);
			else if (obj == button[1])
				frame.jumpLevel(1);
			else if (obj == button[2])
				frame.jumpLevel(2);
			else if (obj == button[3])
				frame.jumpLevel(3);
			else if (obj == button[4])
				frame.jumpLevel(4);
			else if (obj == button[5])
				frame.jumpLevel(5);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
}
