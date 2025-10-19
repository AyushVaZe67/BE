#include<iostream>
using namespace std;

class Image{
    private:
        int height,width;
    public:
        Image(int x,int y){
            height = x;
            width = y;
        }
        int area(){
            return (height * width);
        }
};

int main(){
    Image img(10,21);
    cout << img.area();

    return 0;
}