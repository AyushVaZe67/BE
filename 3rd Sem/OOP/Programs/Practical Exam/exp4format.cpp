#include <fstream>
#include <iostream>

using namespace std;

int main() {
  fstream file;
  file.open("sample.txt", ios::out);
  if (!file) {
    cout << "Error in creating file!!!" << endl;
    return 0;
  }
  cout << "File created successfully." << endl;
  file << "MKC.";
  file.close();

  file.open("sample.txt", ios::in);
  if (!file) {
    cout << "Error in opening file!!!" << endl;
    return 0;
  }

  char ch;
  cout << "File content: ";
  while (!file.eof()) {
    file >> ch;
    cout << ch;
  }
  file.close();
  return 0;
}
