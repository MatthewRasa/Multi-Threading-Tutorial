

import java.awt.Color;

/**
 * Color that automatically fades through different shades
 * @author Matthew Rasa
 */
public class DynamicColor extends Color implements Runnable {

	private Color color;
	private byte stage;
	
	public DynamicColor() {
		super(0);
		color = new Color(255,0,0);
		stage = 0;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				transition();
				Thread.sleep(17);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void transition() {
		switch(stage) {
			case 0:
				if (color.getGreen() == 255)
					stage++;
				else
					color = new Color(color.getRed(), color.getGreen() + 1, color.getBlue());
				break;
			case 1:
				if (color.getRed() == 0)
					stage++;
				else
					color = new Color(color.getRed() - 1, color.getGreen(), color.getBlue());
				break;
			case 2:
				if (color.getGreen() == 0)
					stage++;
				else
					color = new Color(color.getRed(), color.getGreen() - 1, color.getBlue() + 1);
				break;
			case 3:
				if (color.getRed() == 192)
					stage++;
				else
					color = new Color(color.getRed() + 1, color.getGreen(), color.getBlue());
				break;
			case 4:
				if (color.getBlue() == 0)
					stage = 0;
				else
					color = new Color(color.getRed() == 255 ? color.getRed() : color.getRed() + 1, color.getGreen(), color.getBlue() - 1);
				break;
		}
	}
	
	@Override
	public int getRGB() {
		return color.getRGB();
	}
	
}
