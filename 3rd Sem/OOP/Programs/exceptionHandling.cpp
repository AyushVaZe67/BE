#include<iostream>
using namespace std;

int main(){

    // int age = 20;
    int age;
    cout<<"Enter your Age : ";
    cin>>age;

    try{
        if(age>=18){
            cout<<"DRIVE AND GO HOME !!!";
        }
        else{
            throw(age);
        }
    }catch(int age){
        cout << "Dont drive kid !!!" << endl;
        cout << "Your age is : " << age << endl;
    }

    return 0;
}