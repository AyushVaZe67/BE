#include <iostream.h>
using namespace std;
class Vehicle{
    public:
    string brand;
    void showBrand(){
        cout << brand;
    }
};

int main()
{

    Vehicle v1;
    v1.brand = "Hero";
    v1.showBrand();

    return 0;
};