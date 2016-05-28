package com.jn.bgcolor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
/**
 * 这个类是游戏的主框架
 * @author bgcolor
 *
 */
public class DoodleFrame extends Frame implements MouseListener {
	/**
	 * 游戏的宽高常量
	 */
	private static final int GAME_WIDTH = 550;
	private static final int GAME_HEIGHT = 400;
	/**
	 * 素材图片路径
	 */
	private static final String IMAGEPATH="images/";	
	/**
	 * 游戏是否开始
	 */
	private boolean ifstart = false;
	/**
	 * 游戏关卡数
	 */
	private int rank = 0;
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	/**
	 * 玩家得分
	 */
	private int score = 0;
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	private boolean ifDead = false;
	public boolean isIfDead() {
		return ifDead;
	}

	public void setIfDead(boolean ifDead) {
		this.ifDead = ifDead;
	}
	/**
	 * 用于双缓冲的伪图片
	 */
	Image offScreenImage = null;
	/*
	 * 子弹，怪物，墙体的容器
	 */
	List<Bullet> bullets = new ArrayList<Bullet>();
	List<Mouster> mousters = new ArrayList<Mouster>();
	List<Wall> walls = new ArrayList<Wall>();
	/*
	 * 自定义按钮及其定位所需的面板
	 */
	DoodleMenu dm = new DoodleMenu(4);
	DoodleMenu dm1 = new DoodleMenu(0);
	DoodleMenu dm2 = new DoodleMenu(2);
	DoodleMenu dm3 = new DoodleMenu(5);
	DoodleMenu dm4 = new DoodleMenu(7);
	Doodle doodle = new Doodle(150,150,this,mousters,walls);
	JPanel p = new JPanel();
	/*
	 * 将素材图片载入内存
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	static Image[] imgs = {
		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"frame1.gif")),
		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"end.gif"))	
	};
	/**
	 * 表示墙体位置地图的数组
	 */
	int [][] wallMap = {
			{120,120},
			{275,130},
			{425,75},
			{425,175},
			{100,240},
			{295,225},
			{455,290},
			{215,330}
	};
	/**
	 * 表示怪物位置地图的数组
	 */
	int [][] mousterMap = {
			{100,330},
			{210,75},
			{355,125},
			{420,235}
	};
	/**
	 * 主函数，你懂的！
	 * @param args 你懂的,too!
	 */
	public static void main(String[] args) {
		DoodleFrame df = new DoodleFrame();
		df.launchFrame();
	}
    /**
     * 创建游戏窗口
     */
	public void launchFrame() {		
	    if (!ifstart) {
	    	dm.setSize(GAME_WIDTH,GAME_HEIGHT);	    
	    	dm1.setSize(113,39);
		    dm2.setSize(113, 39);
		    dm1.addMouseListener(this);
		    dm2.addMouseListener(this);
		    p.setSize(113, 100);
		    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		    p.setLocation(230,175);
		    p.setOpaque(false);
		    p.add(dm1);
		    p.add(dm2);		
		    this.add(p);
		    this.add(dm);
	    }
	   
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setTitle("DoodleShoot");
		this.setLocation(350, 150);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());
		/*
		 * 负责刷新的线程
		 */
		new Thread(new PaintThread()).start();
	}
	/**
	 * 供重绘调用
	 */
	public void paint(Graphics g){	
		if (!ifstart) {
			super.paint(g);
			return;
		}
		if (rank < 7)  {			
			g.drawImage(imgs[0],0, 0,null);
			if(mousters.size() <= 0) {
				for(int i=0; i<4; i++) {
					mousters.add(new Mouster(mousterMap[i][0], mousterMap[i][1],  this, i,this.rank,3+rank * 3));
				}
				if (this.getScore() != 0) {
					this.setScore(this.getScore()+500);
				}
				doodle.setSuperFire(2);
				doodle.setX(150);
				doodle.setY(150);
				doodle.setHp(3);
				this.bullets.clear();
				this.rank++;
			}
			
			if(walls.size() <= 0) {
				for(int i=0; i<8; i++) {
					if (i % 2 == 0) {
						walls.add(new Wall(wallMap[i][0], wallMap[i][1],  this, 0));
					}
					else {
						walls.add(new Wall(wallMap[i][0], wallMap[i][1],  this, 1));
					}
				}
			}
			/*
			 * 画出所有子弹
			 */
			for(int i = 0; i < bullets.size(); i++) {
				Bullet b = bullets.get(i);
				b.hitDoodle(doodle);
				b.hitMousters(mousters);
				b.hitWalls(walls);
				b.draw(g);
			}
			/*
			 * 画出所有墙体
			 */
			for(int i = 0; i < walls.size(); i++) {
				Wall w = walls.get(i);
				w.draw(g);
			}
			/*
			 * 画出所有怪物
			 */
			for(int i = 0; i < mousters.size(); i++) {
				Mouster m = mousters.get(i);
				m.collidesWithMousters(mousters);
				m.collidesWithWalls(walls);
				m.draw(g);
			}
			/*
			 * 画出doodle
			 */
			doodle.collidesWithMousters(mousters);
			doodle.collidesWithWalls(walls);
			doodle.draw(g);
			/*
			 * 显示得分，生命值，关卡数
			 */
			g.drawString("your score:" + this.score, 20, 50);
			g.drawString("HP:" + doodle.getHp(), 20, 70);
			g.drawString("rank:" + this.rank, 490, 50);
			g.drawString("大招:" + doodle.getSuperFire(), 490, 70);
			if (ifDead) {			
				dm4.setSize(233, 77);
				dm4.setLocation(150,150);
				this.add(dm4);
			}
			else {
				this.remove(dm4);
			}

			super.paint(g);
		}
		else {
			/*
			 * 画初始菜单
			 */
			g.drawImage(imgs[1],0, 0,null);
			dm3.setSize( 113, 39);
			dm2.setSize( 113, 39);
			dm3.addMouseListener(this);
			dm2.addMouseListener(this);
			dm3.setLocation( 230, 200);
			dm2.setLocation( 230, 250);
			this.add(dm3);
			this.add(dm2);
			super.paint(g);
		}
		
	}
	/**
	 * 双缓冲技术
	 */
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.WHITE);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	/*
	 * 用于重绘的线程，每30毫秒刷一次
	 */
	private class PaintThread implements Runnable {
		public void run() {
			while(true) {
                repaint();
				try {
					Thread.sleep(30);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		
	}
	/**
	 * 各种鼠标响应
	 */
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == dm1) {
			dm1.setI(1);
		}
		if (e.getSource() == dm2) {
			dm2.setI(3);
		}
		
		if (e.getSource() == dm3) {
			dm3.setI(6);
		}
	}
	/**
	 * 各种鼠标响应
	 */
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == dm1) {
			dm1.setI(0);
		}
		if (e.getSource() == dm2) {
			dm2.setI(2);
		}	
		
		if (e.getSource() == dm3) {
			dm3.setI(5);
		}
		
	}
	/**
	 * 各种鼠标响应
	 */
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == dm2) {	
			System.exit(0);
		}
		
		if (e.getSource() == dm1) {
			this.ifstart = true;
			dm1.removeMouseListener(this);
			dm2.removeMouseListener(this);
			remove(dm1);
			remove(dm2);
			remove(p);
			remove(dm);
		}
		
		if (e.getSource() == dm3) {
			this.ifstart = true;
			dm3.removeMouseListener(this);
			dm2.removeMouseListener(this);
			remove(dm2);
			remove(dm3);
			this.mousters.clear();
			this.bullets.clear();
			doodle.setX(150);
			doodle.setY(150);
			doodle.setHp(5);
			this.rank = 0;
		}
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	/**
	 * 各种鼠标响应
	 */
	private class KeyMonitor extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			doodle.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			doodle.keyPressed(e);
		}
	}

}
