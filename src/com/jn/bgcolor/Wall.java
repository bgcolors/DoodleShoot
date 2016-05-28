package com.jn.bgcolor;
import java.awt.*;
/**
 * 这个类实现所有的墙体
 * @author bgcolor
 *
 */
public class Wall {
	/**
	 * 墙体的位置
	 */
	int x;
	int y;
	/**
	 * 主框架的引用
	 */
	DoodleFrame df;
	/**
	 * 墙体的种类
	 */
	private int category;
	/**
	 * 素材图片路径
	 */
	private static final String IMAGEPATH="images/";
	/*
	 * 载入素材图片到内存
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
    static Image[] imgs = {
    	tk.getImage(Wall.class.getClassLoader().getResource(IMAGEPATH+"wall.gif")),
		tk.getImage(Wall.class.getClassLoader().getResource(IMAGEPATH+"wall1.gif"))
    }; 
    /**
     * 画出墙体
     * @param g 画笔
     */
    public void draw(Graphics g) {
    	g.drawImage(imgs[category], x, y, null);
    }
    /**
     * 获得墙体的碰撞体
     * @return 返回一个Rectangle作为其碰撞体
     */
    public Rectangle getRect() {
		return new Rectangle(x, y, imgs[category].getWidth(null), imgs[category].getHeight(null));
	}
    /**
     * 墙体的构建器，用于创建墙体
     * @param x 墙体x坐标
     * @param y 墙体y坐标
     * @param df 主框架的引用
     * @param category 墙体种类，共两种
     */
	public Wall(int x, int y, DoodleFrame df, int category) {
		this.x = x;
		this.y = y;
		this.df = df;
		this.category = category;
	}
    
    
}
