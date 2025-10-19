#include <iostream>
using namespace std;
class Complex {
public:

  Complex(double real, double imag) : real(real), imag(imag) {}

  Complex operator+(const Complex& other) const {
    return Complex(real + other.real, imag + other.imag);
  }

  // Member functions to access real and imaginary parts
  double getReal() const { return real; }
  double getImag() const { return imag; }

private:
  double real;
  double imag;
};

int main() {
  // Declare two complex numbers
  int num1,num2,num3,num4;
  cout<<"Enter Num1 : ";
  cin>>num1;cout<<"+i";
  cin>>num2;
  cout<<"Enter Num1 : ";
  cin>>num3;
  cin>>num4;
  Complex c1(num1, num2);
  Complex c2(num3, num4);

  // Add the two complex numbers
  Complex sum = c1 + c2;

  // Print the sum
  std::cout << "Sum: " << sum.getReal() << " + " << sum.getImag() << "i" << std::endl;

  return 0;
}