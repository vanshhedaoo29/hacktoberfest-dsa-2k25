// Question number 3692 Majority Frequency Characters
class Solution {
public:
    string majorityFrequencyGroup(string s) {
        // Step 1: Count the frequency of each character in the string
        unordered_map<char,int> mp;
        for(auto &i:s) mp[i]++;
        
        // Step 2: Group characters by their frequency
        // Key = frequency, Value = string of characters having that frequency
        unordered_map<int,string> mp2;
        for(auto &i:mp){
            mp2[i.second].push_back(i.first);
        }

        // Step 3: Find the maximum group size (i.e., most characters sharing the same frequency)
        int maxi=0;
        for(auto &i:mp2){
            maxi=max(maxi,(int)i.second.size());
        }

        // Step 4: From the largest groups, choose the one with the highest frequency value
        string ans="";
        int prev=-1; // stores the best frequency seen so far
        for(auto &i:mp2){
            if(i.second.size()==maxi){ // check only groups of maximum size
                if(prev<i.first){      // prefer the group with larger frequency
                    ans=i.second;
                    prev=i.first;
                }
            }
        }
        
        // Step 5: Return the group of characters (string) satisfying conditions
        return ans;
    }
};
