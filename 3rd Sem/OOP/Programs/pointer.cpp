#include<iostream>
using namespace std;

int main()
{
    int a = 10;
    int *aptr;
    aptr = &a;
    cout << aptr << endl; //fd14
    cout << a << endl; //10
    cout << *aptr << endl; //10

    

    return 0;
}