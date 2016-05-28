package com.jn.bgcolor;
import java.awt.*;
import javax.swing.*;

/**
 * 这个类用于制作游戏中菜单的按钮，按钮上的图标用i区分	
 * @author bgcolor
 *
 */
public class DoodleMenu extends JPanel {

	/**
	 * 素材图片路径
	 */
	private static final String IMAGEPATH="images/";	
	/*
	 * 载入素材图片到内存
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
	 * 记录按钮的ID
	 */
    private int i;
    public void setI(int i) {
		this.i = i;
	}
	/**
	 * 按钮的构建器
	 * @param i 按钮ID
	 */
    public DoodleMenu(int i) {
		this.i = i;
	}
    /**
     * 表面重绘按钮
     */
	protected void paintComponent(Graphics g) {		
			g.drawImage(imgs[i],0, 0,null);
	}
    
}
