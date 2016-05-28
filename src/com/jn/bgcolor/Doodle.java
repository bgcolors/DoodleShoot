package com.jn.bgcolor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
/**
 * 这个类实现的是游戏主角doodle
 * @author bgcolor
 *
 */
public class Doodle {
	/**
	 * doodle的位置坐标
	 */
	int x;
	int y;
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * doodle旧的位置和坐标，供碰撞后移动使用
	 */
	int oldX;
	int oldY;
	/**
	 * 供碰撞判断的一个参量，每刷新一次，进行累加，用于记录doodle旧的位置
	 */
	int i = 0;
	/**
	 * 主框架的引用
	 */
	DoodleFrame df;
	/**
	 * 怪物的容器
	 */
	List<Mouster> mousters;
	/**
	 * 墙体的容器
	 */
	List<Wall> walls;
	/**
	 * doodle大招剩余使用次数
	 */
	private int superFire = 2;
	public void setSuperFire(int superFire) {
		this.superFire = superFire;
	}
	public int getSuperFire() {
		return superFire;
	}
	/**
	 * doodle两个方向的速度
	 */
    private static int X_SPEED = 5;
    private static int Y_SPEED = 5;
    /**
     * 判断doodle东西南北四个方向的变量
     */
    private boolean north,east,south,west = false;
    /**
     * 活动区域宽高
     */
    private static int AREA_X = 550;
    private static int AREA_Y = 400;
    /**
     * 为draw()函数准备的一个偏移量
     */
    private static int A = 30;
    /**
     * doodle的生命值
     */
    private int hp = 3;
    /**
     * doodle的生命
     */
    private boolean live = true;
    /**
     * 素材图片的路径
     */
	private static final String IMAGEPATH="images/";
	/*
	 * 载入素材图片的内存
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();	
    static Image[] imgs = {
		tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"N.gif")),
		tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"NE.gif")),
		tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"E.gif")),
		tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"SE.gif")),
		tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"S.gif")),
		tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"SW.gif")),
		tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"W.gif")),
		tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"NW.gif"))
    }; 
    /**
     * 所有人物方向的枚举，一次为北，东北，东，东南，南，西南，西，西北
     * @author bgcolor
     *
     */
    enum Direction {N,NE,E,SE,S,SW,W,NW,stop};
    /**
     * doodle的方向
     */
    private Direction dir = Direction.stop;
    /**
     * doodle发射子弹的方向
     */
    private Direction ptdir = Direction.E;
    
	
	public Doodle(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Doodle(int x, int y,DoodleFrame df) {
		this(x,y);
		this.df = df;
	}
	
	
	/**
	 * doodle的构建器，用于创建主角doodle
	 * @param x doodle的x坐标
	 * @param y doodle的y坐标
	 * @param df 主框架的引用
	 * @param mousters 怪物容器的引用
	 * @param walls 墙体容器的引用
	 */
	public Doodle(int x, int y, DoodleFrame df, List<Mouster> mousters,
			List<Wall> walls) {
		this.x = x;
		this.y = y;
		this.df = df;
		this.mousters = mousters;
		this.walls = walls;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * doodle的移动函数，每刷新一次屏幕调用一次
	 */
	public void move() {

		if (i % 2 == 0) {
			oldX = x;
			oldY = y;
		}
		
		switch(dir) {
		case N:
			y-=Y_SPEED;
			break;
		case NE:
			y-=Y_SPEED/1.414;
			x+=X_SPEED/1.414;
			break;
		case E:
			x+=X_SPEED;
			break;
		case SE:
			y+=Y_SPEED/1.414;
			x+=X_SPEED/1.414;
			break;
		case S:
			y+=Y_SPEED;
			break;
		case SW:
			y+=Y_SPEED/1.414;
			x-=X_SPEED/1.414;
			break;
		case W:
			x-=X_SPEED;
			break;
		case NW:
			y-=Y_SPEED/1.414;
			x-=X_SPEED/1.414;
			break;
		}
		if (this.dir != Direction.stop) {
			this.ptdir = this.dir;
		}
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		if (x > AREA_X - A ) x = AREA_X - A;
		if (y > AREA_Y - A ) y = AREA_Y - A;
	}
	/**
	 * 画出doodle
	 * @param g 画笔
	 */
	public void draw(Graphics g) {

		if (!live) {
			return;
		}

		switch(ptdir) {
		case N:
			g.drawImage( imgs[0], x, y, null);
			break;
		case NE:
			g.drawImage( imgs[1], x, y, null);
			break;
		case E:
			g.drawImage( imgs[2], x, y, null);
			break;
		case SE:
			g.drawImage( imgs[3], x, y, null);
			break;
		case S:
			g.drawImage( imgs[4], x, y, null);
			break;
		case SW:
			g.drawImage( imgs[5], x, y, null);
			break;
		case W:
			g.drawImage( imgs[6], x, y, null);		
			break;
		case NW:
			g.drawImage( imgs[7], x, y, null);
			break;
		}
		BloodBar b = new BloodBar( this, x, y + 5);
		b.draw(g);
		move();
		i++;
	}
	/**
	 * 对键盘消息进行相应，上下左右控制方向，f2重新活过来（由于游戏比较难，基本上不可能一命通关，所以没做成重新开始），f12跳关，可以直接跳到游戏全关，并且处理方位的控制
	 * @param e 鼠标事件
	 */
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_Q:
			if (superFire > 0) {
				superFire();
			}
			break;
		case KeyEvent.VK_F12:
			df.setScore(df.getScore()+500);
			df.mousters.clear();
			break;
		case KeyEvent.VK_F2: 
			if (df.isIfDead()) {
				this.setHp(3);
				this.x = 150;
				this.y = 150;
				this.setLive(true);
				df.setRank(df.getRank());
				df.setIfDead(false);
				df.bullets.clear();
			}
			break;
		case KeyEvent.VK_SPACE:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			west = false;
			break;
		case KeyEvent.VK_RIGHT:
			east = false;
			break;
		case KeyEvent.VK_UP:
			north = false;
			break;
		case KeyEvent.VK_DOWN:
			south = false;
			break;
		}
		locateDirection();
	}
	/**
	 * 对键盘消息进行相应，与keyReleased()一同确定八个方位
	 * 	 * @param e 鼠标事件
	 */
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			west = true;
			break;
		case KeyEvent.VK_RIGHT:
			east = true;
			break;
		case KeyEvent.VK_UP:
			north = true;
			break;
		case KeyEvent.VK_DOWN:
			south = true;
			break;
		}
		locateDirection();
	}
	/**
	 * 判断最终的移动方向
	 */
	void locateDirection() {
		if(west && !north && !east && !south) dir = Direction.W;
		else if(!west && north && !east && !south) dir = Direction.N;
		else if(!west && !north && east && !south) dir = Direction.E;
		else if(!west && !north && !east && south) dir = Direction.S;
		else if(west && north && !east && !south) dir = Direction.NW;
		else if(west && !north && !east && south) dir = Direction.SW;
		else if(!west && north && east && !south) dir = Direction.NE;
		else if(!west && !north && east && south) dir = Direction.SE;
		else if(!west && !north && !east && !south) dir = Direction.stop;
	}
	/**
	 * doodle射出子弹的方法
	 * @return 返回一颗子弹
	 */
	public Bullet fire() {
		if (!live) {
			return null;
		}
		Bullet b = null;
		b = new Bullet( x + A, y + A, ptdir, true, this.df);
		df.bullets.add(b);
		return b;
	}
	/**
	 * doodle大招,损失当前所有怪物1/2的血量，并且清除弹幕
	 * @return 返回一颗子弹
	 */
	public Bullet superFire() {
		if (!live)
		return null;		
		Direction[] dirs = Direction.values();
		Bullet b = null;		
		df.bullets.clear();
		for(int i = 0;i < 8; i++) {
			b = new Bullet(x + A, y + A , dirs[i], true, this.df);
			df.bullets.add(b);
		}
		for(int i = 0;i < df.mousters.size(); i++) {
			Mouster m = df.mousters.get(i);
			m.setHp(m.getHp()/2);
			if(m.getHp() <= 0) {
				m.setLive(false);
			}
		}
		this.superFire--;
		return b;
	}
	/**
	 * 获得doodle的碰撞体
	 * @return 返回一个Rectangle作为其碰撞体
	 */
	public Rectangle getRect() {
			return new Rectangle(x + 15, y + 15, A, A); 
	}
	/**
	 * 用于判断与墙体的碰撞
	 * @param walls 墙体容器的引用
	 * @return 返回是否与墙体碰撞，是返回true，否返回false
	 */
	public boolean collidesWithWalls(java.util.List<Wall> walls) {
		for(int i=0; i<walls.size(); i++) {
			Wall w = walls.get(i);
			if(this.live && this.getRect().intersects(w.getRect())) {
				this.x = oldX;
				this.y = oldY;
				return true;
			}
		}
		return false;
	}
	/**
	 * 用于判断与怪物的碰撞
	 * @param mousters 怪物容器的引用
	 * @return 返回是否与怪物碰撞，是返回true，否返回false
	 */
	public boolean collidesWithMousters(java.util.List<Mouster> mousters) {
		for(int i=0; i<mousters.size(); i++) {
			Mouster m = mousters.get(i);
			if(this.live && m.isLive() && this.getRect().intersects(m.getRect())) {
				this.x = oldX;
				this.y = oldY;
				m.x = m.oldX;
				m.y = m.oldY;
				return true;
			}
		}
		return false;
	}
	
}
