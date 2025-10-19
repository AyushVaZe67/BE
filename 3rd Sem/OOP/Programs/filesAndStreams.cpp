/*
fstream -> support input output on file
ifstream for i/p ops
ofstream for o/p ops
*/
#include<iostream>
#include<fstream>
using namespace std;

int main(){
    //using const and writing
    string st = "Ayush is goods";
    ofstream out("sample.txt");
    out<<st;

    //using const and reading
    string st2;
    ifstream in("sample1.txt");
    // in>>st2;
    //getline to print next line else one word will be printed
    getline(in, st2);
    getline(in, st2);
    cout<<st2;

    return 0;
}