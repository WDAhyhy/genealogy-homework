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
    T->subbro=NULL;
    T->child=NULL;
    return T;
}

//成员查询(通过姓名或者出生日期查询)
Tree SearchByName(Tree T,char *name){
    if(!T){
        return NULL;
    }
    if(T->name==name){
        return T;
    }
    else{
        if(SearchByName(T->child,name))
            return (SearchByName(T->child,name));
        if(SearchByName(T->subbro,name))
            return (SearchByName(T->subbro,name));
        return NULL;
    }
}
Tree SearchByBirth(Tree T,Date birth){
    if(!T){
        return NULL;
    }
    if(T->birth->year==birth->year&&T->birth->month==birth->month&&T->birth->day==birth->day){
        return T;
    }
    else{
        if(SearchByBirth(T->child,birth))
            return(SearchByBirth(T->child,birth));
        if(SearchByBirth(T->subbro,birth))
            return(SearchByBirth(T->child,birth));
        return NULL;
    }
}
//成员插入(返回值为树的第一个节点)(以其父节点进行查找插入)
Tree Insert(Tree T,char *father,char *name,Date birth,bool marriage,char *address,bool alive,Date death){
    Tree fathT,broT;
    //报错
    if(T&&father=="")
        return NULL;
    Tree newT=CreateTNode(name,birth,marriage,address,alive,death);
    if(father!=""){
        fathT=SearchByName(T,father);
        if(!fathT->child)
            fathT->child=newT;
        else{
            broT=fathT->child;
            while(broT->subbro)
                broT=broT->subbro;
            broT->subbro=newT;
        }
    }
    else
        T=newT;
    return T;
}

//关系溯源（输入两人姓名，确定其关系）

//修改成员信息（先查询，再修改）

//按出生日期对所有成员排序

//提醒当天生日的健在成员(如有，返回名字（或者该节点？），没有返回空字符（或者NULL？）)

//修改当前日期(因为要实现上个函数)

