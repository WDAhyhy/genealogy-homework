#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include<string.h>
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
    int depth;
    int age_ratio;
    char *name;
    Date birth;
    bool marriage;
    char *address;
    bool alive;
    Date death;
    Tree parent;
    Tree child;
    Tree subbro;
};
//创建树节点
Tree CreateTNode(char *name,Date birth,bool marriage,char *address,bool alive,Date death){
    Tree T=(Tree)malloc(sizeof(struct TNode));
    T->depth = 1;
    T->age_ratio = birth->day + birth->month*100 + birth->year*10000;
    T->name=name;
    T->birth->day=birth->day;
    T->birth->month=birth->month;
    T->birth->year=birth->year;
    T->marriage=marriage;
    T->address=address;
    T->alive=alive;
    if(alive)
        T->death=NULL;
    else{
        T->death->day=death->day;
        T->death->month=death->month;
        T->death->year=death->year;
    }
    T->parent = NULL;
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
Tree Insert(Tree T,char *father,char *name,int byear,int bmonth,int bday,bool marriage,char *address,bool alive,int dyear,int dmonth,int dday){
    Tree fathT,broT;
    //报错
    if(T&&father=="")
        return NULL;
    Date birth=(Date)malloc(sizeof(struct Time));
    birth->year=byear;
    birth->month=bmonth;
    birth->day=bday;
    Date death;
    if(alive)
        death=NULL;
    else{
        death=(Date)malloc(sizeof(struct Time));
        death->year=dyear;
        death->month=dmonth;
        death->day=dday;
    }
    Tree newT=CreateTNode(name,birth,marriage,address,alive,death);
    if(father!=""){
        fathT=SearchByName(T,father);
        newT->parent = fathT;
        if (!fathT->child) 
            fathT->child = newT;
        else{
            broT=fathT->child;
            while(broT->subbro)
                broT=broT->subbro;
            broT->subbro=newT;
        }
        newT->depth = fathT->depth + 1;
    }
    else
        T=newT;
    return T;
}

//关系溯源（输入两人姓名，确定其关系）
char* Relation(Tree T, char* name1, char* name2) {
    Tree Person1 = (Tree)malloc(sizeof(struct TNode));
    Tree Person2 = (Tree)malloc(sizeof(struct TNode));
    Person1 = SearchByName(T, name1);
    Person2 = SearchByName(T, name2);
    if (Person1== NULL){
        printf("家谱中没有%c\n",name1);
        return;
    }
    if (Person2 == NULL) {
        printf("家谱中没有%c\n", name2);
        return;
    }
    int depth1 = Person1->depth;
    int depth2 = Person2->depth;
    int age1 = Person1->age_ratio;
    int age2 = Person2->age_ratio;
    switch (depth1 - depth2) {
    case 0:
        if (Person1->parent == Person2->parent) {
            if (age1 < age2)
                printf("%c 是 %c 的哥哥\n", name1, name2);
            else
                printf("%c 是 %c 的弟弟\n", name1, name2);
        }
        else if (Person1->parent != Person2->parent) {
            if (age1) < age2)
            printf("%c 是 %c 的堂哥\n", name1, name2);
            else
                printf("%c 是 %c 的堂弟\n", name1, name2);
        }
        break;

    case -1:
        if (Person1 == Person2->parent)
            printf("%c 是 %c 的父亲\n", name1, name2);
        else if (Person1 != Person2->parent && Person1->parent == Person2->parent->parent) {
            if (age1 < Person2->parent.age_ratio)
                printf("%c 是 %c 的伯伯\n", name1, name2);
            else
                printf("%c 是 %c 的叔叔\n", name1, name2);
        }
        else if (Person1 != Person2->parent && Person1->parent != Person2->parent->parent) {
            if (age1 < Person2->parent.age_ratio)
                printf("%c 是 %c 的堂伯\n", name1, name2);
            else
                printf("%c 是 %c 的堂叔\n", name1, name2);
        }
        break;

    case 1:
        if (Person2 == Person1->parent)
            printf("%c 是 %c 的父亲\n", name2, name1);
        else if (Person2 != Person1->parent && Person2->parent == Person1->parent->parent) {
            if (age2 < Person1->parent.age_ratio)
                printf("%c 是 %c 的伯伯\n", name2, name1);
            else
                printf("%c 是 %c 的叔叔\n", name2, name1);
        }
        else if (Person2 != Person1->parent && Person2->parent != Person1->parent->parent) {
            if (age2 < Person1->parent.age_ratio)
                printf("%c 是 %c 的堂伯\n", name2, name1);
            else
                printf("%c 是 %c 的堂叔\n", name2, name1);
        }
        break;

    case -2:
        if (Person1 == Person2->parent->parent)
            printf("%c 是 %c 的爷爷\n", name1, name2);
        else if (Person1 != Person2->parent->parent) {
            if (age1 < Person2->parent->parent.age_ratio)
                printf("%c 是 %c 的伯祖父\n", name1, name2);
            else
                printf("%c 是 %c 的叔祖父\n", name1, name2);
        }
        break;

    case 2:
        if (Person2 == Person1->parent->parent)
            printf("%c 是 %c 的爷爷\n", name2, name1);
        else if (Person2 != Person1->parent->parent) {
            if (age2 < Person1->parent->parent.age_ratio)
                printf("%c 是 %c 的伯祖父\n", name2, name1);
            else
                printf("%c 是 %c 的叔祖父\n", name2, name1);
        }
        break;
    case -3:
        if (Person1 == Person2->parent->parent->parent)
            printf("%c 是 %c 的曾祖父\n", name1, name2);
        else if (Person1 != Person2->parent->parent->parent) {
            if (age1 < Person2->parent->parent->parent.age_ratio)
                printf("%c 是 %c 的曾伯祖父\n", name1, name2);
            else
                printf("%c 是 %c 的曾叔祖父\n", name1, name2);
        }
        break;
    case 3:
        if (Person2 == Person1->parent->parent->parent)
            printf("%c 是 %c 的曾祖父\n", name2, name1);
        else if (Person2 != Person1->parent->parent->parent) {
            if (age2 < Person1->parent->parent->parent.age_ratio)
                printf("%c 是 %c 的曾伯祖父\n", name2, name1);
            else
                printf("%c 是 %c 的曾叔祖父\n", name2, name1);
        }
        break;
    }
    if (depth1 - depth2 < -3)
        printf("%c 是 %c 的祖先\n", name1, name2);
    else if(depth1 - depth2 > 3)
        printf("%c 是 %c 的祖先\n", name2, name1);
    free(Person1);
    free(Person2);
    return;
}

