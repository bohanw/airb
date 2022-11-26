package travel_buddy;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

public class TravelBuddy {
    /*
        Travel Buddy
        AirBnB Interview Question

        followup是给了一个max值，找出你的buddy的wishlist里不在你的wishlist里的最多max个城市，根据buddy和你的重合程度来排序
例如 你的wishlist是 a,b,c,d
buddy1 的wishlist 是 a,b,e,f, 有两个和你的一样，所以是你的buddy
buddy2 的wishlist 是 a,c,d,g, 有三个和你的一样，也是你的budy
问题是输出一个size最多为max的推荐城市列表
     */
    public class Solution {
        private List<Buddy> buddies;
        private Set<String> myWishList;

        class Buddy implements Comparable<Buddy> {
            String name;
            int similarity;
            Set<String> wishList;

            Buddy(String name, int similarity, Set<String> wishList) {
                this.name = name;
                this.similarity = similarity;
                this.wishList = wishList;
            }

            @Override
            public int compareTo(Buddy that) {
                return that.similarity - this.similarity;
            }
        }

        /**
         *
         * @param myWishList : List of locations
         * @param friendsWishList hashmap: key is friend name, value is set of strings of the friend's wishlist
         */


        /*
        我的解法就是把自己的城市放在hashset 里面，遍􀦶一遍所有朋友，如果相似度大于50%，
        个朋友已􃓿他的相似度，最后sort according to 相似度。
         */
        public Solution(Set<String> myWishList, Map<String, Set<String>> friendsWishList) {
            this.buddies = new ArrayList<>();
            this.myWishList = myWishList;
            //Traverse all friends, and get list of locations from friendsWishList map
            //From the friends list get intersection of locations with myWishList
            //If overlap with more than 50% than call a buddy
            //Note implements a Buddy class and customer comparator sorted by similarity
            for (String name : friendsWishList.keySet()) {
                Set<String> wishList = friendsWishList.get(name);
                Set<String> intersection = new HashSet<>(wishList);
                intersection.retainAll(myWishList);
                int similarity = intersection.size();
                if (similarity >= wishList.size() / 2) {
                    buddies.add(new Buddy(name, similarity, wishList));
                }
            }
        }

        public List<Buddy> getSortedBuddies() {
            Collections.sort(buddies);
            List<Buddy> res = new ArrayList<>(buddies);
            return res;
        }

        //Follow up:
        //
        public List<String> recommendCities(int k) {
            List<String> res = new ArrayList<>();
            List<Buddy> buddies = getSortedBuddies();

            int i = 0;
            while (k > 0 && i < buddies.size()) {
                Set<String> diff = new HashSet<>(buddies.get(i).wishList);
                diff.removeAll(myWishList);
                if (diff.size() <= k) {
                    res.addAll(diff);
                    k -= diff.size();
                    i++;
                } else {
                    Iterator<String> it = diff.iterator();
                    while (k > 0) {
                        res.add(it.next());
                        k--;
                    }
                }
            }
            return res;
        }
    }

    public static class UnitTest {
        @Test
        public void test1() {
            Set<String> myWishList = new HashSet<>(Arrays.asList(new String[]{"a", "b", "c", "d"}));
            Set<String> wishList1 = new HashSet<>(Arrays.asList(new String[]{"a", "b", "e", "f"}));
            Set<String> wishList2 = new HashSet<>(Arrays.asList(new String[]{"a", "c", "d", "g"}));
            Set<String> wishList3 = new HashSet<>(Arrays.asList(new String[]{"c", "f", "e", "g"}));
            Map<String, Set<String>> friendWishLists = new HashMap<>();
            friendWishLists.put("Buddy1", wishList1);
            friendWishLists.put("Buddy2", wishList2);
            friendWishLists.put("Buddy3", wishList3);
            Solution sol = new TravelBuddy().new Solution(myWishList, friendWishLists);
            List<String> res = sol.recommendCities(10);
            assertEquals(3, res.size());
            assertEquals("g", res.get(0));
            assertEquals("e", res.get(1));
            assertEquals("f", res.get(2));
        }
    }
}

