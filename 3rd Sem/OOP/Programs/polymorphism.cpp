#include <iostream>
using namespace std;

class One{
    public:
    void showName(){
        cout << "Name 1";
    }
};

class Two : public One{
    public:
    void showName(){
        cout << "Name 2";
    }
};

int main(){
    Two t1;
    One o1;

    t1.showName();
    o1.showName();

}