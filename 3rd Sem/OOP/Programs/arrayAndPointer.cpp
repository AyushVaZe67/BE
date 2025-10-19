#include<iostream>
using namespace std;

int main(){

    int arr[] = {10,20,30};

    cout << *arr << endl;

    // To print values
    for(int i=0;i<3;i++){
        cout << *(arr+i) << endl;
    }

    //To print addresses
    for(int i=0;i<3;i++){
        cout << arr+i << endl;
    }


    return 0;
}