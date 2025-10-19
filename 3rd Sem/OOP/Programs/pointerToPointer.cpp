#include<iostream>
using namespace std;

int main(){

    int a = 10;
    int *aptr;
    aptr = &a;

    cout << *aptr << endl;

    int **aptr2;
    aptr2 = &aptr;
    cout << aptr2 << endl; // 88
    cout << *aptr2 << endl; //94
    cout << **aptr2 << endl; //10

    return 0;
}