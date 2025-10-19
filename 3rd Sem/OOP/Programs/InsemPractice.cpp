#include<iostream>
using namespace std;

class Test{

    int height,width;
    public:
    Test(int x,int y){
    height = x;
    width = y;
    }
    void printArea(){
        cout << height * width;
    }

};
    
int main(){

    Test t1(10,20);
    t1.printArea();
    

    return 0;
}