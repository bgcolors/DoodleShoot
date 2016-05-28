package com.jn.bgcolor;

import java.awt.*;
import java.util.Random;
import com.jn.bgcolor.Doodle.Direction;
/**
 * �����ʵ�����еĹ���
 * @author bgcolor
 *
 */
public class Mouster {
	/**
	 * �����λ��
	 */
	int x;
	int y;
	/**
	 * ����ľ�λ��
	 */
	int oldX;
	int oldY;
	/**
	 * ����ܰ�������
	 */
	DoodleFrame df;
	/**
	 * ��������
	 */
	private int category;
	public int getCategory() {
		return category;
	}
	/**
	 * ���������
	 */
	private boolean live = true;
	/**
	 * ���������ÿһ�ȼ���һ
	 */
	private int maxHp;
	
	public int getMaxHp() {
		return maxHp;
	}
	/**
	 * ���������ֵ
	 */
	private int hp;
	/**
	 * ����ĵȼ�
	 */
	private int rank;
	/**
	 * ��¼����ȼ����������飬��ֵΪ����Ƶ��
	 */
	private double [] ranks = { 0.990, 0.985, 0.980, 0.970, 0.960, 0.950, 0.900};
	/**
	 * ��ʼ�������
	 */
	private static Random r = new Random();
	private int step = r.nextInt(15) + 5;
	/**
	 * ���﷽�򣬳�ʼΪ��
	 */
	private Doodle.Direction dir = Direction.E;
	/**
	 * ������ٶ�
	 */
    private static int X_SPEED = 3;
    private static int Y_SPEED = 3;
    /**
     * �����������Χ
     */
    private static int AREA_X = 550;
    private static int AREA_Y = 400;
    /**
     * �ز�ͼƬ·��
     */
	private static final String IMAGEPATH="images/";
	/*
	 * �����ز�ͼƬ���ڴ�
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
    static Image[] imgs = {
		tk.getImage(Mouster.class.getClassLoader().getResource(IMAGEPATH+"mouster.gif")),
		tk.getImage(Mouster.class.getClassLoader().getResource(IMAGEPATH+"mouster1.gif")),
		tk.getImage(Mouster.class.getClassLoader().getResource(IMAGEPATH+"mouster2.gif")),
		tk.getImage(Mouster.class.getClassLoader().getResource(IMAGEPATH+"mouster3.gif"))
    };      
    /**
     * �������Ҫ������
     * @param x ����x����
     * @param y ����y����
     * @param df ����ܵ�����
     * @param category �������࣬������
     * @param rank ����ȼ�
     * @param maxHp �������
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
	 * ��������
	 * @param g ����
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
	 * ���﷢���ӵ�����ţ����8������
	 * @return ����һ���ӵ�
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
	 * ������ƶ������岻����
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
	 * ��ù������ײ��
	 * @return ����һ��Rectangle��Ϊ����ײ��
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, imgs[category].getWidth(null), imgs[category].getHeight(null));
	}
	/**
	 * �ù���ͣ����ԭ����λ�ã���������ײ֮��ʹ��
	 */
	private void stay() {
		x = oldX;
		y = oldY;
	}
	/**
	 * �жϹ�����ǽ����ײ
	 * @param w ǽ
	 * @return �Ƿ���ײ���Ƿ���true���񷹺�false
	 */
	public boolean collidesWithWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	/**
	 * �жϹ�����ǽ�����ײ
	 * @param walls ǽ������
	 * @return �Ƿ���ײ���Ƿ���true���񷹺�false
	 */
	public boolean collidesWithWalls(java.util.List<Wall> walls) {
		for(int i=0; i<walls.size(); i++) {
			Wall w = walls.get(i);
			this.collidesWithWall(w);
		}
		return false;
	}
	/**
	 * �жϹ��������֮�����ײ
	 * @param mousters ��������
	 * @return �Ƿ���ײ���Ƿ���true���񷹺�false
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