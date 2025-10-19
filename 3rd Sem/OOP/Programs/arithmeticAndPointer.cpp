#include<iostream>
using namespace std;

int main(){

    int a = 10;
    int *aptr;
    aptr = &a;

    cout << aptr << endl;
    // *aptr++;
    // cout << aptr << endl;
    // *aptr--;
    // cout << aptr << endl;
    cout << aptr << endl;


    return 0;
}