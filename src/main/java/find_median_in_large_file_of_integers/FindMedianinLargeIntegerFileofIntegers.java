package find_median_in_large_file_of_integers;

import org.junit.*;

import static org.junit.Assert.*;

public class FindMedianinLargeIntegerFileofIntegers {
    /*
        Find Median in Large Integer File of Integers
        AirBnB Interview Question

        Possible problem:
        Find the median of a large file with integers. CANNOT access number by index
        can only access numbers sequentially(meaning, must iterate through nums array in for loop
        one by one)
        What is k? -> Find the kth largest number
        For median, kth largest is (if odd, length / 2+  1
     */
    public class Solution {
        private long search(int[] nums, int k, long left, long right) {
            if (left >= right) {
                return left;
            }

            long res = left;
            long guess = left + (right - left) / 2;
            int count = 0;
            //From left and right boundaries, guess the median value in binary search

            //Traverse the file and count how many numbers smaller than guess
            //Update res: maximum of all numbers that are smaller than guess
            for (int num : nums) {
                if (num <= guess) {
                    count++;
                    res = Math.max(res, num);
                }
            }

            //For the chosen guess value, count how many numbers are less or equal.
            /*
            if equal then result is the answer median
            if count < k, meaning guess is too small for median, update lower bounds and search for range in greater half
            if count > k, meaning guess is too big. Update upper bound and search for left half range
             */
            if (count == k) {
                return res;
            } else if (count < k) {
                return search(nums, k, Math.max(res + 1, guess), right);
            } else {
                return search(nums, k, left, res);
            }
        }

        public double findMedian(int[] nums) {
            int len = 0;
            for (int num : nums) {
                len++;
            }
            //Disucss median in even or odd count of mumbers
            //If num size is odd. len / 2 + 1 th number is the median
            if (len % 2 == 1) {
                return (double) search(nums, len / 2 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            } else {
             // If num size is even, find the (len / 2)th largest and (len / 2 + 1)th largest, then
             //median should be the average of the two values
                return (double) (search(nums, len / 2, Integer.MIN_VALUE, Integer.MAX_VALUE) +
                        search(nums, len / 2 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE)) / 2;
            }
        }
    }

    public static class UnitTest {
        @Test
        public void test1() {
            Solution sol = new FindMedianinLargeIntegerFileofIntegers().new Solution();
            assertEquals(3.0, sol.findMedian(new int[]{3, -2, 7}), 1E-03);
            assertEquals(5.0, sol.findMedian(new int[]{-100, 99, 3, 0, 5, 7, 11, 66, -33}), 1E-03);
            assertEquals(4.5, sol.findMedian(new int[]{4, -100, 99, 3, 0, 5, 7, 11, 66, -33}), 1E-03);
        }
    }
}

