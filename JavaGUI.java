import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
//需要实现插入，删除，查找，添加，判断
public class JavaGUI {
    //加载C库
    static{
        System.loadLibrary("C_functions");
    }
    public static class Date {
        int year;
        int month;
        int day; 
        public Date(int year,int month,int day){
            this.year=year;
            this.month=month;
            this.day=day;
        }
    }
    public static class TNode {
        int depth;
        int age_ratio;
        String name;
        Date birth;
        boolean marriage;
        String address;
        boolean alive;
        Date death;
        long parent;
        long child;
        long subbro;
        public TNode(int depth,int age_ratio,String name,Date birth,boolean marriage,String address,boolean alive,Date death,long parent,long child,long subbro){
            this.depth=depth;
            this.age_ratio=age_ratio;
            this.name=name;
            this.birth=birth;
            this.marriage=marriage;
            this.address=address;
            this.alive=alive;
            this.death=death;
            this.parent=parent;
            this.child=child;
            this.subbro=subbro;

        }
    }
    
    //Native方法声明
    public native long searchByName(long T,String name);
    public native long searchByBirth(long T,int year,int month,int day);
    public native long insert(long T,String father,String name,int byear,int bmonth,int bday,boolean marriage,String address,boolean alive,int dyear,int dmonth,int dday);
    public native TNode convertToTree(long T);
    // public native long test(String str);
    public native long modify(long T,String name,int byear,int bmonth,int bday,boolean marriage,String address,boolean alive,int dyear,int dmonth,int dday);
    public native String sortByBirth(long T);
    public native long modifyDate(long date,int year,int month,int day);
    public native String remindBirth(long T,long date);
    public native Date convertToDate(long date);

    //主函数
    public static void main(String[] args) {
        new MyFrame();
    }
}
class MyFrame extends JFrame{
    Container container;
    JPanel panel_init,panel_insert;

