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
    JPanel panel_insert,panel_init;
    public  MyFrame(){
        super("族谱管理系统");
        setVisible(true);
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.container=getContentPane();
        this.container.setLayout(null);
        //按钮
        MyButton mybutton=new MyButton(this);
        //面板
        //初始面板
        this.panel_init=new JPanel();
        this.panel_init.setBackground(new Color(173, 216, 230));
        this.panel_init.setBounds(this.getBounds());
        this.panel_init.setLayout(null);
        this.panel_init.add(mybutton.b_insert);
        this.panel_init.add(mybutton.b_search);
        this.panel_init.add(mybutton.b_del);
        //插入面板
        this.panel_insert=new JPanel();
        this.panel_insert.setBackground(Color.pink);
        this.panel_insert.setBounds(this.getBounds());
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
        JLabel aliveJLabel=new JLabel("存活");
        JLabel deathJLabel=new JLabel("死亡日期：");
        
        panel_insert.add(fatherJLabel);
        panel_insert.add(fatherJTextArea);
        panel_insert.add(nameJLabel);
        panel_insert.add(nameJTextArea);
        panel_insert.add(birthJLabel);
        panel_insert.add(byearJTextArea);
        panel_insert.add(byearJLabel);
        panel_insert.add(bmonthJTextArea);
        panel_insert.add(bmonthJLabel);
        panel_insert.add(bdayJLabel);
        panel_insert.add(bdayJTextArea);
        
        this.container.add(panel_init);
        pack();

    }
}
//按钮类
class MyButton extends JButton{
    JButton b_insert,b_search,b_del;
    MyFrame frame=null;
    public MyButton(MyFrame frame){
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
    }
    public MyActionListener(MyFrame frame){
        this.frame=frame;
    }
}