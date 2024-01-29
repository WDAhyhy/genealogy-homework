#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
//下面的头文件为JNI虚拟机，调试时可以注释掉
// #include<jni>


typedef struct TNode* Tree;
typedef struct Time* Date;
/*这是一个函数库，通过C语言进行主要函数的构建，再通过java的GUI进行图形化界面的交互*/





struct Time{
    int year;
    int month;
    int day;
};
//节点结构体（姓名、出生日期、婚否、地址、健在否、死亡日期（若其已死亡），也可附加其它信息、但不是必需的。由于同一辈可能不止两人，则要用兄弟节点）
struct TNode{
    char *name;
    Date birth;
    bool marriage;
    char *address;
    bool alive;
    Date death;
    Tree child;
    Tree subbro;
};
//创建树节点
Tree CreateTNode(char *name,Date birth,bool marriage,char *address,bool alive,Date death){
    Tree T=(Tree)malloc(sizeof(struct TNode));
    T->name=name;
    T->birth=birth;
    T->marriage=marriage;
    T->address=address;
    T->alive=alive;
    T->death=death;
    return T;
}
//成员插入(返回值为树的第一个节点)

//成员查询(通过姓名或者出生日期查询)

//关系溯源（输入两人姓名，确定其关系）

//修改成员信息（先查询，再修改）

//按出生日期对所有成员排序

//提醒当天生日的健在成员(如有，返回名字（或者该节点？），没有返回空字符（或者NULL？）)

//修改当前日期(因为要实现上个函数)