    public  MyFrame(){
        super("族谱管理系统");
        setVisible(true);
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.container=getContentPane();
        this.container.setLayout(null);
        //面板
        //初始面板
        this.panel_init=new MyPanel_init(this).panel_init;
        //插入面板
        this.panel_insert=new MyPanel_insert(this).panel_insert;
        this.container.add(this.panel_init);
        pack();

    }
}
//面板类
class MyPanel_insert extends JPanel{
    JPanel panel_insert;
    InsertButton insertButton;
    MyFrame frame=null;
    public MyPanel_insert(MyFrame frame){
        this.frame=frame;
        this.panel_insert=new JPanel();
        this.panel_insert.setBackground(Color.pink);
        this.panel_insert.setBounds(0,0,1920,1080);
        this.panel_insert.setLayout(null);


        JLabel fatherJLabel=new JLabel("父亲：");
        JTextArea fatherJTextArea=new JTextArea(1,5);
        fatherJLabel.setBounds(50,50,100,100);
        fatherJLabel.setFont(new Font("宋体",Font.BOLD,30));
        fatherJTextArea.setBounds(150,85,100,30);
        fatherJTextArea.setFont(new Font("宋体",Font.PLAIN,25));


        JLabel nameJLabel=new JLabel("姓名：");
        JTextArea nameJTextArea=new JTextArea(1,5);
        nameJLabel.setBounds(50,150,100,100);
        nameJLabel.setFont(new Font("宋体",Font.BOLD,30));
        nameJTextArea.setBounds(150,185,100,30);
        nameJTextArea.setFont(new Font("宋体",Font.PLAIN,25));

        JLabel birthJLabel= new JLabel("生日：");
        JTextArea byearJTextArea=new JTextArea(1,5);
        birthJLabel.setBounds(50,250,100,100);
        birthJLabel.setFont(new Font("宋体",Font.BOLD,30));
        byearJTextArea.setBounds(150,285,100,30);
        byearJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel byearJLabel=new JLabel("年");
        byearJLabel.setBounds(275,253,100,100);
        byearJLabel.setFont(new Font("宋体",Font.BOLD,30));
        JTextArea bmonthJTextArea=new JTextArea(1,5);
        bmonthJTextArea.setBounds(350,285,100,30);
        bmonthJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel bmonthJLabel=new JLabel("月");
        bmonthJLabel.setBounds(475,253,100,100);
        bmonthJLabel.setFont(new Font("宋体",Font.BOLD,30));
        JTextArea bdayJTextArea=new JTextArea(1,5);
        bdayJTextArea.setBounds(550,285,100,30);
        bdayJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel bdayJLabel=new JLabel("日");
        bdayJLabel.setBounds(675,253,100,100);
        bdayJLabel.setFont(new Font("宋体",Font.BOLD,30));



        JLabel addressJLabel= new JLabel("地址：");
        JTextArea addressJTextArea= new JTextArea(1,20);
        addressJLabel.setBounds(50,350,100,100);
        addressJLabel.setFont(new Font("宋体",Font.BOLD,30));
        addressJTextArea.setBounds(150,385,500,30);
        addressJTextArea.setFont(new Font("宋体",Font.PLAIN,25));

        JLabel aliveJLabel=new JLabel("存活：");
        JRadioButton aliveYesJRadioButton=new JRadioButton("是");
        JRadioButton aliveNoJRadioButton=new JRadioButton("否");
        ButtonGroup aliveButtonGroup=new ButtonGroup();
        aliveButtonGroup.add(aliveNoJRadioButton);
        aliveButtonGroup.add(aliveYesJRadioButton);
        aliveJLabel.setBounds(50,450,100,100);
        aliveJLabel.setFont(new Font("宋体",Font.BOLD,30));
        aliveYesJRadioButton.setBounds(150,485,40,30);
        aliveNoJRadioButton.setBounds(200,485,40,30);


        JLabel deathJLabel=new JLabel("死亡日期：");
        JTextArea dyearJTextArea=new JTextArea(1,5);
        deathJLabel.setBounds(50,550,200,100);
        deathJLabel.setFont(new Font("宋体",Font.BOLD,30));
        dyearJTextArea.setBounds(200,585,100,30);
        dyearJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel dyearJLabel=new JLabel("年");
        dyearJLabel.setBounds(325,553,100,100);
        dyearJLabel.setFont(new Font("宋体",Font.BOLD,30));
        JTextArea dmonthJTextArea=new JTextArea(1,5);
        dmonthJTextArea.setBounds(375,585,100,30);
        dmonthJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel dmonthJLabel=new JLabel("月");
        dmonthJLabel.setBounds(500,553,100,100);
        dmonthJLabel.setFont(new Font("宋体",Font.BOLD,30));
        JTextArea ddayJTextArea=new JTextArea(1,5);
        ddayJTextArea.setBounds(550,585,100,30);
        ddayJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel ddayJLabel=new JLabel("日");
        ddayJLabel.setBounds(675,553,100,100);
        ddayJLabel.setFont(new Font("宋体",Font.BOLD,30));
        //插入部分的按钮
        this.insertButton=new InsertButton(this.frame);
        this.panel_insert.add(this.insertButton.b_return);
        this.panel_insert.add(this.insertButton.b_submit);
        
        this.panel_insert.add(fatherJLabel);
        this.panel_insert.add(fatherJTextArea);
        this.panel_insert.add(nameJLabel);
        this.panel_insert.add(nameJTextArea);
        this.panel_insert.add(birthJLabel);
        this.panel_insert.add(byearJTextArea);
        this.panel_insert.add(byearJLabel);
        this.panel_insert.add(bmonthJTextArea);
        this.panel_insert.add(bmonthJLabel);
        this.panel_insert.add(bdayJLabel);
        this.panel_insert.add(bdayJTextArea);
        this.panel_insert.add(addressJLabel);
        this.panel_insert.add(addressJTextArea);
        this.panel_insert.add(aliveJLabel);
        this.panel_insert.add(aliveYesJRadioButton);
        this.panel_insert.add(aliveNoJRadioButton);
        this.panel_insert.add(deathJLabel);
        this.panel_insert.add(dyearJTextArea);
        this.panel_insert.add(dyearJLabel);
        this.panel_insert.add(dmonthJTextArea);
        this.panel_insert.add(dmonthJLabel);
        this.panel_insert.add(ddayJLabel);
        this.panel_insert.add(ddayJTextArea);
    }
}
class MyPanel_init extends JPanel{
    JPanel panel_init;
    InitButton initButton;
    MyFrame frame=null;
    public MyPanel_init(MyFrame frame){
        this.frame=frame;
        this.panel_init=new JPanel();
        this.panel_init.setBackground(new Color(173, 216, 230));
        this.panel_init.setBounds(this.frame.getBounds());
        this.panel_init.setLayout(null);
        this.initButton=new InitButton(this.frame);
        this.panel_init.add(this.initButton.b_insert);
        this.panel_init.add(this.initButton.b_search);
        this.panel_init.add(this.initButton.b_del);
    }
}
//按钮类
class InitButton extends JButton{
    JButton b_insert,b_search,b_del;
    MyFrame frame=null;
    public InitButton(MyFrame frame){
        this.frame=frame;
        this.b_insert=new JButton("插入");
        this.b_insert.setBounds(1350,50,150,50);
        this.b_insert.setFont(new Font("宋体",Font.BOLD,30));
        this.b_search=new JButton("查找");
        this.b_search.setBounds(1350,150,150,50);
        this.b_search.setFont(new Font("宋体",Font.BOLD,30));
        this.b_del=new JButton("删除");
        this.b_del.setBounds(1350,250,150,50);
        this.b_del.setFont(new Font("宋体",Font.BOLD,30));
        //绑定事件
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_insert.addActionListener(myActionListener);
        this.b_search.addActionListener(myActionListener);
        this.b_del.addActionListener(myActionListener);
}
}
class InsertButton extends JButton{
    JButton b_submit,b_return;
    MyFrame frame=null;
    public InsertButton(MyFrame frame){
        this.frame=frame;
        this.b_submit=new JButton("提交");
        this.b_submit.setBounds(1200,700,100,50);
        this.b_submit.setFont(new Font("宋体",Font.BOLD,30));
        this.b_submit.setForeground(Color.red);
        this.b_return=new JButton("返回");
        this.b_return.setBounds(1400,700,100,50);
        this.b_return.setFont(new Font("宋体",Font.BOLD,30));
        this.b_return.setForeground(Color.gray);

    }
}

//事件类
class MyActionListener implements ActionListener{
    MyFrame frame=null;
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand()=="插入"){
            this.frame.container.remove(this.frame.panel_init);
            this.frame.container.add(this.frame.panel_insert);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="查找"){
            System.out.println("456");
        }
        else if(e.getActionCommand()=="删除"){
            System.out.println("789");
        }
        else if(e.getActionCommand()=="提交"){
            
        }
    }
    public MyActionListener(MyFrame frame){
        this.frame=frame;
    }
}