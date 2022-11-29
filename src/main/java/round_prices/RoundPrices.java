package round_prices;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

public class RoundPrices {
    /*
        Round Prices
        AirBnB Interview Question


        https://leetcode.com/discuss/interview-question/algorithms/125001/airbnb-phone-screen-minimize-rounding-error-to-meet-target/123806

        input: list of floating points X
        output: list of int Y
        But they need to satisfy the condition:

        sum(Y) = round(sum(x))
        minmize (|y1-x1| + |y2-x2| + ... + |yn-xn|)

     */
    public class Solution {
        public int[] roundUp(double[] arr) {
            int n = arr.length;
            NumWithDiff[] arrWithDiff = new NumWithDiff[n];
            double sum = 0.0;
            int floorSum = 0;
            for (int i = 0; i < n; i++) {
                int floor = (int) arr[i];
                int ceil = floor;
                if (floor < arr[i]) ceil++;
                floorSum += floor;
                sum += arr[i];
                arrWithDiff[i] = new NumWithDiff(ceil, ceil - arr[i]);
            }

            int num = (int) Math.round(sum);
            int diff = num - floorSum;
            Arrays.sort(arrWithDiff, new Comparator<NumWithDiff>() {
                @Override
                public int compare(NumWithDiff n1, NumWithDiff n2) {
                    return Double.compare(n2.diffWithCeil, n1.diffWithCeil) * (-1);
                }
            });
            // Arrays.sort(arrWithDiff, (a, b) -> (Double.compare(b.diffWithCeil, a.diffWithCeil)));

            int[] res = new int[n];
            int i = 0;
            for (; i < diff; i++) {
                res[i] = arrWithDiff[i].num; // 这些放ceil
            }
            for (; i < n; i++) {
                res[i] = arrWithDiff[i].num - 1; // 剩下的只放floor
            }

            System.out.println("sol1" + Arrays.toString(res));
            return res;
        }

        class NumWithDiff {
            int num;
            double diffWithCeil;

            public NumWithDiff(int n, double c) {
                this.num = n;
                this.diffWithCeil = c;
            }
        }
    }

    /*
        Round Prices
        AirBnB Interview Question
     */
    public class Solution_2 {
        public int[] roundUp(double[] prices) {
            if (prices == null || prices.length == 0) {
                return new int[0];
            }

            int[] res = new int[prices.length];

            double sum = 0;
            int roundSum = 0;
            Number[] numbers = new Number[prices.length];
            for (int i = 0; i < prices.length; i++) {
                numbers[i] = new Number(prices[i], i);
                sum += prices[i];
                roundSum += (int) Math.round(prices[i]);
                res[i] = (int) Math.round(prices[i]);
            }
            int sumRound = (int) Math.round(sum);

            if (sumRound == roundSum) {
                return res;
            } else if (sumRound > roundSum) {
                Arrays.sort(numbers, (a, b) -> (Double.compare(b.frac, a.frac)));
                int count = sumRound - roundSum;
                for (int i = 0; i < prices.length; i++) {
                    Number num = numbers[i];
                    if (num.frac < 0.5 && count > 0) {
                        res[num.index] = (int) Math.ceil(num.val);
                        count--;
                    } else {
                        res[num.index] = (int) Math.round(num.val);
                    }
                }
            } else {
                Arrays.sort(numbers, (a, b) -> (Double.compare(a.frac, b.frac)));
                int count = roundSum - sumRound;
                for (int i = 0; i < prices.length; i++) {
                    Number num = numbers[i];
                    if (num.frac >= 0.5 && count > 0) {
                        res[num.index] = (int) Math.floor(num.val);
                        count--;
                    } else {
                        res[num.index] = (int) Math.round(num.val);
                    }
                }
            }

            return res;
        }

        class Number {
            double val;
            double frac;
            int index;

            Number(double val, int index) {
                this.val = val;
                this.frac = val - Math.floor(val);
                this.index = index;
            }
        }
    }


    public class Solution3 {
        class PriceDiff{
            int idx;
            double diff;

            public PriceDiff(int idx, double diff){
                this.idx = idx;
                this.diff = diff;
            }
        }

