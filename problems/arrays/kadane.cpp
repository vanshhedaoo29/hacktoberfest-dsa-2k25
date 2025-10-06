#include <bits/stdc++.h>
using namespace std;

static long long maximumSubarraySum(const vector<long long>& numbers) {
    if (numbers.empty()) return 0; // Define as 0 for empty input

    long long bestEndingHere = numbers[0];
    long long bestSoFar = numbers[0];

    for (size_t i = 1; i < numbers.size(); ++i) {
        const long long current = numbers[i];
        bestEndingHere = max(current, bestEndingHere + current);
        bestSoFar = max(bestSoFar, bestEndingHere);
    }

    return bestSoFar;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    // Input format:
    // n
    // a1 a2 a3 ... an
    // Example:
    // 8
    // -2 -3 4 -1 -2 1 5 -3

    int n;
    if (!(cin >> n)) {
        return 0;
    }

    vector<long long> numbers;
    numbers.reserve(max(0, n));
    for (int i = 0; i < n; ++i) {
        long long x; cin >> x; numbers.push_back(x);
    }

    cout << maximumSubarraySum(numbers) << "\n";
    return 0;
}


