#include<iostream>
using namespace std;
class complex
{
    private:float R,I;
    public: complex();
            friend istream & operator >>(istream &, complex &);
            friend ostream & operator <<(ostream &,complex &);
            complex operator +(complex &);
            complex operator *(complex &);
};
complex::complex()
{
    R=0;
    I=0;
}
istream & operator >>(istream &in, complex &c)
{
    cout<<"\n Enter Real :";
    in>>c.R;     cout<<"\n Enter Imaginary :";
    in>>c.I;
    return in;
}
ostream & operator <<(ostream &out,complex &c)
{
    out<<"\t"<<c.R<<" + i"<<c.I;
    return out;
}
complex complex:: operator +(complex &c)
{
    complex temp;
    temp.R=this->R+c.R;
    temp.I=this->I+c.I;
    return temp;
} 
complex complex:: operator *(complex &c)
{
    complex temp;
    temp.R=(this->R*c.R)-(this->I*c.I);
    temp.I=(this->R*c.I)+(this->I*c.R);
    return temp;
}
int main()
{
    complex c1,c2,c3;
    cout<<c1;
    cout<<"\n Enter First complex Number :";
    cin>>c1;//operator(cin,c1)
    cout<<"\n Enter Second complex Number :";
    cin>>c2;
    cout<<c1;
    cout<<c2;
    //Addition of Two complex
    c3=c1+c2;
    
    cout<<"\n Addition : "<<c3;
    //multiplication of two complex
    c3=c1*c2; 
    
    cout<<"\n Multiplication : "<<c3;
 }