package file_system;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

public class FileSystem {
    /*
        File System
        AirBnB Interview Question



        开始的􁰦候是写两个function， create 和get
create("/a",1)
get("/a") //得到1
create("/a/b",2)
get("/a/b") //得到2
create("/c/d",1) //Error，因􀑪它的上一􃓗“/c”并不存在
get("/c") //Error,因􀑪“/c”不存在
follow up 是写一个watch 函数，比如watch("/a",new Runnable(){System.out.println("helloword");})
后，每当create("/a/b"，1) 等在/a 之下的目􁖅不􀓗生error 的􄈍，都会􁢗行􃔁在“/a”上的callback
函数
比如 watch("/a",System.out.println("yes"))
watch("/a/b",System.out.println("no"))
当create("/a/b/c",1)􁰦􀋈􀑔􀑚 callback 函数都会被触􀨁，会output yes 和no
     */
    public class Solution {
        Map<String, Integer> pathMap;
        Map<String, Runnable> callbackMap;

        public Solution() {
            this.pathMap = new HashMap<>();
            this.callbackMap = new HashMap<>();
            this.pathMap.put("", 0);
        }

        public boolean create(String path, int value) {
            //Return false if full path already exists
            if (pathMap.containsKey(path)) {
                return false;
            }


            int lastSlashIndex = path.lastIndexOf("/");
            if (!pathMap.containsKey(path.substring(0, lastSlashIndex))) {
                return false;
            }

            pathMap.put(path, value);
            return true;
        }

        public boolean set(String path, int value) {
            if (!pathMap.containsKey(path)) {
                return false;
            }

            pathMap.put(path, value);

            // Trigger callbacks
//            String curPath = path;
//            while (curPath.length() > 0) {
//                if (callbackMap.containsKey(curPath)) {
//                    callbackMap.get(curPath).run();
//                }
//                int lastSlashIndex = path.lastIndexOf("/");
//                curPath = curPath.substring(0, lastSlashIndex);
//            }

            return true;
        }

        public Integer get(String path) {
            return pathMap.get(path);
        }

        public boolean watch(String path, Runnable callback) {
            if (!pathMap.containsKey(path)) {
                return false;
            }

            callbackMap.put(path, callback);
            return true;
        }
    }

    public static class UnitTest {
        @Test
        public void test1() {
            Solution sol = new FileSystem().new Solution();
            assertTrue(sol.create("/a",1));
            assertEquals(1, (int)sol.get("/a"));
            assertTrue(sol.create("/a/b",2));
            assertEquals(2, (int)sol.get("/a/b"));
            assertTrue(sol.set("/a/b",3));
            assertEquals(3, (int)sol.get("/a/b"));
            assertFalse(sol.create("/c/d",4));
            assertFalse(sol.set("/c/d",4));

            sol = new FileSystem().new Solution();
            assertTrue(sol.create("/NA",1));
            assertTrue(sol.create("/EU",2));
            assertEquals(1, (int)sol.get("/NA"));
            assertTrue(sol.create("/NA/CA",101));
            assertEquals(101, (int)sol.get("/NA/CA"));
            assertTrue(sol.set("/NA/CA",102));
            assertEquals(102, (int)sol.get("/NA/CA"));
            assertTrue(sol.create("/NA/US",101));
            assertEquals(101, (int)sol.get("/NA/US"));
            assertFalse(sol.create("/NA/CA",101));
            assertFalse(sol.create("/SA/MX",103));
            assertFalse(sol.set("SA/MX",103));
        }
    }
}

