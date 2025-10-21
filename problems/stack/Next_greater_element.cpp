// Problem: Find the Next Greater Element for each array element using Stack

#include <iostream>
#include <stack>
#include <vector>
using namespace std;

vector<int> nextGreaterElement(vector<int> arr) {
    int n = arr.size();
    vector<int> result(n, -1); 
    stack<int> st; 

    for (int i = n - 1; i >= 0; i--) {
        
        while (!st.empty() && st.top() <= arr[i]) {
            st.pop();
        }

        if (!st.empty()) {
            result[i] = st.top();
        }

        st.push(arr[i]);
    }

    return result;
}

int main() {
    int n;
    cout << "Enter number of elements: ";
    cin >> n;

    vector<int> arr(n);
    cout << "Enter " << n << " elements: ";
    for (int i = 0; i < n; i++) {
        cin >> arr[i];
    }

    vector<int> res = nextGreaterElement(arr);

    cout << "\nNext Greater Elements: ";
    for (int x : res) {
        cout << x << " ";
    }
    cout << endl;

    return 0;
}
