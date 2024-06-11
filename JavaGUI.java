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
    public native String relation(long T,String name1,String name2);
    public native long delete(long T,String name);
    public native long createTime(int year,int month,int day);
    public native void freeTree(long T);
    public native void save(long T);
    public native long load();

    //主函数
    public static void main(String[] args) {
        JavaGUI ctj=new JavaGUI();
        long T=ctj.load();
        new MyFrame(T);
    }
}
class MyFrame extends JFrame{
    long T=0,TN=0,Date=0;
    Container container;
    MyPanel_insert myPanel_insert;
    MyPanel_init myPanel_init;
    MyPanel_search myPanel_search;
    MyPanel_searchByName myPanel_searchByName;
    MyPanel_searchByBirth myPanel_searchByBirth;
    MyPanel_information myPanel_information;
    MyPanel_delete myPanel_delete;
    MyPanel_relation myPanel_relation;
    MyPanel_creatTime myPanel_creatTime;
    MyPanel_modifyTime myPanel_modifyTime;
    MyPanel_sortByBirth myPanel_sortByBirth;
    MyPanel_remindBirth myPanel_remindBirth;
    MyPanel_error myPanel_error;
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
        //删除面板
        this.myPanel_delete=new MyPanel_delete(this);
        //关系面板
        this.myPanel_relation=new MyPanel_relation(this);
        //创建日期面板
        this.myPanel_creatTime=new MyPanel_creatTime(this);
        //修改日期面板
        this.myPanel_modifyTime=new MyPanel_modifyTime(this);
        //排序面板
        this.myPanel_sortByBirth=new MyPanel_sortByBirth(this);
        //提醒生日面板
        this.myPanel_remindBirth=new MyPanel_remindBirth(this);
        //错误面板
        this.myPanel_error=new MyPanel_error(this);
        this.container.add(this.myPanel_init.panel_init);
        pack();

    }
}
//面板类
class MyPanel_graph extends JPanel{
    MyFrame frame=null;
    Queue head=null;
    int depth=1;
    //行的空间大小
    int[] space=new int[100];
    //行的对象个数
    int[] arr=new int[100];
    //行目前已画对象个数
    int[] num=new int[100];
    public MyPanel_graph(MyFrame frame){
        this.frame=frame;
        this.setBackground(new Color(203,203,203));
        this.setBounds(0,0,1350,this.frame.getHeight());
        this.setLayout(null);
        //数组初始化
        for(int i=0;i<99;i++){
            this.space[i]=0;
            this.arr[i]=0;
        }
            
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        JavaGUI ctj=new JavaGUI();
        ctj.save(this.frame.T);
        drawTree(g,this.frame.T);
        //初始化
        for(int i=0;i<99;i++){
            this.space[i]=0;
            this.arr[i]=0;
            this.num[i]=0;
        }
    }
    public void drawTree(Graphics g,long T){
        if(T==0)
            return;
 
        JavaGUI ctj=new JavaGUI();
        int x1,x2;
        long CT;
        this.arr[1]++;
        x1=drawTRect(g, T);
        this.head=AddQ(head, T,x1);
        //遍历计算一层中结点个数
        Count(T);
        
        while (this.head!=null) {
            CT=ctj.convertToTree(this.head.TN).child;
            x1=this.head.x;
            //画图
            while(CT!=0){
                x2=drawTRect(g, CT);
                this.head=AddQ(head, CT,x2);
                this.num[this.depth]++;
                //20 20
                g.drawLine(x1+20, 30+(this.depth-2)*100, x2+20,10+(this.depth-1)*100);
                CT=ctj.convertToTree(CT).subbro;
            }
            this.head=DelQ(this.head);
        }
        
    }
    public int drawTRect(Graphics g,long T){
        JavaGUI ctj=new JavaGUI();
        JavaGUI.TNode TN=ctj.convertToTree(T);
        this.depth=TN.depth;
            

        this.space[TN.depth]=this.getWidth()/this.arr[TN.depth];

        g.setColor(Color.black);
        g.drawRect(((((this.num[TN.depth]+1)+this.num[TN.depth])*this.space[TN.depth])/2)-20,10+(TN.depth-1)*100,40,20);
        g.drawString(TN.name, ((((this.num[TN.depth]+1)+this.num[TN.depth])*this.space[TN.depth])/2)-17, 25+(TN.depth-1)*100);
        //返回x坐标
        return ((((this.num[TN.depth]+1)+this.num[TN.depth])*this.space[TN.depth])/2)-20;
        //3 25
    }
    private void Count(long T){
        if(T==0)
            return;
        JavaGUI ctj=new JavaGUI();
        this.arr[ctj.convertToTree(T).depth]++;
        Count(ctj.convertToTree(T).child);
        Count(ctj.convertToTree(T).subbro);
    }
    //下面是队列方法以及类
    private class Queue{
        long TN;
        int x;
        Queue next;
        public Queue(long TN,int x){
            this.TN=TN;
            this.x=x;
            this.next=null;
        }
    }
    private Queue AddQ(Queue head,long TN,int x){
        Queue pNode;
        Queue newNode=new Queue(TN,x);
        if(head==null){
            head=newNode;
        }
        else{
            pNode=head;
            while (pNode.next!=null) {
                pNode=pNode.next;
            }
            pNode.next=newNode;
        }
        return head;
    }
    private Queue DelQ(Queue head){
        head=head.next;
        return head;
    }
}
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
        this.ddayJTextArea.setEnabled(false);
        this.dmonthJTextArea.setEnabled(false);
        this.dyearJTextArea.setEnabled(false);
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
    MyPanel_graph myPanel_graph;
    JLabel time;
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
        this.panel_init.add(this.initButton.b_relation);
        this.panel_init.add(this.initButton.b_creatTime);
        this.panel_init.add(this.initButton.b_sortByBirth);
        this.panel_init.add(this.initButton.b_remind);
        this.time=new JLabel("");
        this.time.setBounds(1375,725,100,100);
        this.time.setFont(new Font("宋体",Font.BOLD,20));
        //图面板
        this.myPanel_graph=new MyPanel_graph(this.frame);
        this.panel_init.add(this.myPanel_graph);
        this.panel_init.add(this.time);
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
class MyPanel_relation extends JPanel{
    JLabel relationJLabel;
    MyFrame frame=null;
    JTextArea name_1JTextArea ,name_2JTextArea;
    JPanel panel_relation;
    RelationButton relationButton;
    public MyPanel_relation(MyFrame frame){
        this.frame=frame;
        this.relationJLabel=new JLabel("");
        this.relationJLabel.setBounds(200,350,800,100);
        this.relationJLabel.setFont(new Font("宋体",Font.BOLD,30));
        JLabel name_1_JLabel=new JLabel("名字一：");
        name_1_JLabel.setBounds(200,250,130,100);
        name_1_JLabel.setFont(new Font("宋体",Font.BOLD,25));
        this.name_1JTextArea=new JTextArea(1,5);
        this.name_1JTextArea.setBounds(300,285,100,30);
        this.name_1JTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel name_2_JLabel=new JLabel("名字二：");
        name_2_JLabel.setBounds(700,250,130,100);
        name_2_JLabel.setFont(new Font("宋体",Font.BOLD,25));
        this.name_2JTextArea=new JTextArea(1,5);
        this.name_2JTextArea.setBounds(800,285,100,30);
        this.name_2JTextArea.setFont(new Font("宋体",Font.PLAIN,25));

        this.panel_relation=new JPanel();
        this.panel_relation.setBounds(this.frame.getBounds());
        this.panel_relation.setBackground(Color.yellow);
        this.panel_relation.setLayout(null);
        this.panel_relation.add(this.relationJLabel);
        this.panel_relation.add(name_1_JLabel);
        this.panel_relation.add(name_2_JLabel);
        this.panel_relation.add(this.name_1JTextArea);
        this.panel_relation.add(this.name_2JTextArea);
        //按钮
        this.relationButton=new RelationButton(this.frame);
        this.panel_relation.add(this.relationButton.b_return);
        this.panel_relation.add(this.relationButton.b_search);
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
    InformationButton informationButton;
    public MyPanel_information(MyFrame frame,long TN){
        super(frame);
        this.TN=TN;
        this.frame=frame;
        
        this.panel_information=this.panel_insert;
        
        //按钮
        this.informationButton=new InformationButton(this.frame);
        this.aliveNoJRadioButton=this.informationButton.aliveNoJRadioButton;
        this.aliveYesJRadioButton=this.informationButton.aliveYesJRadioButton;

        
        this.panel_insert.remove(this.insertButton.b_submit);
        this.panel_insert.remove(this.insertButton.aliveNoJRadioButton);
        this.panel_insert.remove(this.insertButton.aliveYesJRadioButton);
        this.panel_insert.add(this.informationButton.aliveNoJRadioButton);
        this.panel_insert.add(this.informationButton.aliveYesJRadioButton);
        this.panel_information.add(this.informationButton.b_modify);
    }

}
class MyPanel_delete extends JPanel{
    DeleteButton deleteButton;
    JTextArea nameJTextArea;
    JPanel panel_delte;
    MyFrame frame=null;
    public MyPanel_delete(MyFrame frame){
        this.frame=frame;
        JLabel nameJLabel=new JLabel("名字：");
        nameJLabel.setBounds(500,250,100,100);
        nameJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.nameJTextArea=new JTextArea(1,5);
        this.nameJTextArea.setBounds(600,285,100,30);
        this.nameJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        this.panel_delte=new JPanel();
        this.panel_delte.setBounds(this.frame.getBounds());
        this.panel_delte.setLayout(null);
        this.panel_delte.setBackground(Color.green);
        this.panel_delte.add(nameJLabel);
        this.panel_delte.add(this.nameJTextArea);
        //按钮
        this.deleteButton=new DeleteButton(this.frame);
        this.panel_delte.add(this.deleteButton.b_cancel);
        this.panel_delte.add(this.deleteButton.b_del);
        
    }
}
class MyPanel_creatTime extends JPanel{
    JPanel panel_createTime;
    MyFrame frame=null;
    CreateTimeButton createTimeButton;
    JTextArea yearJTextArea,monthJTextArea,dayJTextArea;
    public MyPanel_creatTime(MyFrame frame){
        this.frame=frame;
        this.panel_createTime=new JPanel();
        this.panel_createTime.setBounds(this.frame.getBounds());
        this.panel_createTime.setLayout(null);
        this.panel_createTime.setBackground(Color.magenta);

        this.yearJTextArea=new JTextArea(1,5);
        this.yearJTextArea.setBounds(450,285,100,30);
        this.yearJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel yearJLabel=new JLabel("年");
        yearJLabel.setBounds(575,253,100,100);
        yearJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.monthJTextArea=new JTextArea(1,5);
        this.monthJTextArea.setBounds(650,285,100,30);
        this.monthJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel monthJLabel=new JLabel("月");
        monthJLabel.setBounds(775,253,100,100);
        monthJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.dayJTextArea=new JTextArea(1,5);
        this.dayJTextArea.setBounds(850,285,100,30);
        this.dayJTextArea.setFont(new Font("宋体",Font.PLAIN,25));
        JLabel dayJLabel=new JLabel("日");
        dayJLabel.setBounds(975,253,100,100);
        dayJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.panel_createTime.add(this.yearJTextArea);
        this.panel_createTime.add(yearJLabel);
        this.panel_createTime.add(this.monthJTextArea);
        this.panel_createTime.add(monthJLabel);
        this.panel_createTime.add(this.dayJTextArea);
        this.panel_createTime.add(dayJLabel);
        //按钮
        this.createTimeButton=new CreateTimeButton(this.frame);
        this.panel_createTime.add(this.createTimeButton.b_create);
        this.panel_createTime.add(this.createTimeButton.b_return);
    }
}
class MyPanel_modifyTime extends MyPanel_creatTime{
    ModifyTimeButton modifyTimeButton;
    public MyPanel_modifyTime(MyFrame frame){
        super(frame);
        this.modifyTimeButton=new ModifyTimeButton(this.frame);
        this.panel_createTime.remove(this.createTimeButton.b_create);
        this.panel_createTime.add(this.modifyTimeButton.b_modify);


    }
}
class MyPanel_sortByBirth extends JPanel{
    JPanel panel_sortByBirth;
    MyFrame frame=null;
    JLabel sortByBirthJLabel;
    SortByBirthButton sortByBirthButton;
    public MyPanel_sortByBirth(MyFrame frame){
        this.frame=frame;
        this.panel_sortByBirth=new JPanel();
        this.panel_sortByBirth.setBounds(this.frame.getBounds());
        this.panel_sortByBirth.setLayout(null);
        this.sortByBirthJLabel=new JLabel("");
        this.sortByBirthJLabel.setBounds(20,20,1500,300);
        this.sortByBirthJLabel.setFont(new Font("宋体",Font.BOLD,15));
        JLabel aJLabel=new JLabel("按生日排序，从大到小：");
        aJLabel.setBounds(0,0,800,100);
        aJLabel.setFont(new Font("宋体",Font.BOLD,30));
        aJLabel.setForeground(Color.red);
        this.panel_sortByBirth.add(aJLabel);
        this.panel_sortByBirth.add(this.sortByBirthJLabel);
        //按钮
        sortByBirthButton=new SortByBirthButton(this.frame);
        panel_sortByBirth.add(sortByBirthButton.b_return);
    }
}
class MyPanel_remindBirth extends JPanel{
    MyFrame frame=null;
    SortByBirthButton remindBirthButton;
    JLabel remindJLabel;
    JPanel panel_remindBirth;
    public MyPanel_remindBirth(MyFrame frame){
        this.frame=frame;
        this.panel_remindBirth=new JPanel();
        this.panel_remindBirth.setBounds(this.frame.getBounds());
        this.panel_remindBirth.setLayout(null);
        this.remindJLabel=new JLabel("");
        this.remindJLabel.setBounds(100,100,800,200);
        this.remindJLabel.setFont(new Font("宋体",Font.BOLD,30));
        this.panel_remindBirth.add(this.remindJLabel);
        //按钮
        this.remindBirthButton=new SortByBirthButton(this.frame);
        this.panel_remindBirth.add(this.remindBirthButton.b_return);
    }
}
class MyPanel_error extends JPanel{
    MyFrame frame=null;
    JPanel panel_error;
    SortByBirthButton errorButton;
    public MyPanel_error(MyFrame frame){
        this.frame=frame;
        this.panel_error=new JPanel();
        this.panel_error.setBounds(this.frame.getBounds());
        this.panel_error.setLayout(null);
        JLabel errorJLabel=new JLabel("出错啦！");
        errorJLabel.setBounds(650,300,800,100);
        errorJLabel.setFont(new Font("宋体",Font.BOLD,50));
        errorJLabel.setForeground(Color.red);
        this.panel_error.add(errorJLabel);
        //按钮
        errorButton=new SortByBirthButton(this.frame);
        this.panel_error.add(errorButton.b_return);
    }
}
//按钮类
class InitButton extends JButton{
    JButton b_insert,b_search,b_del,b_relation,b_creatTime,b_modifyTime,b_sortByBirth,b_remind;
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
        this.b_relation=new JButton("关系溯源");
        this.b_relation.setBounds(1350,350,150,50);
        this.b_relation.setFont(new Font("宋体",Font.BOLD,25));
        this.b_creatTime=new JButton("创建日期");
        this.b_creatTime.setBounds(1350,450,150,50);
        this.b_creatTime.setFont(new Font("宋体",Font.BOLD,25));
        this.b_modifyTime=new JButton("修改日期");
        this.b_modifyTime.setBounds(1350,450,150,50);
        this.b_modifyTime.setFont(new Font("宋体",Font.BOLD,25));
        this.b_sortByBirth=new JButton("排序");
        this.b_sortByBirth.setBounds(1350,550,150,50);
        this.b_sortByBirth.setFont(new Font("宋体",Font.BOLD,30));
        this.b_remind=new JButton("提醒生日");
        this.b_remind.setBounds(1350,650,150,50);
        this.b_remind.setFont(new Font("宋体",Font.BOLD,25));
        //绑定事件
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_insert.addActionListener(myActionListener);
        this.b_search.addActionListener(myActionListener);
        this.b_del.addActionListener(myActionListener);
        this.b_relation.addActionListener(myActionListener);
        this.b_creatTime.addActionListener(myActionListener);
        this.b_modifyTime.addActionListener(myActionListener);
        this.b_sortByBirth.addActionListener(myActionListener);
        this.b_remind.addActionListener(myActionListener);
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
        this.aliveNoJRadioButton.setActionCommand("插入否");
        this.aliveYesJRadioButton.setActionCommand("插入是");
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
class InformationButton extends InsertButton{
    MyFrame frame=null;
    JButton b_modify,b_confirm;
    ButtonGroup aliveButtonGroup;
    public InformationButton(MyFrame frame){
        super(frame);
        this.frame=frame;
        this.b_modify=new JButton("修改");
        this.b_modify.setBounds(1200,700,100,50);
        this.b_modify.setFont(new Font("宋体",Font.BOLD,30));
        this.b_confirm=new JButton("确认");
        this.b_confirm.setBounds(1200,700,100,50);
        this.b_confirm.setFont(new Font("宋体",Font.BOLD,30));
        this.aliveNoJRadioButton.setActionCommand("修改否");
        this.aliveYesJRadioButton.setActionCommand("修改是");
        this.aliveButtonGroup=new ButtonGroup();
        this.aliveButtonGroup.add(this.aliveNoJRadioButton);
        this.aliveButtonGroup.add(this.aliveYesJRadioButton);
        //绑定事件
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_confirm.addActionListener(myActionListener);
        this.b_modify.addActionListener(myActionListener);
        this.aliveYesJRadioButton.addActionListener(myActionListener);
        this.aliveNoJRadioButton.addActionListener(myActionListener);
    }
}
class DeleteButton extends JButton{
    JButton b_cancel,b_del;
    MyFrame frame=null;
    public DeleteButton(MyFrame frame){
        this.frame=frame;
        this.b_del=new JButton("删除");
        this.b_del.setBounds(1200,700,100,50);
        this.b_del.setFont(new Font("宋体",Font.BOLD,30));
        this.b_del.setForeground(Color.red);
        this.b_del.setActionCommand("删除中的删除");
        this.b_cancel=new JButton("取消");
        this.b_cancel.setBounds(1350,700,100,50);
        this.b_cancel.setFont(new Font("宋体",Font.BOLD,30));
        this.b_cancel.setForeground(Color.gray);
        //绑定事件
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_del.addActionListener(myActionListener);
        this.b_cancel.addActionListener(myActionListener);
    }

}
class RelationButton extends JButton{
    JButton b_search,b_return;
    MyFrame frame=null;
    public RelationButton(MyFrame frame){
        this.frame=frame;
        this.b_search=new JButton("查询");
        this.b_search.setBounds(1200,700,100,50);
        this.b_search.setFont(new Font("宋体",Font.BOLD,30));
        this.b_search.setForeground(Color.red);
        this.b_return=new JButton("返回");
        this.b_return.setActionCommand("查询返回");
        this.b_return.setBounds(1350,700,100,50);
        this.b_return.setFont(new Font("宋体",Font.BOLD,30));
        this.b_return.setForeground(Color.gray);
        //绑定事件
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_return.addActionListener(myActionListener);
        this.b_search.addActionListener(myActionListener);
    }
}
class CreateTimeButton extends JButton{
    JButton b_create,b_return;
    MyFrame frame=null;
    public CreateTimeButton(MyFrame frame){
        this.frame=frame;
        this.b_create=new JButton("创建");
        this.b_create.setBounds(1200,700,100,50);
        this.b_create.setFont(new Font("宋体",Font.BOLD,30));
        this.b_create.setForeground(Color.red);
        this.b_return=new JButton("返回");
        this.b_return.setBounds(1350,700,100,50);
        this.b_return.setFont(new Font("宋体",Font.BOLD,30));
        this.b_return.setForeground(Color.gray);
        this.b_return.setActionCommand("创建中的返回");
        //绑定事件
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_create.addActionListener(myActionListener);
        this.b_return.addActionListener(myActionListener);
    }
}
class ModifyTimeButton extends CreateTimeButton{
    JButton b_modify;
    public ModifyTimeButton(MyFrame frame){
        super(frame);
        this.b_modify=new JButton("修改");
        this.b_modify.setBounds(1200,700,100,50);
        this.b_modify.setFont(new Font("宋体",Font.BOLD,30));
        this.b_modify.setForeground(Color.red);
        this.b_modify.setActionCommand("确认修改时间");
        //绑定按键
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_modify.addActionListener(myActionListener);
    }
}
class SortByBirthButton extends JButton{
    JButton b_return;
    MyFrame frame=null;
    public SortByBirthButton(MyFrame frame){
        this.frame=frame;
        this.b_return=new JButton("返回");
        this.b_return.setBounds(1350,700,100,50);
        this.b_return.setFont(new Font("宋体",Font.BOLD,30));
        this.b_return.setForeground(Color.gray);
        //绑定事件
        MyActionListener myActionListener=new MyActionListener(this.frame);
        this.b_return.addActionListener(myActionListener);
    }
}
//事件类
class MyActionListener implements ActionListener{
    MyFrame frame=null;
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand()=="插入"){
            if(this.frame.T==0){
                this.frame.myPanel_insert.fatherJTextArea.setEnabled(false);
            }
            else{
                this.frame.myPanel_insert.fatherJTextArea.setEnabled(true);
            }
            this.frame.myPanel_insert.addressJTextArea.setText("");
            this.frame.myPanel_insert.bdayJTextArea.setText("");
            this.frame.myPanel_insert.bmonthJTextArea.setText("");
            this.frame.myPanel_insert.byearJTextArea.setText("");
            this.frame.myPanel_insert.ddayJTextArea.setText("");
            this.frame.myPanel_insert.dmonthJTextArea.setText("");
            this.frame.myPanel_insert.dyearJTextArea.setText("");
            this.frame.myPanel_insert.fatherJTextArea.setText("");
            this.frame.myPanel_insert.nameJTextArea.setText("");
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
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_delete.panel_delte);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="关系溯源"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_relation.panel_relation);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="创建日期"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_creatTime.panel_createTime);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="修改日期"){
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_modifyTime.panel_createTime);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="排序"){
            JavaGUI ctj=new JavaGUI();
            boolean error=false;
            try{
                this.frame.myPanel_sortByBirth.sortByBirthJLabel.setText(ctj.sortByBirth(this.frame.T));
                this.frame.myPanel_sortByBirth.sortByBirthJLabel.setBounds(20,20,this.frame.myPanel_sortByBirth.panel_sortByBirth.getWidth(),this.frame.myPanel_sortByBirth.panel_sortByBirth.getHeight());
                this.frame.myPanel_sortByBirth.sortByBirthJLabel.setFont(new Font("宋体",Font.BOLD,(this.frame.myPanel_sortByBirth.panel_sortByBirth.getWidth())/(this.frame.myPanel_sortByBirth.sortByBirthJLabel.getText().length())));
                this.frame.container.removeAll();
                this.frame.container.add(this.frame.myPanel_sortByBirth.panel_sortByBirth);
                this.frame.container.revalidate();
                this.frame.container.repaint();
            }catch(Exception ee){
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }
            }
            
        }
        else if(e.getActionCommand()=="提醒生日"){
            JavaGUI ctj=new JavaGUI();
            if(this.frame.Date==0){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
            }
            this.frame.myPanel_remindBirth.remindJLabel.setText(ctj.remindBirth(this.frame.T, this.frame.Date));
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_remindBirth.panel_remindBirth);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="提交"){
            boolean alive;
            boolean error=false;
            int bday=-1,byear=-1,bmonth=-1;
            int dday=-1,dmonth=-1,dyear=-1;
            JavaGUI ctj=new JavaGUI();
            String father=this.frame.myPanel_insert.fatherJTextArea.getText();
            String name=this.frame.myPanel_insert.nameJTextArea.getText();
            try {
                byear=Integer.parseInt(this.frame.myPanel_insert.byearJTextArea.getText());
                bmonth=Integer.parseInt(this.frame.myPanel_insert.bmonthJTextArea.getText());
                bday=Integer.parseInt(this.frame.myPanel_insert.bdayJTextArea.getText());
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error)
                {
                    this.frame.container.remove(this.frame.myPanel_insert.panel_insert);
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }

            }
            String address=this.frame.myPanel_insert.addressJTextArea.getText();
            
            if(this.frame.myPanel_insert.aliveButtonGroup.getSelection()==this.frame.myPanel_insert.aliveYesJRadioButton.getModel()){
                alive=true;
            }
            else{
                alive=false;
                try {
                    dyear=Integer.parseInt(this.frame.myPanel_insert.dyearJTextArea.getText());
                    dmonth=Integer.parseInt(this.frame.myPanel_insert.dmonthJTextArea.getText());
                    dday=Integer.parseInt(this.frame.myPanel_insert.ddayJTextArea.getText());
                } catch (Exception ee) {
                    error=true;
                }finally{
                    if(error)
                {
                    this.frame.container.remove(this.frame.myPanel_insert.panel_insert);
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }
                }
            }
            long TN=ctj.insert(this.frame.T, father, name, byear, bmonth, bday, alive, address, alive, dyear, dmonth, dday);
            if(TN!=0)
                this.frame.T=TN;
            else{
                this.frame.container.remove(this.frame.myPanel_insert.panel_insert);
                this.frame.container.add(this.frame.myPanel_error.panel_error);
                this.frame.container.revalidate();
                this.frame.container.repaint();
                return;
            }
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
        else if(e.getActionCommand()=="插入否"){
            if(this.frame.myPanel_insert.aliveNoJRadioButton.isSelected()){
                this.frame.myPanel_insert.ddayJTextArea.setEnabled(true);
                this.frame.myPanel_insert.dmonthJTextArea.setEnabled(true);
                this.frame.myPanel_insert.dyearJTextArea.setEnabled(true);
            }
        }
        else if(e.getActionCommand()=="插入是"){
            if(this.frame.myPanel_insert.aliveYesJRadioButton.isSelected()){
                this.frame.myPanel_insert.ddayJTextArea.setText("");
                this.frame.myPanel_insert.dmonthJTextArea.setText("");
                this.frame.myPanel_insert.dyearJTextArea.setText("");
                this.frame.myPanel_insert.ddayJTextArea.setEnabled(false);
                this.frame.myPanel_insert.dmonthJTextArea.setEnabled(false);
                this.frame.myPanel_insert.dyearJTextArea.setEnabled(false);

            }
        }
        else if(e.getActionCommand()=="修改否"){
            if(this.frame.myPanel_information.aliveNoJRadioButton.isSelected()){
                this.frame.myPanel_information.ddayJTextArea.setEnabled(true);
                this.frame.myPanel_information.dmonthJTextArea.setEnabled(true);
                this.frame.myPanel_information.dyearJTextArea.setEnabled(true);
            }
        }
        else if(e.getActionCommand()=="修改是"){
            if(this.frame.myPanel_information.aliveYesJRadioButton.isSelected()){
                this.frame.myPanel_information.ddayJTextArea.setText("");
                this.frame.myPanel_information.dmonthJTextArea.setText("");
                this.frame.myPanel_information.dyearJTextArea.setText("");
                this.frame.myPanel_information.ddayJTextArea.setEnabled(false);
                this.frame.myPanel_information.dmonthJTextArea.setEnabled(false);
                this.frame.myPanel_information.dyearJTextArea.setEnabled(false);

            }
        }
        else if(e.getActionCommand()=="按名字搜索"){
            this.frame.myPanel_searchByName.nameJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_searchByName.panel_searchByName);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if (e.getActionCommand()=="按生日搜索"){
            this.frame.myPanel_searchByBirth.byearJTextArea.setText("");
            this.frame.myPanel_searchByBirth.bmonthJTextArea.setText("");
            this.frame.myPanel_searchByBirth.bdayJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_searchByBirth.panel_searchByBirth);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="依据名字搜索"){
            JavaGUI.TNode T1;
            String name=this.frame.myPanel_searchByName.nameJTextArea.getText();
            JavaGUI ctj=new JavaGUI();
            long TN=ctj.searchByName(this.frame.T, name);
            if(TN!=0){
                this.frame.TN=TN;
            }
            else{
                this.frame.container.removeAll();
                this.frame.container.add(this.frame.myPanel_error.panel_error);
                this.frame.container.revalidate();
                this.frame.container.repaint();
                return;
            }
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
            
            //不可修改
            this.frame.myPanel_information.fatherJTextArea.setEnabled(false);
            this.frame.myPanel_information.nameJTextArea.setEnabled(false);
            this.frame.myPanel_information.addressJTextArea.setEnabled(false);
            this.frame.myPanel_information.aliveYesJRadioButton.setEnabled(false);
            this.frame.myPanel_information.aliveNoJRadioButton.setEnabled(false);
            this.frame.myPanel_information.byearJTextArea.setEnabled(false);
            this.frame.myPanel_information.bmonthJTextArea.setEnabled(false);
            this.frame.myPanel_information.bdayJTextArea.setEnabled(false);
            this.frame.myPanel_information.dyearJTextArea.setEnabled(false);
            this.frame.myPanel_information.dmonthJTextArea.setEnabled(false);
            this.frame.myPanel_information.ddayJTextArea.setEnabled(false);


            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_information.panel_information);
            this.frame.container.revalidate();
            this.frame.container.repaint();
            
        }
        else if(e.getActionCommand()=="依据生日搜索"){
            boolean error=false;
            JavaGUI.TNode T1;
            int byear=-1,bmonth=-1,bday=-1;
            try {
                byear=Integer.parseInt(this.frame.myPanel_searchByBirth.byearJTextArea.getText());
                bmonth=Integer.parseInt(this.frame.myPanel_searchByBirth.bmonthJTextArea.getText());
                bday=Integer.parseInt(this.frame.myPanel_searchByBirth.bdayJTextArea.getText());
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }
            }
            
            JavaGUI ctj=new JavaGUI();
            long TN=ctj.searchByBirth(this.frame.T, byear, bmonth, bday);
            if(TN!=0){
                this.frame.TN=TN;
            }
            else{
                this.frame.container.removeAll();
                this.frame.container.add(this.frame.myPanel_error.panel_error);
                this.frame.container.revalidate();
                this.frame.container.repaint();
                return;
            }
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
            //不可修改
            this.frame.myPanel_information.fatherJTextArea.setEnabled(false);
            this.frame.myPanel_information.nameJTextArea.setEnabled(false);
            this.frame.myPanel_information.addressJTextArea.setEnabled(false);
            this.frame.myPanel_information.aliveYesJRadioButton.setEnabled(false);
            this.frame.myPanel_information.aliveNoJRadioButton.setEnabled(false);
            this.frame.myPanel_information.byearJTextArea.setEnabled(false);
            this.frame.myPanel_information.bmonthJTextArea.setEnabled(false);
            this.frame.myPanel_information.bdayJTextArea.setEnabled(false);
            this.frame.myPanel_information.dyearJTextArea.setEnabled(false);
            this.frame.myPanel_information.dmonthJTextArea.setEnabled(false);
            this.frame.myPanel_information.ddayJTextArea.setEnabled(false);
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_information.panel_information);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="修改"){
            this.frame.myPanel_information.nameJTextArea.setEnabled(true);
            this.frame.myPanel_information.addressJTextArea.setEnabled(true);
            this.frame.myPanel_information.aliveYesJRadioButton.setEnabled(true);
            this.frame.myPanel_information.aliveNoJRadioButton.setEnabled(true);
            this.frame.myPanel_information.byearJTextArea.setEnabled(true);
            this.frame.myPanel_information.bmonthJTextArea.setEnabled(true);
            this.frame.myPanel_information.bdayJTextArea.setEnabled(true);
            if(this.frame.myPanel_information.aliveNoJRadioButton.isSelected()){
                this.frame.myPanel_information.dyearJTextArea.setEnabled(true);
                this.frame.myPanel_information.dmonthJTextArea.setEnabled(true);
                this.frame.myPanel_information.ddayJTextArea.setEnabled(true);
            }
            
            //按钮更换
            this.frame.myPanel_information.panel_information.remove(this.frame.myPanel_information.informationButton.b_modify);
            this.frame.myPanel_information.panel_information.add(this.frame.myPanel_information.informationButton.b_confirm);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="确认"){
            boolean alive,error=false;
            int dday=-1,dmonth=-1,dyear=-1,byear=-1,bmonth=-1,bday=-1;
            JavaGUI ctj=new JavaGUI();
            this.frame.myPanel_information.panel_information.remove(this.frame.myPanel_information.informationButton.b_confirm);
            this.frame.myPanel_information.panel_information.add(this.frame.myPanel_information.informationButton.b_modify);
            String name=this.frame.myPanel_information.nameJTextArea.getText();
            try {
                byear=Integer.parseInt(this.frame.myPanel_information.byearJTextArea.getText());
                bmonth=Integer.parseInt(this.frame.myPanel_information.bmonthJTextArea.getText());
                bday=Integer.parseInt(this.frame.myPanel_information.bdayJTextArea.getText());
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    return;
                }
            }
            
            String address=this.frame.myPanel_information.addressJTextArea.getText();
            
            if(this.frame.myPanel_information.aliveYesJRadioButton.isSelected()){
                alive=true;
            }
            else{
                alive=false;
                try {
                    dyear=Integer.parseInt(this.frame.myPanel_information.dyearJTextArea.getText());
                    dmonth=Integer.parseInt(this.frame.myPanel_information.dmonthJTextArea.getText());
                    dday=Integer.parseInt(this.frame.myPanel_information.ddayJTextArea.getText());
                } catch (Exception ee) {
                    error=true;
                }finally{
                    if(error){
                        this.frame.container.removeAll();
                        this.frame.container.add(this.frame.myPanel_error.panel_error);
                        this.frame.container.revalidate();
                        this.frame.container.repaint();
                        return;
                    }
                }
            }
            this.frame.TN=ctj.modify(this.frame.TN, name, byear, bmonth, bday, alive, address, alive, dyear, dmonth, dday);
            this.frame.myPanel_information.panel_information.remove(this.frame.myPanel_information.informationButton.b_confirm);
            this.frame.myPanel_information.panel_information.add(this.frame.myPanel_information.informationButton.b_modify);
            this.frame.container.remove(this.frame.myPanel_information.panel_information);
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
        else if(e.getActionCommand()=="删除中的删除"){
            String name=this.frame.myPanel_delete.nameJTextArea.getText();
            JavaGUI ctj=new JavaGUI();
            this.frame.T=ctj.delete(this.frame.T, name);
            this.frame.myPanel_delete.nameJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();

        }
        else if(e.getActionCommand()=="取消"){
            this.frame.myPanel_delete.nameJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="查询"){
            JavaGUI ctj=new JavaGUI();
            String name_1=this.frame.myPanel_relation.name_1JTextArea.getText();
            String name_2=this.frame.myPanel_relation.name_2JTextArea.getText();
            this.frame.myPanel_relation.relationJLabel.setText(ctj.relation(this.frame.T, name_1, name_2));
        }
        else if(e.getActionCommand()=="查询返回"){
            this.frame.myPanel_relation.relationJLabel.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="创建"){
            JavaGUI ctj=new JavaGUI();
            int year=-1,month=-1,day=-1;
            boolean error=false;
            try {
                year=Integer.parseInt(this.frame.myPanel_creatTime.yearJTextArea.getText());
                month=Integer.parseInt(this.frame.myPanel_creatTime.monthJTextArea.getText());
                day=Integer.parseInt(this.frame.myPanel_creatTime.dayJTextArea.getText());
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    this.frame.myPanel_creatTime.yearJTextArea.setText("");
                    this.frame.myPanel_creatTime.monthJTextArea.setText("");
                    this.frame.myPanel_creatTime.dayJTextArea.setText("");

                    return;
                }
            }
            
            this.frame.Date=ctj.createTime(year, month, day);
            String time=year+"/"+month+"/"+day;
            this.frame.myPanel_init.time.setText(time);
            this.frame.myPanel_creatTime.yearJTextArea.setText("");
            this.frame.myPanel_creatTime.monthJTextArea.setText("");
            this.frame.myPanel_creatTime.dayJTextArea.setText("");
            this.frame.myPanel_init.panel_init.remove(this.frame.myPanel_init.initButton.b_creatTime);
            this.frame.myPanel_init.panel_init.add(this.frame.myPanel_init.initButton.b_modifyTime);
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="创建中的返回"){
            this.frame.myPanel_creatTime.yearJTextArea.setText("");
            this.frame.myPanel_creatTime.monthJTextArea.setText("");
            this.frame.myPanel_creatTime.dayJTextArea.setText("");
            this.frame.myPanel_modifyTime.yearJTextArea.setText("");
            this.frame.myPanel_modifyTime.monthJTextArea.setText("");
            this.frame.myPanel_modifyTime.dayJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
        else if(e.getActionCommand()=="确认修改时间"){
            JavaGUI ctj=new JavaGUI();
            int year=-1,month=-1,day=-1;
            boolean error=false;
            try {
                year=Integer.parseInt(this.frame.myPanel_modifyTime.yearJTextArea.getText());
                month=Integer.parseInt(this.frame.myPanel_modifyTime.monthJTextArea.getText());
                day=Integer.parseInt(this.frame.myPanel_modifyTime.dayJTextArea.getText());
            this.frame.Date=ctj.modifyDate(this.frame.Date, year, month, day);
            } catch (Exception ee) {
                error=true;
            }finally{
                if(error){
                    this.frame.container.removeAll();
                    this.frame.container.add(this.frame.myPanel_error.panel_error);
                    this.frame.container.revalidate();
                    this.frame.container.repaint();
                    this.frame.myPanel_modifyTime.yearJTextArea.setText("");
                    this.frame.myPanel_modifyTime.monthJTextArea.setText("");
                    this.frame.myPanel_modifyTime.dayJTextArea.setText("");
                    return;
                }
            }
            
            String time=year+"/"+month+"/"+day;
            this.frame.myPanel_init.time.setText(time);
            this.frame.myPanel_modifyTime.yearJTextArea.setText("");
            this.frame.myPanel_modifyTime.monthJTextArea.setText("");
            this.frame.myPanel_modifyTime.dayJTextArea.setText("");
            this.frame.container.removeAll();
            this.frame.container.add(this.frame.myPanel_init.panel_init);
            this.frame.container.revalidate();
            this.frame.container.repaint();
        }
    }
    public MyActionListener(MyFrame frame){
        this.frame=frame;
    }
}