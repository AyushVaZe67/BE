#include <iostream>
using namespace std;

class Grand{
    public:
    void showGrand(){
        cout << "GrandFather OP";
    }
};

class Parent:public Grand{
    public:
    void showParent(){
        cout << "Parent OP";
    }
};

class Child:public Parent{
    public:
    void showChild(){
        cout << "Child OP";
    }
};

int main(){
    Child c1;
    c1.showChild();

    return 0;

}