package com.jn.bgcolor;
import java.awt.*;
/**
 * �����ʵ����Ϸ�е�Ѫ��
 * @author bgcolor
 *
 */
public class BloodBar {
	/**
	 * ��������ã�Doodle������ʾdoodleѪ��,mouster������ʾ����Ѫ��
	 */
	Doodle d;
	Mouster m;
	/**
	 * Ѫ����λ��
	 */
	int x;
	int y;
	/**
	 * ȷ��Ѫ��������
	 */
	private boolean category;
	/*
	 * ��ͬѪ������ɫ��ͬ��doodle���̣�mouster�Ǻ�
	 */
	Color doodle = Color.GREEN;
	Color mouster = Color.RED;
	/**
	 * ����Ѫ��
	 * @param g ����
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
	 * doodleѪ���Ĺ�����
	 * @param d doodle������
	 * @param x doodle��x����
	 * @param y doodle��y����
	 */
	public BloodBar(Doodle d, int x, int y) {
		this.d = d;
		this.x = x;
		this.y = y;
		this.category = true;
	}
	/**
	 * mousterѪ���Ĺ��캯��
	 * @param m mouster������
	 * @param x mouster��x����
	 * @param y mouster��y����
	 */
	public BloodBar(Mouster m, int x, int y) {
		this.m = m;
		this.x = x;
		this.y = y;
		this.category = false;
	}
}
