package com.jn.bgcolor;

import java.awt.*;
import java.util.List;
import com.jn.bgcolor.Doodle.Direction;
/**
 * �����ʵ�ֵ����ӵ��Ĺ���
 * @author bgcolor
 *
 */
public class Bullet {
	/**
	 * �ӵ���λ������
	 */
	int x;
	int y;
	/**
	 * ������ܵ�����
	 */
	DoodleFrame df;
	/**
	 * �ӵ��Ƿ���doodle�����
	 */
	boolean ifDoodle;
	/**
	 * �ӵ�������
	 */
	private boolean live = true;
	/**
	 * doodle�ӵ�x�����ٶ�
	 */
    private static int X_SPEED = 15;
	/**
	 * doodle�ӵ�y�����ٶ�
	 */
    private static int Y_SPEED = 15;
	/**
	 * mouster�ӵ��ٶ�
	 */
    private static int MB_SPEED = 3;
    /**
     * ������
     */
    private static int AREA_X = 550;
    /**
     * ������
     */
    private static int AREA_Y = 400;
    /**
     * �ӵ�����ƫ����
     */
    private static int A = 30;
    /**
     * �ز�ͼƬ·��
     */
    private static final String IMAGEPATH="images/";
	/*
	 * �����ز�ͼƬ���ڴ�
	 */
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    static Image[] imgs = {
    	tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"dbullet.gif")),
    	tk.getImage(Doodle.class.getClassLoader().getResource(IMAGEPATH+"mbullet.gif"))
    };   
    /**
     * �ӵ�����
     */
    private Doodle.Direction dir;
    
    
    
	public Bullet(int x, int y, Direction dir, DoodleFrame df) {
		this.x = x;
		this.y = y;
		this.df = df;
		this.dir = dir;
	}
	/**
	 * �ӵ������������Դ����ӵ�
	 * @param x �ӵ�x����
	 * @param y �ӵ�y����
	 * @param dir �ӵ�����
	 * @param ifDoodle �Ƿ�doodle���
	 * @param df ����ܵ�����
	 */
	public Bullet(int x, int y, Direction dir, boolean ifDoodle, DoodleFrame df) {
		this.x = x;
		this.y = y;
		this.df = df;
		this.dir = dir;
		this.ifDoodle = ifDoodle;
	}
	/**
	 * �����ӵ�
	 * @param g ����
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
	 * �ӵ��ƶ��ĺ�����doodle���ӵ���mouster���ӵ��ƶ����ٶȺͷ����ǲ���ȫһ�µģ��ú���ȷ�����˶�
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
	 * ����ӵ�����ײ��
	 * @return ����һ��Rectangle��Ϊ�ӵ�����ײ��
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
	 * �ж��ӵ���������ײ����hitMousters()����
	 * @param m ����Ĺ���
	 * @return �����Ƿ�򵽹���Ƿ���true,�񷵻�false
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
	 * �ж��ӵ���doodle����ײ
	 * @param d doodle
	 * @return �����Ƿ��doodle���Ƿ���true,�񷵻�false
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
	 * �ж��ӵ������Ⱥ����ײ��ͨ������hitMouster()���б���
	 * @param mousters ��������
	 * @return �����Ƿ�򵽹���Ⱥ���Ƿ���true,�񷵻�false
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
	 * �ж��ӵ���ǽ����ײ����hitWalls()����
	 * @param w ǽ��
	 * @return �����Ƿ��ǽ���Ƿ���true,�񷵻�false
	 */
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	/**
	 * �ж��ӵ���ǽ�����ײ��ͨ������hitWalls()���б���
	 * @param walls ǽ������
	 * @return �����Ƿ��ǽ�壬�Ƿ���true,�񷵻�false
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