        /**
         * 先将所有floor(x)加起来􃔏􄇑出如果所有都floor 的􄈍􄘈差多少，按照ceil 以后需要加的价格排
         * 序，贪心取最小的即可。
         * @param prices
         * @return
         */
        public int[] roundUp(double[] prices){
            double doubleSum = 0.0;
            int intSum = 0;// Math.floor value of

            List<PriceDiff> list = new ArrayList<>();
            //Compute the total sums in double, integer sum with floor values
            //And Pair object to record index and prices[i] difference between floor and raw value
            for(int i = 0; i < prices.length;i++){
                double val = prices[i];
                doubleSum += val;
                intSum += Math.floor(val);
                PriceDiff p = new PriceDiff(i, val - Math.floor(val));
                list.add(p);
            }

            //Count the total count of numbers need to round up to make up the deficit
            // from rounding double sum to intSum
            int totalRoundup = (int)Math.round(doubleSum) - intSum;

            //reverse order of list from big to small
            //based on the
            Collections.sort(list,  new Comparator<PriceDiff>(){
                @Override
                public int compare(PriceDiff p1, PriceDiff p2){
                    return Double.compare(p1.diff, p2.diff) * (-1);
                }
            });

            int[] res = new int[prices.length];

            //Start with the largest deficit
            //These numbers (on indices from  the PriceDiff objecdt) need to take ceiling to make up for the round loss
            for(int i = 0; i < totalRoundup;i++){
                PriceDiff p  = list.get(i);
                res[p.idx] = (int) Math.ceil(prices[p.idx]);
            }

            //Rest numbers will round to floor
            for(int i = totalRoundup;i < prices.length;i++){
                PriceDiff p = list.get(i);
                res[p.idx] = (int) Math.floor(prices[p.idx]);

            }
            return res;
        }
    }
    public static class UnitTest {
        @Test
        public void test1() {
            Solution sol = new RoundPrices().new Solution();
            double[] arr = {1.2, 3.7, 2.3, 4.8};
            int[] res = sol.roundUp(arr);
            assertEquals(5, res[0]);
            assertEquals(4, res[1]);
            assertEquals(2, res[2]);
            assertEquals(1, res[3]);

            arr = new double[]{1.2, 2.3, 3.4};
            res = sol.roundUp(arr);
            System.out.println(res);
            assertEquals(4, res[0]);
            assertEquals(2, res[1]);
            assertEquals(1, res[2]);

            arr = new double[]{1.2, 3.7, 2.3, 4.8};
            res = sol.roundUp(arr);
            assertEquals(5, res[0]);
            assertEquals(4, res[1]);
            assertEquals(2, res[2]);
            assertEquals(1, res[3]);

            arr = new double[]{1.2, 2.5, 3.6, 4.0};
            res = sol.roundUp(arr);
            assertEquals(4, res[0]);
            assertEquals(3, res[1]);
            assertEquals(2, res[2]);
            assertEquals(1, res[3]);
        }

        @Test
        public void test2() {
            Solution_2 sol = new RoundPrices().new Solution_2();
            double[] arr = {1.2, 2.3, 3.4};
            int[] res = sol.roundUp(arr);
            System.out.println(res);
            assertEquals(1, res[0]);
            assertEquals(2, res[1]);
            assertEquals(4, res[2]);

            arr = new double[]{1.2, 3.7, 2.3, 4.8};
            res = sol.roundUp(arr);
            assertEquals(1, res[0]);
            assertEquals(4, res[1]);
            assertEquals(2, res[2]);
            assertEquals(5, res[3]);

            arr = new double[]{1.2, 2.5, 3.6, 4.0};
            res = sol.roundUp(arr);
            assertEquals(1, res[0]);
            assertEquals(2, res[1]);
            assertEquals(4, res[2]);
            assertEquals(4, res[3]);

            arr = new double[]{2.5, 2.3, 3.1, 6.5};
            res = sol.roundUp(arr);
            assertEquals(2, res[0]);
            assertEquals(2, res[1]);
            assertEquals(3, res[2]);
            assertEquals(7, res[3]);

            arr = new double[]{2.9, 2.3, 1.4, 3, 6};
            res = sol.roundUp(arr);
            assertEquals(3, res[0]);
            assertEquals(2, res[1]);
            assertEquals(2, res[2]);
            assertEquals(3, res[3]);
            assertEquals(6, res[4]);

            arr = new double[]{-0.4, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3};
            res = sol.roundUp(arr);
            assertEquals(0, res[0]);
            assertEquals(2, res[1]);
            assertEquals(2, res[2]);
            assertEquals(2, res[3]);
            assertEquals(1, res[4]);
            assertEquals(1, res[5]);
            assertEquals(1, res[6]);
            assertEquals(1, res[7]);
            assertEquals(1, res[8]);
            assertEquals(1, res[9]);
            assertEquals(1, res[10]);
        }


        @Test
        public void test3() {
            Solution3 sol = new RoundPrices().new Solution3();
            double[] arr = {1.2, 2.3, 3.4};
            int[] res = sol.roundUp(arr);
            System.out.println(res);
            assertEquals(1, res[0]);
            assertEquals(2, res[1]);
            assertEquals(4, res[2]);

            arr = new double[]{1.2, 3.7, 2.3, 4.8};
            res = sol.roundUp(arr);
            assertEquals(1, res[0]);
            assertEquals(4, res[1]);
            assertEquals(2, res[2]);
            assertEquals(5, res[3]);

            arr = new double[]{1.2, 2.5, 3.6, 4.0};
            res = sol.roundUp(arr);
            assertEquals(1, res[0]);
            assertEquals(2, res[1]);
            assertEquals(4, res[2]);
            assertEquals(4, res[3]);

            arr = new double[]{2.5, 2.3, 3.1, 6.5};
            res = sol.roundUp(arr);
            assertEquals(2, res[0]);
            assertEquals(2, res[1]);
            assertEquals(3, res[2]);
            assertEquals(7, res[3]);

            arr = new double[]{2.9, 2.3, 1.4, 3, 6};
            res = sol.roundUp(arr);
            assertEquals(3, res[0]);
            assertEquals(2, res[1]);
            assertEquals(2, res[2]);
            assertEquals(3, res[3]);
            assertEquals(6, res[4]);

            arr = new double[]{-0.4, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3, 1.3};
            res = sol.roundUp(arr);
            assertEquals(0, res[0]);
            assertEquals(2, res[1]);
            assertEquals(2, res[2]);
            assertEquals(2, res[3]);
            assertEquals(1, res[4]);
            assertEquals(1, res[5]);
            assertEquals(1, res[6]);
            assertEquals(1, res[7]);
            assertEquals(1, res[8]);
            assertEquals(1, res[9]);
            assertEquals(1, res[10]);
        }
    }
}

