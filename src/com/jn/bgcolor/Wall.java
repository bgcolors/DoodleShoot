package com.jn.bgcolor;
import java.awt.*;
/**
 * �����ʵ�����е�ǽ��
 * @author bgcolor
 *
 */
public class Wall {
	/**
	 * ǽ���λ��
	 */
	int x;
	int y;
	/**
	 * ����ܵ�����
	 */
	DoodleFrame df;
	/**
	 * ǽ�������
	 */
	private int category;
	/**
	 * �ز�ͼƬ·��
	 */
	private static final String IMAGEPATH="images/";
	/*
	 * �����ز�ͼƬ���ڴ�
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
    static Image[] imgs = {
    	tk.getImage(Wall.class.getClassLoader().getResource(IMAGEPATH+"wall.gif")),
		tk.getImage(Wall.class.getClassLoader().getResource(IMAGEPATH+"wall1.gif"))
    }; 
    /**
     * ����ǽ��
     * @param g ����
     */
    public void draw(Graphics g) {
    	g.drawImage(imgs[category], x, y, null);
    }
    /**
     * ���ǽ�����ײ��
     * @return ����һ��Rectangle��Ϊ����ײ��
     */
    public Rectangle getRect() {
		return new Rectangle(x, y, imgs[category].getWidth(null), imgs[category].getHeight(null));
	}
    /**
     * ǽ��Ĺ����������ڴ���ǽ��
     * @param x ǽ��x����
     * @param y ǽ��y����
     * @param df ����ܵ�����
     * @param category ǽ�����࣬������
     */
	public Wall(int x, int y, DoodleFrame df, int category) {
		this.x = x;
		this.y = y;
		this.df = df;
		this.category = category;
	}
    
    
}
