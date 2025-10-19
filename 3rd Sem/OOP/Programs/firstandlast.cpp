#include <iostream>

using namespace std;

int main() {
    int numerator, denominator, result;

    cout << "Enter numerator: ";
    cin >> numerator;
    cout << "Enter denominator: ";
    cin >> denominator;

    try {
        if (denominator == 0) {
            throw runtime_error("Division by zero!");  // Throw an exception
        }

        result = numerator / denominator;
        cout << "Result: " << result << endl;
    } catch (const runtime_error& e) {
        cerr << "Error: " << e.what() << endl;  // Catch and print error message
    }

    return 0;
}
