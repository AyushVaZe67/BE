#include<iostream>
using namespace std;

class Complex{
private:
int real,imag;
public:
Complex(int r=0,int i=0){
        real = r;
        imag = i;
}
void display(){
    cout<<real<<"+i"<<imag<<endl;
}
Complex operator +(Complex const& obj){
    Complex res;
    res.real = real + obj.real;
    res.imag = imag + obj.imag;
    return res;
}

};

int main(){

    Complex c1(10,20) ,c2(30,40);
    Complex c3 = c1 + c2;
    c3.display();
    return 0;
}