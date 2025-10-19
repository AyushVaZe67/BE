#include<iostream>
using namespace std;

class Base{
    public:
    Base(){
        cout<<"This is Based Cons"<<endl;
    }
    ~Base(){
        cout<<"This is Based Dest"<<endl;
    }
};

class Derived : public Base{
    public:
    Derived(){
        cout<<"This is Derived Cons"<<endl;
    }
    ~Derived(){
        cout<<"This is Derived Dest"<<endl;
    }
};

int main(){
    Derived obj;
    return 0;
}