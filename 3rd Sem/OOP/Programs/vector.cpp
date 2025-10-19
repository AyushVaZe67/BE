#include<iostream>
#include<vector>
using namespace std;

int main(){

    vector <char> v(10);

    cout << "SIZE : " << v.size() << endl;

    for(int i=0;i<v.size();i++){
        v[i] = i + 'A';
    }

    for(int i=0;i<v.size();i++){
        cout << v[i] << " ";
    }

    cout << endl;

    for(int i=0;i<5;i++){
        v.push_back(i + 10 + 'A');
    }

    for(int i=0;i<v.size();i++){
        cout << v[i] << " ";
    }

    cout << endl;

    for(int i=0;i<5;i++){
        v.pop_back();
    }

    for(int i=0;i<v.size();i++){
        cout << v[i] << " ";
    }


    return 0;
}