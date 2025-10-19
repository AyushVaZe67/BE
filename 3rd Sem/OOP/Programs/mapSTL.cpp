#include<iostream>
#include<map>
#include<string>
using namespace std;

int main(){
    
    map<string,int> mp;
    mp["Ayush"] = 69;
    mp["Samipatrao"] = 70;
    
    map<string,int> :: iterator it;
    for(it=mp.begin();it!=mp.end();it++){
        cout<<(*it).first<< " " << (*it).second<<"\n";
    }
    
    return 0;
}