package com.jn.bgcolor;

import java.awt.*;
import java.util.Random;
import com.jn.bgcolor.Doodle.Direction;
/**
 * 这个类实现所有的怪物
 * @author bgcolor
 *
 */
public class Mouster {
	/**
	 * 怪物的位置
	 */
	int x;
	int y;
	/**
	 * 怪物的旧位置
	 */
	int oldX;
	int oldY;
	/**
	 * 主框架啊的引用
	 */
	DoodleFrame df;
	/**
	 * 怪物种类
	 */
	private int category;
	public int getCategory() {
		return category;
	}
	/**
	 * 怪物的生命
	 */
	private boolean live = true;
	/**
	 * 最大生命，每一等级加一
	 */
	private int maxHp;
	
	public int getMaxHp() {
		return maxHp;
	}
	/**
	 * 怪物的生命值
	 */
	private int hp;
	/**
	 * 怪物的等级
	 */
	private int rank;
	/**
	 * 记录怪物等级参数的数组，数值为发弹频率
	 */
	private double [] ranks = { 0.990, 0.985, 0.980, 0.970, 0.960, 0.950, 0.900};
	/**
	 * 初始话随机数
	 */
	private static Random r = new Random();
	private int step = r.nextInt(15) + 5;
	/**
	 * 怪物方向，初始为东
	 */
	private Doodle.Direction dir = Direction.E;
	/**
	 * 怪物的速度
	 */
    private static int X_SPEED = 3;
    private static int Y_SPEED = 3;
    /**
     * 怪物的欲动范围
     */
    private static int AREA_X = 550;
    private static int AREA_Y = 400;
    /**
     * 素材图片路径
     */
	private static final String IMAGEPATH="images/";
	/*
	 * 载入素材图片到内存
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
    static Image[] imgs = {
		tk.getImage(Mouster.class.getClassLoader().getResource(IMAGEPATH+"mouster.gif")),
		tk.getImage(Mouster.class.getClassLoader().getResource(IMAGEPATH+"mouster1.gif")),
		tk.getImage(Mouster.class.getClassLoader().getResource(IMAGEPATH+"mouster2.gif")),
		tk.getImage(Mouster.class.getClassLoader().getResource(IMAGEPATH+"mouster3.gif"))
    };      
    /**
     * 怪物的主要构建器
     * @param x 怪物x坐标
     * @param y 怪物y坐标
     * @param df 主框架的引用
     * @param category 怪物种类，共四种
     * @param rank 怪物等级
     * @param maxHp 最大生命
     */
    public Mouster(int x, int y, DoodleFrame df, int category, int rank, int maxHp) {
		this.x = x;
		this.y = y;
		this.df = df;
		this.category = category;
		this.rank = rank;
		this.maxHp = maxHp;
		this.hp = this.maxHp;
	}
    
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	/**
	 * 画出怪物
	 * @param g 画笔
	 */ 
	public void draw(Graphics g) {
    	if (!live) {
    		df.mousters.remove(this);
    		return;
    	}
		g.drawImage(imgs[category], x, y, null);
		BloodBar b = new BloodBar( this, x, y - 3);
		b.draw(g);
    	move();
    }
	/**
	 * 怪物发射子弹，很牛掰，8个方向
	 * @return 返回一颗子弹
	 */
	public Bullet superFire() {
		if (!live)
			return null;
		Direction[] dirs = Direction.values();
		Bullet b = null;
		for(int i = 0;i < 8; i++) {
			b = new Bullet(x + imgs[category].getWidth(null)/2, y+imgs[category].getHeight(null)/2 , dirs[i], false, this.df);
			df.bullets.add(b);
		}
		return b;
	}
	/**
	 * 怪物的移动，具体不解释
	 */
	public void move() {
		this.oldX = x;
		this.oldY = y;
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
		if (x < 25) x = 25;
		if (y < 60) y = 60;
		if (x > AREA_X - 25) x = AREA_X - 25;
		if (y > AREA_Y - 20) y = AREA_Y - 20;
		
		Direction[] dirs = Direction.values();
		if(step == 0) {
			step = r.nextInt(12) + 3;
			int rn = r.nextInt(dirs.length);
			dir = dirs[rn];
		}			
		step --;
			
		if(r.nextFloat() > ranks[rank]) this.superFire();
	}
	/**
	 * 获得怪物的碰撞体
	 * @return 返回一个Rectangle作为其碰撞体
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, imgs[category].getWidth(null), imgs[category].getHeight(null));
	}
	/**
	 * 让怪物停留在原来的位置，供怪物碰撞之后使用
	 */
	private void stay() {
		x = oldX;
		y = oldY;
	}
	/**
	 * 判断怪物与墙的碰撞
	 * @param w 墙
	 * @return 是否碰撞，是返回true，否饭后false
	 */
	public boolean collidesWithWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	/**
	 * 判断怪物与墙体的碰撞
	 * @param walls 墙体容器
	 * @return 是否碰撞，是返回true，否饭后false
	 */
	public boolean collidesWithWalls(java.util.List<Wall> walls) {
		for(int i=0; i<walls.size(); i++) {
			Wall w = walls.get(i);
			this.collidesWithWall(w);
		}
		return false;
	}
	/**
	 * 判断怪物与怪物之间的碰撞
	 * @param mousters 怪物容器
	 * @return 是否碰撞，是返回true，否饭后false
	 */
	public boolean collidesWithMousters(java.util.List<Mouster> mousters) {
		for(int i=0; i<mousters.size(); i++) {
			Mouster m = mousters.get(i);
			if(this != m) {
				if(this.live && m.isLive() && this.getRect().intersects(m.getRect())) {
					this.stay();
					m.stay();
					return true;
				}
			}
		}
		return false;
	}
}