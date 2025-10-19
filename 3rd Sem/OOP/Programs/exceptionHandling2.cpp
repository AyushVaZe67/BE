#include<iostream>
using namespace std;

int main(){
    int num,denum;
    float output;
    cout << "Enter numbers for division : " << endl;
    cin >> num;
    cin >> denum;

    try{
        if(denum == 0){
            throw denum;
        }
        else{
        output = num / denum;
        cout << "DIVISION : " << output << endl;
        }
    }catch(int d){
        cout<<"Your cannot divide by 0 pagal"<<endl;
    }

    return 0;
}