//修改成员信息(输入为要修改的节点以及全部信息，返回值为该节点)
Tree Modify(Tree T,char* name,Date birth,bool marriage,char *address,bool alive,Date death){
    T->name=name;
    T->birth=birth;
    T->marriage=marriage;
    T->address=address;
    T->alive=alive;
    T->death=death;
    return T;
}
//计算族谱人数总数
int Count(Tree T){
    int num=0;
    if(!T)
        return 0;
    return Count(T->subbro)+Count(T->child)+1;
}
//将树存进数组准备排序
Tree *AddInArry(Tree T,Tree *arry,int index,int num){
    Tree *arry2;
    if(!T)
        return NULL;
    arry[index]=T;
    arry2=AddInArry(T->subbro,arry,++index,num);
    if(arry2){
        arry=arry2;
        index++;
    }
    arry2=AddInArry(T->child,arry,index,num);
    if(arry2)
        arry=arry2;
    return arry;
}
//交换
void Swarp(Tree* T1,Tree* T2){
    Tree T3=*T1;
    *T1=*T2;
    *T2=T3;
}
//按出生日期对所有成员排序(返回值字符串)
char* SortByBirth(Tree T){
    int i,j;
    int num=Count(T);
    char *str=(char*)malloc(4*num*sizeof(char));
    Tree *Sortarry=(Tree*)malloc(num*sizeof(Tree));
    Sortarry=AddInArry(T,Sortarry,0,num);
    //简单选择排序
    for(i=0;i<num;i++){
        for(j=i;j<num;j++){
            if(Sortarry[i]->birth->year>Sortarry[j]->birth->year)
                Swarp(&Sortarry[i],&Sortarry[j]);
            else if(Sortarry[i]->birth->year==Sortarry[j]->birth->year){
                if(Sortarry[i]->birth->month>Sortarry[j]->birth->month)
                    Swarp(&Sortarry[i],&Sortarry[j]);
                else if(Sortarry[i]->birth->month==Sortarry[j]->birth->month){
                    if(Sortarry[i]->birth->day>Sortarry[j]->birth->day)
                        Swarp(&Sortarry[i],&Sortarry[j]);
                }
            }
        }
    }
    //对字符串进行处理
    strcpy(str,Sortarry[0]->name);
    for(i=1;i<num;i++){
        strcat(str," ");
        strcat(str,Sortarry[i]->name);
    }
    return str;
}
//修改当前日期
Date ModifyDate(Date date,int year,int month,int day){
    date->year=year;
    date->month=month;
    date->day=day;
    return date;
}
Tree CheckBirth(Tree T,Date date){
    Tree T1;
    if(!T)
        return NULL;
    if(T->birth->day==date->day&&T->birth->month==date->month)
        return T;
    else{
        T1=CheckBirth(T->child,date);
        if(T1)
            return T1;
        T1=CheckBirth(T->subbro,date);
        if(T1)
            return T1;
        return NULL;
    }
}
//提醒当天生日的健在成员(如有，返回名字（或者该节点？），没有返回空字符（或者NULL？）)
char* RemindBirth(Tree T,Date date){
    return CheckBirth(T,date)->name;
}

