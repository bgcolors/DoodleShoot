package com.jn.bgcolor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
/**
 * �����ʵ�ֵ�����Ϸ����doodle
 * @author bgcolor
 *
 */
public class Doodle {
	/**
	 * doodle��λ������
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
	 * doodle�ɵ�λ�ú����꣬����ײ���ƶ�ʹ��
	 */
	int oldX;
	int oldY;
	/**
	 * ����ײ�жϵ�һ��������ÿˢ��һ�Σ������ۼӣ����ڼ�¼doodle�ɵ�λ��
	 */
	int i = 0;
	/**
	 * ����ܵ�����
	 */
	DoodleFrame df;
	/**
	 * ���������
	 */
	List<Mouster> mousters;
	/**
	 * ǽ�������
	 */
	List<Wall> walls;
	/**
	 * doodle����ʣ��ʹ�ô���
	 */
	private int superFire = 2;
	public void setSuperFire(int superFire) {
		this.superFire = superFire;
	}
	public int getSuperFire() {
		return superFire;
	}
	/**
	 * doodle����������ٶ�
	 */
    private static int X_SPEED = 5;
    private static int Y_SPEED = 5;
    /**
     * �ж�doodle�����ϱ��ĸ�����ı���
     */
    private boolean north,east,south,west = false;
    /**
     * �������
     */
    private static int AREA_X = 550;
    private static int AREA_Y = 400;
    /**
     * Ϊdraw()����׼����һ��ƫ����
     */
    private static int A = 30;
    /**
     * doodle������ֵ
     */
    private int hp = 3;
    /**
     * doodle������
     */
    private boolean live = true;
    /**
     * �ز�ͼƬ��·��
     */
	private static final String IMAGEPATH="images/";
	/*
	 * �����ز�ͼƬ���ڴ�
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
     * �������﷽���ö�٣�һ��Ϊ�����������������ϣ��ϣ����ϣ���������
     * @author bgcolor
     *
     */
    enum Direction {N,NE,E,SE,S,SW,W,NW,stop};
    /**
     * doodle�ķ���
     */
    private Direction dir = Direction.stop;
    /**
     * doodle�����ӵ��ķ���
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
	 * doodle�Ĺ����������ڴ�������doodle
	 * @param x doodle��x����
	 * @param y doodle��y����
	 * @param df ����ܵ�����
	 * @param mousters ��������������
	 * @param walls ǽ������������
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
	 * doodle���ƶ�������ÿˢ��һ����Ļ����һ��
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
	 * ����doodle
	 * @param g ����
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
	 * �Լ�����Ϣ������Ӧ���������ҿ��Ʒ���f2���»������������Ϸ�Ƚ��ѣ������ϲ�����һ��ͨ�أ�����û�������¿�ʼ����f12���أ�����ֱ��������Ϸȫ�أ����Ҵ���λ�Ŀ���
	 * @param e ����¼�
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
	 * �Լ�����Ϣ������Ӧ����keyReleased()һͬȷ���˸���λ
	 * 	 * @param e ����¼�
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
	 * �ж����յ��ƶ�����
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
	 * doodle����ӵ��ķ���
	 * @return ����һ���ӵ�
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
	 * doodle����,��ʧ��ǰ���й���1/2��Ѫ�������������Ļ
	 * @return ����һ���ӵ�
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
	 * ���doodle����ײ��
	 * @return ����һ��Rectangle��Ϊ����ײ��
	 */
	public Rectangle getRect() {
			return new Rectangle(x + 15, y + 15, A, A); 
	}
	/**
	 * �����ж���ǽ�����ײ
	 * @param walls ǽ������������
	 * @return �����Ƿ���ǽ����ײ���Ƿ���true���񷵻�false
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
	 * �����ж���������ײ
	 * @param mousters ��������������
	 * @return �����Ƿ��������ײ���Ƿ���true���񷵻�false
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
