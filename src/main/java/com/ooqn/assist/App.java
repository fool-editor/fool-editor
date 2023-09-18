package com.ooqn.assist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.google.common.io.Resources;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.system.JmeContext;

public class App {

    public static JFrame frame;
    
    public static void main(String[] args) {

        App app = new App();


        frame = new JFrame("Java第三个GUI程序"); //创建Frame窗口
        frame.setSize(900,500);
        frame.setLayout(new BorderLayout()); //为Frame窗口设置布局为BorderLayout

        frame.add(app.createWest(),BorderLayout.WEST);// 50
        frame.add(app.createCenter(),BorderLayout.CENTER);
        frame.add(app.createEast(),BorderLayout.EAST);

        // Create an ImageIcon for the icon
        ImageIcon imageIcon = new ImageIcon(Resources.getResource("image/icon.png"));	
        
        


        JMenuBar menuBar = new JMenuBar(); // 创建菜单栏
        menuBar.setBackground(Color.WHITE);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));


        frame.setJMenuBar(menuBar);

        JMenu iconMenu = new JMenu(); // 创建顶级菜单
        iconMenu.setIcon(imageIcon);


        iconMenu.setPreferredSize(new Dimension(40, 30));
        JMenu fileMenu = new JMenu("File"); // 创建顶级菜单
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(iconMenu);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);


        // 添加菜单项到File菜单
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator(); // 添加分隔线
        fileMenu.add(exitItem);

        frame.setVisible(true);
        frame.setUndecorated(true);
    }

    public ImageIcon createIcom(String path){
        ImageIcon imageIcon = new ImageIcon(Resources.getResource(path));	
        imageIcon.setImage( imageIcon.getImage().getScaledInstance(40, 40,  Image.SCALE_DEFAULT));
        
        return imageIcon;
    }

    public JPanel createWest(){
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(41, frame.getHeight()));
        panel1.setLayout(new BorderLayout());
        Border blackline = BorderFactory.createMatteBorder(0,0,0,1,new Color(16, 32, 56));
        panel1.setBorder(blackline);

        DefaultListModel<ImageIcon> model=new DefaultListModel<>();
        model.addElement(createIcom("image/catalogue.png"));
        model.addElement(createIcom("image/plugin.png"));
        model.addElement(createIcom("image/setting.png"));
        JList<ImageIcon> list = new JList<>(model);
        list.setBackground(new Color(25, 80, 123));
        panel1.add(list);

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = list.getSelectedIndex();
                if (selectedIndex != -1) {
                    // 设置选择后的背景颜色
                    list.setSelectionBackground(Color.BLUE);
                    // 设置选择后的文本颜色
                    list.setSelectionForeground(Color.RED);
                }
            }
        });





        // 创建根节点
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        
        // 创建一些子节点
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Node 1");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Node 2");
        
        // 将子节点添加到根节点
        root.add(node1);
        root.add(node2);
        
        // 创建树模型
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        
        // 创建JTree并设置树模型
        JTree tree = new JTree(treeModel);
       
        tree.setPreferredSize(new Dimension(200, frame.getHeight()));
        tree.setBorder(blackline);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(panel1, BorderLayout.WEST);
        panel.add(tree, BorderLayout.EAST);

        return panel;
    }

    static Boolean isOs = true;

    static JmeApp app = null;

    public JPanel createCenter(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        panel.setBackground(Color.WHITE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                app = new JmeApp();
                AppSettings settings = new AppSettings(true);
                app.setSettings(settings);
                app.start();
                app.createCanvas();
                isOs = false;
            }
        }).start();;

        while(isOs){

        }
        
   

        JmeCanvasContext context = (JmeCanvasContext)app.getContext();

        panel.add(context.getCanvas(), BorderLayout.CENTER);





        return panel;
    }

    public JPanel createEast(){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(50, frame.getHeight()));
        panel.setBackground(new Color(16, 32, 56));
        return panel;
    }
}
