import javax.swing.*;
//需要实现插入，删除，查找，添加，判断
public class JavaGUI {
    //加载C库
    static{
        System.loadLibrary("C_functions");
    }
    static class TNode {
        int depth;
        int age_ratio;
        String name;
        Date birth;
        boolean marriage;
        String address;
        boolean alive;
        Date death;
        TNode parent;
        TNode child;
        TNode subbro;
        
    }
    static class Date {
        int year;
        int month;
        int day; 
    }
    //Native方法声明
    public native long searchByName(long T,String name);
    public native long searchByBirth(long T,int year,int month,int day);
    public native long insert(long T,String father,String name,int byear,int bmonth,int bday,boolean marriage,String address,boolean alive,int dyear,int dmonth,int dday);



    //主函数
    public static void main(String[] args) {
        // JFrame frame = new JFrame("族谱管理系统");
        // frame.setSize(1920,1080);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setVisible(true);
        JavaGUI CF=new JavaGUI();
        TNode T=CF.insert(0, null, "你好", 0, 0, 0, false, null, false, 0, 0, 0);


    }


}