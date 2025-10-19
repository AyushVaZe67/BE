#include<iostream>
using namespace std;

class Arithmetic{
    private:
    int num1,num2;
    char opr;
    public:
    Arithmetic(int x,char op,int y){
        num1 = x;
        num2 = y;
        opr = op;
    }
    int operation(){
        int result;
        switch (opr){
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
        }
        return result;
    }
};

int main(){
    Arithmetic obj(10,'+',10);
    Arithmetic obj1(50,'/',62);
    cout << obj.operation();
    cout << obj1.operation();

    return 0;
}