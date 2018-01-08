import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Game extends JFrame {
	private static final long serialVersionUID = 333L;

	private final int XSIZE = 15;
	private final int YSIZE = 15;

	private final int PWIDTH = 70;
	private final int PHEIGHT = 10;

	private int x = 3;
	private int y = 5;

	private int px = 10;
	private int py = 0;

	private int dx = 3;
	private int dy = 3;

	private Color color = Color.blue.brighter();
	private Color[] colors = { Color.cyan.darker(), Color.PINK.darker(), Color.gray.brighter(), Color.ORANGE.darker() };

	public static void main(String[] args) {
		new Game();
	}

	public Game() {
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("TEST");

		py = this.getHeight() - 15;
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				Thread.currentThread();
				while (!Thread.interrupted()) {
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Game.this.repaint();
				}
			}

		});
		th.start();

		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					moveRight();
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					moveLeft();
				}
			}
		});
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		java.awt.Image im = this.createImage(getWidth(), getHeight());
		mypaint(im.getGraphics());
		g.drawImage(im, 0, 0, getWidth(), getHeight(), null);
	}

	private void moveRight() {
		this.px += 20;
		if (px + PWIDTH > this.getWidth())
			px = this.getWidth() - PWIDTH;
	}

	private void moveLeft() {
		this.px -= 20;
		if (px < 0) {
			px = 0;
		}
	}

	public void mypaint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.YELLOW);
		g.fillOval(x, y, XSIZE, YSIZE);
		x += dx;
		y += dy;

		g.setColor(Color.RED);
		g.fillRect(px, py, PWIDTH, PHEIGHT);

		if (x >= px && x <= px + PWIDTH && y + YSIZE >= py) {
			y = py - YSIZE - 1;
			dy = -dy;
			changeColor();
		} else {
			if (x + XSIZE <= 0) {
				x = XSIZE;
				dx = -dx;
			}
			if (x + XSIZE >= this.getWidth()) {
				x = this.getWidth() - XSIZE;
				dx = -dx;
			}
			if (y + YSIZE <= 0) {
				y = YSIZE;
				dy = -dy;
			}
			if (y + YSIZE >= this.getHeight()) {
				y = this.getHeight() - YSIZE;
				dy = -dy;
			}
		}
	}

	private void changeColor() {
		Color c;
		do {
			c = colors[(int) (Math.random() * colors.length)];
		} while (c.equals(color));
		color = c;
	}
}
