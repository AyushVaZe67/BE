#include<iostream>
#include<fstream>
using namespace std;

class TRAIN{
    protected:
        int trainno;
        char dest[20];
        float distance;
    public:
        void get(){
            cout << "\nEnter Train No. : ";
            cin >> trainno;
            cout << "\nEnter Destination : ";
            cin >> dest;
            cout << "\nEnter Distance : ";
            cin >> distance;
        }
        void show(){
            ifstream inObj;
            inObj.open("test.dat",ios::binary);
            inObj.read((char*)this,sizeof(TRAIN));
        }
        void put(){
            ofstream outObj;
            outObj.open("test.dat",ios::app|ios::binary);
            outObj.write((char*).this,sizeof(TRAIN));
        }
};

int main(){

    TRAIN obj;
    cout<<"\nEnter Information below : ";
    obj.get();
    cout<<"\t Reading Information .... ";
    obj.put();
    cout<<"\n The Information is : ";
    obj.show();

    return 0;
}