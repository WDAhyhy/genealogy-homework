﻿#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include<string.h>
#include<stdint.h>
//下面的头文件为JNI虚拟机，调试时可以注释掉
#include<jni.h>
#include"JavaGUI.h"


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

void f(char* s){
    return;
}

//创建时间节点
//（年，月，日）
Date CreateTime(int year, int month, int day) {
    Date D = (Date)malloc(sizeof(struct Time));
    D->year = year;
    D->month = month;
    D->day = day;
    return D;
}

//创建树节点  
// ("名字"，Data结构体生日，bool婚否，"住址"，bool健在，Data结构体忌日)  返回->创建结点
Tree CreateTNode(const char *name,Date birth,bool marriage,const char *address,bool alive,Date death){
    Tree T=(Tree)malloc(sizeof(struct TNode));
    T->birth=(Date)malloc(sizeof(struct Time));
    T->death=(Date)malloc(sizeof(struct Time));
    T->depth = 1;
    T->name=strdup(name);
    T->birth->day=birth->day;
    T->birth->month=birth->month;
    T->birth->year=birth->year;
    T->age_ratio = birth->day + birth->month * 100 + birth->year * 10000;
    T->marriage=marriage;
    T->address=strdup(address);
    T->alive=alive;
    if(alive){
        T->death->day=-1;
        T->death->month=-1;
        T->death->year=-1;
    }
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
// (头结点，"名字")  返回->查找结点
Tree SearchByName(Tree T,const char *name){
    if(!T){
        return NULL;
    }
    if(!strcmp(T->name,name)){
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

//(头结点，Data结构体)  返回->查找结点
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
// (头结点，"父亲名字"，"本人名字"，int b年，int b月，int b日，bool婚否，"住址"，bool健在，int d年，int d月，int d日)
//返回->树的第一个节点
Tree Insert(Tree T,const char *father,const char *name,int byear,int bmonth,int bday,bool marriage,const char *address,bool alive,int dyear,int dmonth,int dday){
    Tree fathT,broT;
    //报错
    if(T&&strcmp(father,"")==0)
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
    if(!strcmp(father,"")){
        T=newT;
    }
    else {
        if (father != "") {
            fathT = SearchByName(T, father);
            newT->parent = fathT;
            if (!fathT->child)
                fathT->child = newT;
            else {
                broT = fathT->child;
                while (broT->subbro)
                    broT = broT->subbro;
                broT->subbro = newT;
            }
            newT->depth = fathT->depth + 1;
        }
    }
    return T;
}

//关系溯源（输入两人姓名，确定其关系）  
//家谱树已经通过出生日期排序，修改为通过深度确定辈分，下标确定年龄
//(头结点,"名字1","名字2")  输出 print内容，返回值为空
void Relation(Tree T, char* name1, char* name2) {
    Tree Person1 = SearchByName(T, name1);
    Tree Person2 = SearchByName(T, name2);
     if (Person1 == NULL && Person2 == NULL) {
         printf("家谱中没有 %s 和 %s\n", name1, name2);
         return;
     }
     if (Person1== NULL){
         printf("家谱中没有%s\n",name1);
         return;
     }
     if (Person2 == NULL) {
         printf("家谱中没有%s\n", name2);
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
                 printf("%s 是 %s 的哥哥\n", name1, name2);
             else
                 printf("%s 是 %s 的弟弟\n", name1, name2);
         }
         else if (Person1->parent != Person2->parent) {
             if (age1 < age2)
             printf("%s 是 %s 的堂哥\n", name1, name2);
             else
                 printf("%s 是 %s 的堂弟\n", name1, name2);
         }
         break;

     case -1:
         if (Person1 == Person2->parent)
             printf("%s 是 %s 的父亲\n", name1, name2);
         else if (Person1 != Person2->parent && Person1->parent == Person2->parent->parent) {
             if (age1 < Person2->parent->age_ratio)
                 printf("%s 是 %s 的伯伯\n", name1, name2);
             else
                 printf("%s 是 %s 的叔叔\n", name1, name2);
         }
         else if (Person1 != Person2->parent && Person1->parent != Person2->parent->parent) {
             if (age1 < Person2->parent->age_ratio)
                 printf("%s 是 %s 的堂伯\n", name1, name2);
             else
                 printf("%s 是 %s 的堂叔\n", name1, name2);
         }
         break;

     case 1:
         if (Person2 == Person1->parent)
             printf("%s 是 %s 的父亲\n", name2, name1);
         else if (Person2 != Person1->parent && Person2->parent == Person1->parent->parent) {
             if (age2 < Person1->parent->age_ratio)
                 printf("%s 是 %s 的伯伯\n", name2, name1);
             else
                 printf("%s 是 %s 的叔叔\n", name2, name1);
         }
         else if (Person2 != Person1->parent && Person2->parent != Person1->parent->parent) {
             if (age2 < Person1->parent->age_ratio)
                 printf("%s 是 %s 的堂伯\n", name2, name1);
             else
                 printf("%s 是 %s 的堂叔\n", name2, name1);
         }
         break;

     case -2:
         if (Person1 == Person2->parent->parent)
             printf("%s 是 %s 的爷爷\n", name1, name2);
         else if (Person1 != Person2->parent->parent) {
             if (age1 < Person2->parent->parent->age_ratio)
                 printf("%s 是 %s 的伯祖父\n", name1, name2);
             else
                 printf("%s 是 %s 的叔祖父\n", name1, name2);
         }
         break;

     case 2:
         if (Person2 == Person1->parent->parent)
             printf("%s 是 %s 的爷爷\n", name2, name1);
         else if (Person2 != Person1->parent->parent) {
             if (age2 < Person1->parent->parent->age_ratio)
                 printf("%s 是 %s 的伯祖父\n", name2, name1);
             else
                 printf("%s 是 %s 的叔祖父\n", name2, name1);
         }
         break;
     case -3:
         if (Person1 == Person2->parent->parent->parent)
             printf("%s 是 %s 的曾祖父\n", name1, name2);
         else if (Person1 != Person2->parent->parent->parent) {
             if (age1 < Person2->parent->parent->parent->age_ratio)
                 printf("%s 是 %s 的曾伯祖父\n", name1, name2);
             else
                 printf("%s 是 %s 的曾叔祖父\n", name1, name2);
         }
         break;
     case 3:
         if (Person2 == Person1->parent->parent->parent)
             printf("%s 是 %s 的曾祖父\n", name2, name1);
         else if (Person2 != Person1->parent->parent->parent) {
             if (age2 < Person1->parent->parent->parent->age_ratio)
                 printf("%s 是 %s 的曾伯祖父\n", name2, name1);
             else
                 printf("%s 是 %s 的曾叔祖父\n", name2, name1);
         }
         break;
     }
     if (depth1 - depth2 < -3)
         printf("%s 是 %s 的祖先\n", name1, name2);
     else if(depth1 - depth2 > 3)
         printf("%s 是 %s 的祖先\n", name2, name1);
     return;
 }
 

//修改成员信息 
// (修改节点，"名字"，生日节点，bool婚否，"住址"，bool健在，死亡节点）  返回->该节点
Tree Modify(Tree T,const char* name,Date birth,bool marriage,const char *address,bool alive,Date death){
    T->name=strdup(name);
    T->birth->day=birth->day;
    T->birth->month=birth->month;
    T->birth->year=birth->year;
    T->marriage=marriage;
    T->address=strdup(address);
    T->alive=alive;
    if(!alive){
        T->death->day=death->day;
        T->death->month=death->month;
        T->death->year=death->year;
    }
    else
        T->death=NULL;
    return T;
}

//计算族谱人数总数
// (头结点）  返回->总数
int Count(Tree T){
    int num=0;
    if(!T)
        return 0;
    return Count(T->subbro)+Count(T->child)+1;
}

//将树存进数组准备排序  
// (头结点，结构数组，已排序下标，总数)  返回->数组
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
void Swap(Tree* T1,Tree* T2){
    Tree T3=*T1;
    *T1=*T2;
    *T2=T3;
}

//按出生日期对所有成员排序
// (返回值字符串)
char* SortByBirth(Tree T){
    int i,j;
    int num=Count(T);
    char *str=(char*)malloc(4*num*sizeof(char));
    Tree *Sortarry=(Tree*)malloc(num*sizeof(Tree));
    Sortarry=AddInArry(T,Sortarry,0,num);
    //简单选择排序
    for (i = 0; i < num; i++) {
        for (j = i; j < num; j++) {
            if (Sortarry[i]->age_ratio> Sortarry[j]->age_ratio)
                Swap(&Sortarry[i], &Sortarry[j]);
        }
    }
    //对字符串进行处理
    strcpy(str, Sortarry[0]->name);
    for (i = 1; i < num; i++) {
        strcat(str, " ");
        strcat(str, Sortarry[i]->name);
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

//搜索当日生日健在成员  
// (头结点，当日日期)  返回->当日生日健在成员节点
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

//删除函数
//(删除节点)  返回->bool型
bool Delete(Tree T) {
    if (!T) {
        printf("家谱中没有该成员\n");
        return false;
    }
    if (!T->parent) {
        free(T);
        return true;
    }//删除节点为头结点
    else {
        Tree fathT = T->parent;
        if (fathT->child == T) {
            fathT->child = T->subbro;
            return true;
        }
        else {
            Tree broT = fathT->child;
            while (broT->subbro != T)
                broT = broT->subbro;
            broT->subbro = NULL;
            return true;
        }
    }
}

//提醒当天生日的健在成员(如有，返回名字（或者该节点？），没有返回空字符（或者NULL？）并提示今日无家族成员过生日（或者距离今日生日最近成员？？可作为完善功能）)
//(头结点，当前日期)  无返回值
void  RemindBirth(Tree T,Date date){
    Tree check = CheckBirth(T, date);
    if (check== NULL)
        printf("今日无家族成员过生日\n");
    else printf("今日%s过生日\n", check->name);
    return;
}

// int main() {
//     Date Today = CreateTime(2024,2,12);
//     Tree T = Insert(NULL, "", "祖先", 1950, 1, 1, true, "翻斗花园", false, 2005, 1, 1);
//     Insert(T,"祖先", "小明1", 1970, 1, 2, true, "翻斗花园", false,2011 , 1, 2);
//     Insert(T, "祖先", "小明2", 1971, 1, 3, true, "翻斗花园", false, 2012, 1, 3);
//     Insert(T, "小明1", "阿华1", 1990, 1, 4, true, "翻斗花园", false, 2019, 1, 4);
//     Insert(T, "小明1", "阿华2", 1991, 1, 5, true, "翻斗花园", false, 2019, 1, 5);
//     Insert(T, "小明2", "阿聪1", 1990, 1, 6, true, "翻斗花园", false, 2019, 1, 6);
//     Insert(T, "小明2", "阿聪2", 1991, 1, 7, true, "翻斗花园", false, 2019, 1, 7);
//     Insert(T, "阿华1", "狗蛋", 2000, 1, 8, true, "翻斗花园", true,0,0,0);
//     //Date Dan_Birth = CreateTime(2004, 2, 12);
//     //Tree ModifyT1 = SearchByName(T, "狗蛋");
//     //Tree ModifyT2 = SearchByName(T, "小明2");
//     //Modify(ModifyT1, "二蛋",Dan_Birth, true, "翻斗花园", true,NULL);
//     //printf("%s\n", T->name);
//     //printf("%d\n", T->depth);
//     //printf("%d.%d.%d\n", T->birth->year, T->birth->month, T->birth->day);
//     //printf("%s\n", T->address);
//     //printf("%d.%d.%d\n", T->death->year, T->death->month,T->death->day);
//     //Relation(T,"二蛋", "阿华1");
//     //RemindBirth(T, Today);
//     //Delete(ModifyT2);
//     //Relation(T, "小明1", "小明2");
//     //Relation(T, "二蛋", "小明2");
//     //Relation(T, "阿聪1", "小明2");
//     printf("%s\n", SortByBirth(T));
// }



// 以下为JNI函数
JNIEXPORT jobject JNICALL Java_JavaGUI_convertToTree(JNIEnv *env,jobject obj,jlong T){
    
   Tree tnode=(Tree)T;
   // 创建Java中的Date类
   jclass dateClass = (*env)->FindClass(env, "JavaGUI$Date"); 
   jmethodID dateConstructor = (*env)->GetMethodID(env, dateClass, "<init>", "(III)V");
   
   
   // 将C结构体中的日期数据填充到Java对象中
   jobject javaBirth = (*env)->NewObject(env, dateClass, dateConstructor, tnode->birth->year,
                                          tnode->birth->month, tnode->birth->day);
   
   jobject javaDeath = (*env)->NewObject(env, dateClass, dateConstructor, tnode->death->year,
                                          tnode->death->month, tnode->death->day);
   
   // 创建Java中的TNode类
   jclass tNodeClass = (*env)->FindClass(env, "JavaGUI$TNode");
   jmethodID tNodeConstructor = (*env)->GetMethodID(env, tNodeClass, "<init>", "(IILjava/lang/String;LJavaGUI$Date;ZLjava/lang/String;ZLJavaGUI$Date;JJJ)V");
   // 将C结构体的数据填充到Java对象中
   jobject javaTNode = (*env)->NewObject(env, tNodeClass, tNodeConstructor, tnode->depth, tnode->age_ratio,
                                           (*env)->NewStringUTF(env, tnode->name), javaBirth, tnode->marriage,
                                           (*env)->NewStringUTF(env, tnode->address), tnode->alive, javaDeath,
                                           tnode->parent, tnode->child, tnode->subbro);
   
   return javaTNode;
}
JNIEXPORT jobject JNICALL Java_JavaGUI_convertToDate(JNIEnv *env,jobject obj,jlong date){
   Date Dnode=(Date)date;
   // 创建Java中的Date类
   jclass dateClass = (*env)->FindClass(env, "JavaGUI$Date"); 
   jmethodID dateConstructor = (*env)->GetMethodID(env, dateClass, "<init>", "(III)V");
   jobject javadate=(*env)->NewObject(env,dateClass,dateConstructor,Dnode->year,Dnode->month,Dnode->day);
   return javadate;

}

//查找 名
JNIEXPORT jlong JNICALL Java_JavaGUI_searchByName(JNIEnv *env,jobject obj,jlong T,jstring name){
   
   const char* c_name=(*env)->GetStringUTFChars(env,name,NULL);
   Tree T1=(Tree)T;
   Tree TN=SearchByName(T1,c_name);
   (*env)->ReleaseStringUTFChars(env,name,c_name);
   return (jlong)TN;
}
//查找 生日
JNIEXPORT jlong JNICALL Java_JavaGUI_searchByBirth(JNIEnv *env,jobject obj,jlong T,jint year,jint month,jint day){
   Tree T1=(Tree)T;
   Date birth=(Date)malloc(sizeof(struct Time));
   birth->year=year;
   birth->month=month;
   birth->day=day;
   T1=SearchByBirth(T1,birth);
   free(birth);
   return (jlong)T1;
}
//成员插入
JNIEXPORT jlong JNICALL Java_JavaGUI_insert(JNIEnv *env,jobject obj,jlong T,jstring father,jstring name,jint byear,jint bmonth,jint bday,jboolean marriage,jstring address,jboolean alive,jint dyear,jint dmonth,jint dday){
   const char* c_name=(*env)->GetStringUTFChars(env,name,NULL);
   const char* c_address=(*env)->GetStringUTFChars(env,address,NULL);
   const char* c_father=(*env)->GetStringUTFChars(env,father,NULL);
   Tree T1=(Tree)T;
   T1=Insert(T1,c_father,c_name,byear,bmonth,bday,marriage,c_address,alive,dyear,dmonth,dday);
   (*env)->ReleaseStringUTFChars(env,name,c_name);
   (*env)->ReleaseStringUTFChars(env,father,c_father);
   (*env)->ReleaseStringUTFChars(env,address,c_address);
   return (jlong)T1;
}
// JNIEXPORT jlong JNICALL Java_JavaGUI_test(JNIEnv *env,jobject obj,jstring str){
//     char* c_name=(*env)->GetStringUTFChars(env,str,NULL);
//     f(c_name);
//     int b=6;
//     int *a;
//     a=&b;
//     (*env)->ReleaseStringUTFChars(env,str,c_name);
    
//     return (jlong)a;
// }
// int main(){
//     Tree T=Insert(NULL, "", "你好", 0, 0, 0, false, NULL, false, 0, 0, 0);
//     printf("%s",T->name);
// }
JNIEXPORT jlong JNICALL Java_JavaGUI_modify(JNIEnv *env,jobject obj,jlong T,jstring name,jint byear,jint bmonth,jint bday,jboolean marriage,jstring address,jboolean alive,jint dyear,jint dmonth,jint dday){
   const char* c_name=(*env)->GetStringUTFChars(env,name,NULL);
   const char* c_address=(*env)->GetStringUTFChars(env,address,NULL);
   Tree T1=(Tree)T;
   Date birth=(Date)malloc(sizeof(struct Time));
   Date death=(Date)malloc(sizeof(struct Time));
   birth->day=bday;
   birth->month=bmonth;
   birth->year=byear;
   if(!alive){
       death->day=dday;
       death->month=dmonth;
       death->year=dyear;
   }
   else
       death=NULL;
   T1=Modify(T1,c_name,birth,marriage,c_address,alive,death);
   (*env)->ReleaseStringUTFChars(env,name,c_name);
   (*env)->ReleaseStringUTFChars(env,address,c_address);
   free(birth);
   free(death);
   return (jlong)T1;
}
JNIEXPORT jstring JNICALL Java_JavaGUI_sortByBirth(JNIEnv *env,jobject obj,jlong T){
   return (*env)->NewStringUTF(env,SortByBirth((Tree)T));
}
JNIEXPORT jlong JNICALL Java_JavaGUI_modifyDate(JNIEnv *env,jobject obj,jlong date,jint year,jint month,jint day){
   return (jlong)ModifyDate((Date)date,year,month,day);
}
// JNIEXPORT jstring JNICALL Java_JavaGUI_remindBirth(JNIEnv *env,jobject obj,jlong T,jlong date){
//    return (*env)->NewStringUTF(env,RemindBirth((Tree)T,(Date)date));
// }