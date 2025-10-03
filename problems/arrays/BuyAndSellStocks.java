class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int buyPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        int profit = 0;
        for(int i=0; i<n; i++){
            if(buyPrice<prices[i]){
                profit = prices[i] - buyPrice;
                maxProfit = Math.max(maxProfit,profit);
            }else{
                buyPrice = prices[i];
            }  
        }
        return maxProfit;
    }
}
// Time Complexity = O(n)