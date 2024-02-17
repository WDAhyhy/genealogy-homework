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
        long T=0;
        new MyFrame(T);
    }
}
class MyFrame extends JFrame{
    long T=0,TN=0;
    Container container;
    MyPanel_insert myPanel_insert;
    MyPanel_init myPanel_init;
    MyPanel_search myPanel_search;
    MyPanel_searchByName myPanel_searchByName;
    MyPanel_searchByBirth myPanel_searchByBirth;
    MyPanel_information myPanel_information;
    public  MyFrame(long T){
        super("族谱管理系统");
        setVisible(true);
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.container=getContentPane();
        this.container.setLayout(null);
        this.T=T;
        //面板
        //初始面板
        this.myPanel_init=new MyPanel_init(this);
        //插入面板
        this.myPanel_insert=new MyPanel_insert(this);
        //搜索面板
        this.myPanel_search=new MyPanel_search(this);
        //依据名字搜索
        this.myPanel_searchByName=new MyPanel_searchByName(this);
        //依据生日搜索
        this.myPanel_searchByBirth=new MyPanel_searchByBirth(this);
        //信息面板
        this.myPanel_information=new MyPanel_information(this,this.TN);
        this.container.add(this.myPanel_init.panel_init);
        pack();

    }
}
//面板类
class MyPanel_insert extends JPanel{
    JPanel panel_insert,panel_death;
    InsertButton insertButton;
    MyFrame frame=null;
    JTextArea fatherJTextArea,nameJTextArea,byearJTextArea,bmonthJTextArea,bdayJTextArea,
        addressJTextArea,dyearJTextArea,dmonthJTextArea,ddayJTextArea;
    ButtonGroup aliveButtonGroup;
    JRadioButton aliveYesJRadioButton,aliveNoJRadioButton;
    public MyPanel_insert(MyFrame frame){
        super();
        this.frame=frame;
        this.panel_insert=new JPanel();
        this.panel_insert.setBackground(Color.pink);
        this.panel_insert.setBounds(0,0,1920,1080);
        this.panel_insert.setLayout(null);


        JLabel fatherJLabel=new JLabel("父亲：");
        this.fatherJTextArea=new JTextArea(1,5);
        fatherJLabel.setBounds(50,50,100,100);
        fatherJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.fatherJTextArea.setBounds(150,85,100,30);
        this.fatherJTextArea.setFont(new Font("宋体",Font.PLAIN,25));


        JLabel nameJLabel=new JLabel("姓名：");
        this.nameJTextArea=new JTextArea(1,5);
        nameJLabel.setBounds(50,150,100,100);
        nameJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.nameJTextArea.setBounds(150,185,100,30);
        this.nameJTextArea.setFont(new Font("宋体",Font.PLAIN,25));

        JLabel birthJLabel= new JLabel("生日：");
        this.byearJTextArea=new JTextArea(1,5);
        birthJLabel.setBounds(50,250,100,100);
        birthJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.byearJTextArea.setBounds(150,285,100,30);
        this.byearJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel byearJLabel=new JLabel("年");
        byearJLabel.setBounds(275,253,100,100);
        byearJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.bmonthJTextArea=new JTextArea(1,5);
        this.bmonthJTextArea.setBounds(350,285,100,30);
        this.bmonthJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel bmonthJLabel=new JLabel("月");
        bmonthJLabel.setBounds(475,253,100,100);
        bmonthJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.bdayJTextArea=new JTextArea(1,5);
        this.bdayJTextArea.setBounds(550,285,100,30);
        this.bdayJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel bdayJLabel=new JLabel("日");
        bdayJLabel.setBounds(675,253,100,100);
        bdayJLabel.setFont(new Font("宋体",Font.BOLD,30));



        JLabel addressJLabel= new JLabel("地址：");
        this.addressJTextArea= new JTextArea(1,20);
        addressJLabel.setBounds(50,350,100,100);
        addressJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.addressJTextArea.setBounds(150,385,500,30);
        this.addressJTextArea.setFont(new Font("宋体",Font.PLAIN,25));

        JLabel aliveJLabel=new JLabel("存活：");

        aliveJLabel.setBounds(50,450,100,100);
        aliveJLabel.setFont(new Font("宋体",Font.BOLD,30));

        JLabel deathJLabel=new JLabel("死亡日期：");
        this.dyearJTextArea=new JTextArea(1,5);
        deathJLabel.setBounds(50,550,200,100);
        deathJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.dyearJTextArea.setBounds(200,585,100,30);
        this.dyearJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel dyearJLabel=new JLabel("年");
        dyearJLabel.setBounds(325,553,100,100);
        dyearJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.dmonthJTextArea=new JTextArea(1,5);
        this.dmonthJTextArea.setBounds(375,585,100,30);
        this.dmonthJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel dmonthJLabel=new JLabel("月");
        dmonthJLabel.setBounds(500,553,100,100);
        dmonthJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.ddayJTextArea=new JTextArea(1,5);
        this.ddayJTextArea.setBounds(550,585,100,30);
        this.ddayJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel ddayJLabel=new JLabel("日");
        ddayJLabel.setBounds(675,553,100,100);
        ddayJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.ddayJTextArea.setEditable(false);
        this.dmonthJTextArea.setEditable(false);
        this.dyearJTextArea.setEditable(false);
        //插入部分的按钮
        this.insertButton=new InsertButton(this.frame);
        this.panel_insert.add(this.insertButton.b_return);
        this.panel_insert.add(this.insertButton.b_submit);
        this.panel_insert.add(this.insertButton.aliveYesJRadioButton);
        this.panel_insert.add(this.insertButton.aliveNoJRadioButton);
        this.aliveButtonGroup=new ButtonGroup();
        this.aliveButtonGroup.add(this.insertButton.aliveYesJRadioButton);
        this.aliveButtonGroup.add(this.insertButton.aliveNoJRadioButton);
        this.aliveNoJRadioButton=this.insertButton.aliveNoJRadioButton;
        this.aliveYesJRadioButton=this.insertButton.aliveYesJRadioButton;

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
class MyPanel_search extends JPanel{
    JPanel panel_search;
    SearchButton searchButton;
    MyFrame frame=null;
    public MyPanel_search(MyFrame frame){
        this.frame=frame;
        this.panel_search=new JPanel();
        this.panel_search.setBackground(new Color(230,230,250));
        this.panel_search.setBounds(this.frame.getBounds());
        this.panel_search.setLayout(null);
        //按钮
        searchButton=new SearchButton(this.frame);
        this.panel_search.add(searchButton.b_SearchByName);
        this.panel_search.add(searchButton.b_SearchByBirth);
        this.panel_search.add(searchButton.b_return);
    }
}
class MyPanel_searchByName extends JPanel{
    JPanel panel_searchByName;
    MyFrame frame=null;
    JTextArea nameJTextArea;
    SearchByNameButton searchByNameButton;
    public MyPanel_searchByName(MyFrame frame){
        this.frame=frame;
        this.panel_searchByName=new JPanel();
        this.panel_searchByName.setBackground(new Color(102,0,255));
        this.panel_searchByName.setBounds(this.frame.getBounds());
        this.panel_searchByName.setLayout(null);
        JLabel nameJLabel=new JLabel("名字：");
        nameJLabel.setBounds(500,250,100,100);
        nameJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.panel_searchByName.add(nameJLabel);
        this.nameJTextArea=new JTextArea(1,5);
        this.nameJTextArea.setBounds(600,285,100,30);
        this.nameJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        this.panel_searchByName.add(nameJTextArea);


        //按钮
        this.searchByNameButton=new SearchByNameButton(this.frame);
        this.panel_searchByName.add(this.searchByNameButton.b_search);
        this.panel_searchByName.add(this.searchByNameButton.b_return);

    }
}
class MyPanel_searchByBirth extends JPanel{
    JPanel panel_searchByBirth;
    MyFrame frame=null;
    JTextArea byearJTextArea,bmonthJTextArea,bdayJTextArea;
    SearchByBirthButton searchByBirthButton;
    public MyPanel_searchByBirth(MyFrame frame){
        this.frame=frame;
        this.panel_searchByBirth=new JPanel();
        this.panel_searchByBirth.setBackground(new Color(102,0,255));
        this.panel_searchByBirth.setBounds(this.frame.getBounds());
        this.panel_searchByBirth.setLayout(null);
        JLabel birthJLabel= new JLabel("生日：");
        this.byearJTextArea=new JTextArea(1,5);
        birthJLabel.setBounds(350,250,100,100);
        birthJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.byearJTextArea.setBounds(450,285,100,30);
        this.byearJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel byearJLabel=new JLabel("年");
        byearJLabel.setBounds(575,253,100,100);
        byearJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.bmonthJTextArea=new JTextArea(1,5);
        this.bmonthJTextArea.setBounds(650,285,100,30);
        this.bmonthJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel bmonthJLabel=new JLabel("月");
        bmonthJLabel.setBounds(775,253,100,100);
        bmonthJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.bdayJTextArea=new JTextArea(1,5);
        this.bdayJTextArea.setBounds(850,285,100,30);
        this.bdayJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel bdayJLabel=new JLabel("日");
        bdayJLabel.setBounds(975,253,100,100);
        bdayJLabel.setFont(new Font("宋体",Font.BOLD,30));

        this.panel_searchByBirth.add(this.byearJTextArea);
        this.panel_searchByBirth.add(this.bmonthJTextArea);
        this.panel_searchByBirth.add(this.bdayJTextArea);
        this.panel_searchByBirth.add(byearJLabel);
        this.panel_searchByBirth.add(bmonthJLabel);
        this.panel_searchByBirth.add(bdayJLabel);
        this.panel_searchByBirth.add(birthJLabel);

        //按钮
        this.searchByBirthButton=new SearchByBirthButton(this.frame);
        this.panel_searchByBirth.add(this.searchByBirthButton.b_search);
        this.panel_searchByBirth.add(this.searchByBirthButton.b_return);

    }
}
class MyPanel_information extends MyPanel_insert{
    MyFrame frame=null;
    JPanel panel_information;
    long TN;
    public MyPanel_information(MyFrame frame,long TN){
        super(frame);
        this.TN=TN;
        this.frame=frame;
        this.panel_insert.remove(this.insertButton.b_submit);
        this.panel_information=this.panel_insert;
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
    JRadioButton aliveYesJRadioButton,aliveNoJRadioButton;
    ButtonGroup aliveButtonGroup;
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

        this.aliveYesJRadioButton=new JRadioButton("是");
        this.aliveNoJRadioButton=new JRadioButton("否");
        this.aliveYesJRadioButton.setBounds(150,485,40,30);
        this.aliveNoJRadioButton.setBounds(200,485,40,30);
        //绑定事件
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_return.addActionListener(myActionListener);
        this.b_submit.addActionListener(myActionListener);
        this.aliveNoJRadioButton.addActionListener(myActionListener);
        this.aliveYesJRadioButton.addActionListener(myActionListener);
    }
}
class SearchButton extends JButton{
    JButton b_SearchByName,b_SearchByBirth,b_return;
    MyFrame frame=null;
    public SearchButton(MyFrame frame){
        this.frame=frame;

        this.b_SearchByName=new JButton("按名字搜索");
        this.b_SearchByName.setBounds(629,100,300,100);
        this.b_SearchByName.setFont(new Font("宋体",Font.BOLD,30));
        this.b_SearchByName.setForeground(Color.red);
        
        this.b_SearchByBirth=new JButton("按生日搜索");
        this.b_SearchByBirth.setBounds(629,300,300,100);
        this.b_SearchByBirth.setFont(new Font("宋体",Font.BOLD,30));
        this.b_SearchByBirth.setForeground(Color.red);

        this.b_return=new JButton("返回");
        this.b_return.setBounds(629,500,300,100);
        this.b_return.setFont(new Font("宋体",Font.BOLD,30));
        this.b_return.setForeground(Color.gray);
        //绑定事件
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_return.addActionListener(myActionListener);
        this.b_SearchByBirth.addActionListener(myActionListener);
        this.b_SearchByName.addActionListener(myActionListener);
    }
}
class SearchByNameButton extends JButton{
    JButton b_search,b_return;
    MyFrame frame=null;
    public SearchByNameButton(MyFrame frame){
        this.frame=frame;
        this.b_search=new JButton("搜索");
        this.b_search.setActionCommand("依据名字搜索");
        this.b_search.setBounds(1200,700,100,50);
        this.b_search.setFont(new Font("宋体",Font.BOLD,30));
        this.b_search.setForeground(Color.red);
        this.b_return=new JButton("返回上一级");
        this.b_return.setBounds(1320,700,200,50);
        this.b_return.setFont(new Font("宋体",Font.BOLD,30));
        this.b_return.setForeground(Color.gray);
        //绑定事件
        MyActionListener myactionListener=new MyActionListener(this.frame);
        this.b_search.addActionListener(myactionListener);
        this.b_return.addActionListener(myactionListener);
    }
}
class SearchByBirthButton extends JButton{
    JButton b_search,b_return;
    MyFrame frame=null;
    public SearchByBirthButton(MyFrame frame){
        this.frame=frame;
        this.b_search=new JButton("搜索");
        this.b_search.setActionCommand("依据生日搜索");
        this.b_search.setBounds(1200,700,100,50);
        this.b_search.setFont(new Font("宋体",Font.BOLD,30));
        this.b_search.setForeground(Color.red);
        this.b_return=new JButton("返回上一级");
        this.b_return.setBounds(1320,700,200,50);
        this.b_return.setFont(new Font("宋体",Font.BOLD,30));
        this.b_return.setForeground(Color.gray);
        //绑定事件
        MyActionListener myactionListener=new MyActionListener(this.frame);
        this.b_search.addActionListener(myactionListener);
        this.b_return.addActionListener(myactionListener);
    }
}

//事件类
class MyActionListener implements ActionListener{
    MyFrame frame=null;
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand()=="插入"){
            this.frame.container.remove(this.frame.myPanel_init.panel_init);
            this.frame.container.add(this.frame.myPanel_insert.panel_insert);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="查找"){
            this.frame.container.remove(this.frame.myPanel_init.panel_init);
            this.frame.container.add(this.frame.myPanel_search.panel_search);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="删除"){
            System.out.println("789");
        }
        else if(e.getActionCommand()=="提交"){
            boolean alive;
            int dday=-1,dmonth=-1,dyear=-1;
            JavaGUI ctj=new JavaGUI();

            String father=this.frame.myPanel_insert.fatherJTextArea.getText();
            String name=this.frame.myPanel_insert.nameJTextArea.getText();
            int byear=Integer.parseInt(this.frame.myPanel_insert.byearJTextArea.getText());
            int bmonth=Integer.parseInt(this.frame.myPanel_insert.bmonthJTextArea.getText());
            int bday=Integer.parseInt(this.frame.myPanel_insert.bdayJTextArea.getText());
            String address=this.frame.myPanel_insert.addressJTextArea.getText();
            
            if(this.frame.myPanel_insert.aliveButtonGroup.getSelection()==this.frame.myPanel_insert.aliveYesJRadioButton.getModel()){
                alive=true;
            }
            else{
                alive=false;
                dyear=Integer.parseInt(this.frame.myPanel_insert.dyearJTextArea.getText());
                dmonth=Integer.parseInt(this.frame.myPanel_insert.dmonthJTextArea.getText());
                dday=Integer.parseInt(this.frame.myPanel_insert.ddayJTextArea.getText());
            }
            
            this.frame.T=ctj.insert(this.frame.T, father, name, byear, bmonth, bday, alive, address, alive, dyear, dmonth, dday);
            this.frame.container.remove(this.frame.myPanel_insert.panel_insert);
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="返回"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="否"){
            if(this.frame.myPanel_insert.aliveNoJRadioButton.isSelected()){
                this.frame.myPanel_insert.ddayJTextArea.setEditable(true);
                this.frame.myPanel_insert.dmonthJTextArea.setEditable(true);
                this.frame.myPanel_insert.dyearJTextArea.setEditable(true);
            }
        }
        else if(e.getActionCommand()=="是"){
            if(this.frame.myPanel_insert.aliveYesJRadioButton.isSelected()){
                this.frame.myPanel_insert.ddayJTextArea.setText("");
                this.frame.myPanel_insert.dmonthJTextArea.setText("");
                this.frame.myPanel_insert.dyearJTextArea.setText("");
                this.frame.myPanel_insert.ddayJTextArea.setEditable(false);
                this.frame.myPanel_insert.dmonthJTextArea.setEditable(false);
                this.frame.myPanel_insert.dyearJTextArea.setEditable(false);
            }
        }
        else if(e.getActionCommand()=="按名字搜索"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_searchByName.panel_searchByName);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if (e.getActionCommand()=="按生日搜索"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_searchByBirth.panel_searchByBirth);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="依据名字搜索"){
            
            JavaGUI.TNode T1;
            String name=this.frame.myPanel_searchByName.nameJTextArea.getText();
            JavaGUI ctj=new JavaGUI();
            this.frame.TN=ctj.searchByName(this.frame.T, name);
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_information.panel_information);
            T1=ctj.convertToTree(this.frame.TN);
            if(T1.parent==0){
                this.frame.myPanel_information.fatherJTextArea.setText("");
            }
            else{
                this.frame.myPanel_information.fatherJTextArea.setText(ctj.convertToTree(T1.parent).name);
            }
            this.frame.myPanel_information.nameJTextArea.setText(T1.name);
            this.frame.myPanel_information.addressJTextArea.setText(T1.address);
            this.frame.myPanel_information.aliveYesJRadioButton.setSelected(T1.alive);
            this.frame.myPanel_information.aliveNoJRadioButton.setSelected(!T1.alive);
            this.frame.myPanel_information.byearJTextArea.setText(Integer.toString(T1.birth.year));
            this.frame.myPanel_information.bmonthJTextArea.setText(Integer.toString(T1.birth.month));
            this.frame.myPanel_information.bdayJTextArea.setText(Integer.toString(T1.birth.day));
            if(T1.death.year==-1){
                this.frame.myPanel_information.dyearJTextArea.setText("");
            this.frame.myPanel_information.dmonthJTextArea.setText("");
            this.frame.myPanel_information.ddayJTextArea.setText("");
            }
            else{
                this.frame.myPanel_information.dyearJTextArea.setText(Integer.toString(T1.death.year));
                this.frame.myPanel_information.dmonthJTextArea.setText(Integer.toString(T1.death.month));
                this.frame.myPanel_information.ddayJTextArea.setText(Integer.toString(T1.death.day));
            }
            
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="依据生日搜索"){
            int byear=Integer.parseInt(this.frame.myPanel_searchByBirth.byearJTextArea.getText());
            int bmonth=Integer.parseInt(this.frame.myPanel_searchByBirth.bmonthJTextArea.getText());
            int bday=Integer.parseInt(this.frame.myPanel_searchByBirth.bdayJTextArea.getText());
            JavaGUI ctj=new JavaGUI();
            this.frame.TN=ctj.searchByBirth(this.frame.T, byear, bmonth, bday);
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="返回上一级"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_search.panel_search);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
    }
    public MyActionListener(MyFrame frame){
        this.frame=frame;
    }
}