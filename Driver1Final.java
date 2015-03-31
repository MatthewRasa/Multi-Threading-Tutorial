import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Driver1Final extends JFrame implements Runnable {

	private static int SCREEN_WIDTH, SCREEN_HEIGHT;
	private ArrayList<Integer> xList, yList;
	private JTextField field;
	private BufferedImage image;
	private Random rand;
	private int x, y, points;
	
	public Driver1Final() {
		setContentPane(new DrawingPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SCREEN_WIDTH = 500;
		SCREEN_HEIGHT = 500;
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		setLayout(null);
		setBackground(Color.BLACK);
		
		//---Initialize edge points---//
		
		xList = new ArrayList<Integer>();
		yList = new ArrayList<Integer>();
		xList.add(SCREEN_WIDTH/2);
		yList.add(50);
		xList.add(SCREEN_WIDTH/2 - (int)((SCREEN_HEIGHT-100)/Math.sqrt(3)));
		yList.add(SCREEN_HEIGHT-50);
		xList.add(SCREEN_WIDTH/2 + (int)((SCREEN_HEIGHT-100)/Math.sqrt(3)));
		yList.add(SCREEN_HEIGHT-50);
		field = new JTextField("1");
		field.setBounds(SCREEN_WIDTH-100,0,100,25);
		add(field);
		image = new BufferedImage(SCREEN_WIDTH,SCREEN_HEIGHT,BufferedImage.TYPE_INT_ARGB);
		for (int index = 0; index < xList.size(); index++)
			image.setRGB(xList.get(index), yList.get(index), Color.YELLOW.getRGB());
		rand = new Random();
		int r = rand.nextInt(3);
		x = xList.get(r);
		y = yList.get(r);
		points = 1;
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Driver1Final frame = new Driver1Final();
		new Thread(frame,"Refresh").start();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(16,666666);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public class DrawingPanel extends JPanel {
		
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			try {
				int text = Integer.parseInt(field.getText());
				if (text < 1)
					field.setText(""+1);
				if (text > 100)
					field.setText(""+100);
				points = text;
			} catch(NumberFormatException e) {
				
			}
			for (int index = 0; index < points; index++) {
				int r = rand.nextInt(3);
				x = (x+xList.get(r))/2;
				y = (y+yList.get(r))/2;
				image.setRGB(x,y,Color.YELLOW.getRGB());
			}
			g.setColor(Color.WHITE);
			g.drawString("Points per repaint:", SCREEN_WIDTH-220, 15);
			g.drawImage(image,0,0,null);
		}
		
	}
	
}
