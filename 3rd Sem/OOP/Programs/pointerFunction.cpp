#include<iostream>
using namespace std;

void increment(int *a,int *b){
    int c = *b;

}

int main(){

    int a = 2;
    int b = NULL;
    // int *ptr;
    // ptr = &a;
    cout << b << endl;
    increment(&a,&b);
    cout << b << endl;

    return 0;
}