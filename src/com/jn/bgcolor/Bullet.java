package com.jn.bgcolor;

import java.awt.*;
import java.util.List;
import com.jn.bgcolor.Doodle.Direction;
/**
 * 这个类实现的是子弹的功能
 * @author bgcolor
 *
 */
public class Bullet {
	/**
	 * 子弹的位置坐标
	 */
	int x;
	int y;
	/**
	 * 对主框架的引用
	 */
	DoodleFrame df;
	/**
	 * 子弹是否是doodle打出的
	 */
	boolean ifDoodle;
	/**
	 * 子弹的生命
	 */
	private boolean live = true;
	/**
	 * doodle子弹x方向速度
	 */
    private static int X_SPEED = 15;
	/**
	 * doodle子弹y方向速度
	 */
    private static int Y_SPEED = 15;
	/**
	 * mouster子弹速度
	 */
    private static int MB_SPEED = 3;
    /**
     * 活动区域宽
     */
    private static int AREA_X = 550;
    /**
     * 活动区域高
     */
    private static int AREA_Y = 400;
    /**
     * 子弹发射偏移量
     */
    private static int A = 30;
    /**
     * 素材图片路径
     */
    private static final String IMAGEPATH="images/";
	/*
	 * 载入素材图片的内存
	 */
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    static Image[] imgs = {
    	tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"dbullet.gif")),
    	tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"mbullet.gif"))
    };   
    /**
     * 子弹方向
     */
    private Doodle.Direction dir;
    
    
    
	public Bullet(int x, int y, Direction dir, DoodleFrame df) {
		this.x = x;
		this.y = y;
		this.df = df;
		this.dir = dir;
	}
	/**
	 * 子弹构建器，可以创建子弹
	 * @param x 子弹x坐标
	 * @param y 子弹y坐标
	 * @param dir 子弹方向
	 * @param ifDoodle 是否doodle打出
	 * @param df 主框架的引用
	 */
	public Bullet(int x, int y, Direction dir, boolean ifDoodle, DoodleFrame df) {
		this.x = x;
		this.y = y;
		this.df = df;
		this.dir = dir;
		this.ifDoodle = ifDoodle;
	}
	/**
	 * 画出子弹
	 * @param g 画笔
	 */
	public void draw(Graphics g) {

		if (!live) {
			df.bullets.remove(this);
			return ;
		}
		if (!ifDoodle){
			g.drawImage( imgs[1], x - imgs[1].getWidth(null)/2, y - imgs[1].getWidth(null)/2, null);
		}
		else {
			g.drawImage( imgs[0], x - imgs[0].getWidth(null)/2, y - imgs[0].getWidth(null)/2, null);			
		}

		move();
	}
	/**
	 * 子弹移动的函数，doodle的子弹和mouster的子弹移动的速度和方向是不完全一致的，该函数确定其运动
	 */
	public void move() {
		if (!ifDoodle) {
			switch(dir) {
			case N:
				y-=MB_SPEED;
				break;
			case NE:
				y-=MB_SPEED / 1.414;
				x+=MB_SPEED / 1.414;
				break;
			case E:
				x+=MB_SPEED;
				break;
			case SE:
				y+=MB_SPEED / 1.414;
				x+=MB_SPEED / 1.414;
				break;
			case S:
				y+=MB_SPEED;
				break;
			case SW:
				y+=MB_SPEED / 1.414;
				x-=MB_SPEED / 1.414;
				break;
			case W:
				x-=MB_SPEED;
				break;
			case NW:
				y-=MB_SPEED / 1.414;
				x-=MB_SPEED / 1.414;
				break;
			}
			if(x<0 || x > AREA_X || y<0 || y > AREA_Y) {
				df.bullets.remove(this);
			}	
		}
		else {
			switch(dir) {
			case N:
				y-=Y_SPEED;
				break;
			case NE:
				y-=Y_SPEED / 1.414;
				x+=X_SPEED / 1.414;
				break;
			case E:
				x+=X_SPEED;
				break;
			case SE:
				y+=Y_SPEED / 1.414;
				x+=X_SPEED / 1.414;
				break;
			case S:
				y+=Y_SPEED;
				break;
			case SW:
				y+=Y_SPEED / 1.414;
				x-=X_SPEED / 1.414;
				break;
			case W:
				x-=X_SPEED;
				break;
			case NW:
				y-=Y_SPEED / 1.414;
				x-=X_SPEED / 1.414;
				break;
			}
			if(x<0 || x > AREA_X || y<0 || y > AREA_Y) {
				df.bullets.remove(this);
			}	
		}	
	}
	/**
	 * 获得子弹的碰撞体
	 * @return 返回一个Rectangle作为子弹的碰撞体
	 */
	public Rectangle getRect() {
		if (!ifDoodle){
			return new Rectangle(x- imgs[1].getWidth(null)/2, y- imgs[1].getWidth(null)/2, imgs[1].getWidth(null), imgs[1].getHeight(null));
		}
		else {
			return new Rectangle(x - imgs[0].getWidth(null)/2, y - imgs[0].getHeight(null)/2, imgs[0].getWidth(null), imgs[0].getHeight(null));		
		}
	}
	/**
	 * 判断子弹与怪物的碰撞，供hitMousters()调用
	 * @param m 被打的怪物
	 * @return 返回是否打到怪物，是返回true,否返回false
	 */
	public boolean hitMouster(Mouster m) {
		if(this.live && this.getRect().intersects(m.getRect()) && m.isLive() && this.ifDoodle) {
			m.setHp(m.getHp()-1);
			if(m.getHp() <= 0) {
				m.setLive(false);
				df.setScore(df.getScore()+100);		
			}
			this.live = false;
			return true;
		}
		return false;
	}
	/**
	 * 判断子弹与doodle的碰撞
	 * @param d doodle
	 * @return 返回是否打到doodle，是返回true,否返回false
	 */
	public boolean hitDoodle(Doodle d) {
		if(this.live && this.getRect().intersects(d.getRect()) && d.isLive() && !this.ifDoodle) {
			d.setHp(d.getHp()-1);
			if(d.getHp() <= 0) {
				d.setLive(false);
				df.setIfDead(true);
			}
			this.live = false;
			return true;
		}
		return false;
	}
	/**
	 * 判断子弹与怪物群的碰撞，通过调用hitMouster()进行遍历
	 * @param mousters 怪物容器
	 * @return 返回是否打到怪物群，是返回true,否返回false
	 */
	public boolean hitMousters(List<Mouster> mousters) {
		for(int i=0; i<mousters.size(); i++) {
			if(hitMouster(mousters.get(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断子弹与墙的碰撞，供hitWalls()调用
	 * @param w 墙体
	 * @return 返回是否打到墙，是返回true,否返回false
	 */
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	/**
	 * 判断子弹与墙体的碰撞，通过调用hitWalls()进行遍历
	 * @param walls 墙体容器
	 * @return 返回是否打到墙体，是返回true,否返回false
	 */
	public boolean hitWalls(List<Wall> walls) {
		for(int i=0; i<walls.size(); i++) {
			if(hitWall(walls.get(i))) {
				return true;
			}
		}
		return false;
	}
}
