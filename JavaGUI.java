import java.awt.*;
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
        new MyFrame().init();
    }
}
class MyFrame extends JFrame{
    Container container;
    public void init(){
        
    }
    public  MyFrame(){
        super("族谱管理系统");
        setVisible(true);
        this.container=getContentPane();
        this.container.setLayout(null);
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.container.setBackground(new Color(173, 216, 230));
        //按钮
        JButton b_insert=new JButton("插入");
        b_insert.setBounds(1350,50,150,50);
        b_insert.setFont(new Font("宋体",Font.BOLD,30));
        JButton b_search=new JButton("查找");
        b_search.setBounds(getBounds());
        this.container.add(b_insert);
        setVisible(true);

    }
}