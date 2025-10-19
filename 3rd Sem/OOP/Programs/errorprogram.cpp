#include<iostream>
using namespace std;

class Student{
    public:
    int roll no;
    string name;
    string dept;
    void getdata();
    void putdata();
};

void Student::getdata(){
    cout << "Enter the roll no : ";
    cin >> rollno;
    cout << "Enter the name of Student : ";
    cin >> name;
    cout << "The Department name is : ";
    cin >> dept;
}

void Student::putdata(){
    cout << "Roll NO :- " << rollno;
    cout << "Name :- " << name;
    cout << "Department :- " << dept;
}

int main(){

    Student s1;
    s1.getdata();
    s1.putdata();


    return 0;
}


