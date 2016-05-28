package com.jn.bgcolor;
import java.awt.*;
import javax.swing.*;

/**
 * ���������������Ϸ�в˵��İ�ť����ť�ϵ�ͼ����i����	
 * @author bgcolor
 *
 */
public class DoodleMenu extends JPanel {

	/**
	 * �ز�ͼƬ·��
	 */
	private static final String IMAGEPATH="images/";	
	/*
	 * �����ز�ͼƬ���ڴ�
	 */
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    static Image[] imgs = {
    		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"play.gif")),
    		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"play1.gif")),
    		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"exit.gif")),
    		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"exit1.gif")),
    		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"frame.gif")),
    		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"replay.gif")),
    		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"replay1.gif")),
    		tk.getImage(DoodleMenu.class.getClassLoader().getResource(IMAGEPATH+"gameover.gif"))
    };   	
	/**
	 * ��¼��ť��ID
	 */
    private int i;
    public void setI(int i) {
		this.i = i;
	}
	/**
	 * ��ť�Ĺ�����
	 * @param i ��ťID
	 */
    public DoodleMenu(int i) {
		this.i = i;
	}
    /**
     * �����ػ水ť
     */
	protected void paintComponent(Graphics g) {		
			g.drawImage(imgs[i],0, 0,null);
	}
    
}
