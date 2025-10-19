#include<iostream>
using namespace std;

class Distance{
    private:
    int feet,inches;
    public:
    Distance(int f,int i){
        feet = f;
        inches = i;
    }
    Distance(){
        feet = 0;
        inches = 0;
    }
    void display(){
        cout<<"F : "<<feet<<"\nI : "<<inches<<endl;
    }
    Distance operator -(){
        feet = -feet;
        inches = -inches;
        return Distance(feet,inches);
    }
};

int main(){

    Distance d1(10,11), d2(-20,29);
    -d1;
    d1.display();
    -d2;
    d2.display();

    return 0;
}