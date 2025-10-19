#include<iostream>
using namespace std;

class Test{
    int data;
    friend int fun(int x);
    public:
    Test(){
        data = 5;
    }
};

int fun(int x){
    Test obj;
    return obj.data + x;
}

int main(){
    
    cout << fun(3);
    return 0;
}