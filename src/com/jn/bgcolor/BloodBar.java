package com.jn.bgcolor;
import java.awt.*;
/**
 * 这个类实现游戏中的血条
 * @author bgcolor
 *
 */
public class BloodBar {
	/**
	 * 对象的引用，Doodle用于显示doodle血条,mouster用于显示怪物血条
	 */
	Doodle d;
	Mouster m;
	/**
	 * 血条的位置
	 */
	int x;
	int y;
	/**
	 * 确定血条的种类
	 */
	private boolean category;
	/*
	 * 不同血条的颜色不同，doodle是绿，mouster是红
	 */
	Color doodle = Color.GREEN;
	Color mouster = Color.RED;
	/**
	 * 画出血条
	 * @param g 画笔
	 */
	public void draw(Graphics g) {
		if (category) {
			Color c = g.getColor();
			g.setColor(doodle);
			g.fillRect(x + 10, y, 40 * d.getHp()/3, 2);
			g.setColor(c);
		}
		else {
			Color c = g.getColor();
			g.setColor(mouster);
			g.fillRect(x, y, m.imgs[m.getCategory()].getWidth(null) * m.getHp()/m.getMaxHp(), 2);
			g.setColor(c);
		}
	}
	/**
	 * doodle血条的构建器
	 * @param d doodle的引用
	 * @param x doodle的x坐标
	 * @param y doodle的y坐标
	 */
	public BloodBar(Doodle d, int x, int y) {
		this.d = d;
		this.x = x;
		this.y = y;
		this.category = true;
	}
	/**
	 * mouster血条的构造函数
	 * @param m mouster的引用
	 * @param x mouster的x坐标
	 * @param y mouster的y坐标
	 */
	public BloodBar(Mouster m, int x, int y) {
		this.m = m;
		this.x = x;
		this.y = y;
		this.category = false;
	}
}
