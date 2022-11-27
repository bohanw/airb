package csv_parser;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

public class CSVParser {
    /*
        CSV Parser
        AirBnB Interview Question
        http://creativyst.com/Doc/Articles/CSV/CSV01.htm#EmbedBRs

         https://hellosmallworld123.wordpress.com/2015/09/04/abnb-%E9%9D%A2%E7%BB%8F%E6%80%BB%E7%BB%93/
        Input: csvformat
    John,Smith,john.smith@gmail.com,Los Angeles,1
    Jane,Roberts,janer@msn.com,"San Francisco, CA",0
    "Alexandra ""Alex""",Menendez,alex.menendez@gmail.com,Miami,1 """Alexandra Alex"""

    如果有逗号，转化成|
如果有引号，把不考虑引号里逗号，把引号里的内容去引号整体打印。
如果有两重引号，只去掉一重引号。

    Output: escaped string
    John|Smith|john.smith@gmail.com|Los Angeles|1
    Jane|Roberts|janer@msn.com|San Francisco, CA|0
    Alexandra "Alex"|Menendez|alex.menendez@gmail.com|Miami|1 "Alexandra Alex"
     */
    public class Solution {
        public String parseCSV(String str) {
            //List to store stirng components delimited by ",",
            //Will join with | in the outpu
            List<String> res = new ArrayList<>();
            boolean inQuote = false;
            //Stringbuilder to build current CSV string component
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (inQuote) {
                    //Deal with continuous quote( inside quotes)
                    if (str.charAt(i) == '\"') {
                        //For \" if the next character is also quote, and inQuote==true,
                        //Only need to remove the outermost quote
                        //So need to append current quote
                        if (i < str.length() - 1 && str.charAt(i + 1) == '\"') {
                            sb.append("\"");
                            i++;
                        //Reach the end of quote string
                        } else {
                            inQuote = false;
                        }
                    } else {
                        //Keep building the current string component
                        sb.append(str.charAt(i));
                    }
                } else {
                    //Mark the start of a quoted string component
                    if (str.charAt(i) == '\"') {
                        inQuote = true;
                    } else if (str.charAt(i) == ',') {
                        //Reach the end of  comma seperated component
                        //add stringbuilder contents to list and reset the stringbuilder
                        res.add(sb.toString());
                        sb.setLength(0);
                    } else {
                        sb.append(str.charAt(i));
                    }
                }
            }

            if (sb.length() > 0) {
                res.add(sb.toString());
            }
            //Return the result by joining res list strings wiht "|"
            return String.join("|", res);
        }
    }

    /*
        CSV Parser
        AirBnB Interview Question
        http://creativyst.com/Doc/Articles/CSV/CSV01.htm#EmbedBRs
     */
    public class Solution_2 {
        public String parseCSV(String str) {
            if (str == null || str.isEmpty()) return null;
            List<String> res = new ArrayList<>();
            StringBuilder curr = new StringBuilder();
            boolean inQuote = false;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (inQuote) {
                    if (c == '\"') {
                        if (i < str.length() - 1 && str.charAt(i + 1) == '\"') {
                            curr.append("\"");
                            i++;
                        } else {
                            inQuote = false;
                        }
                    } else {
                        curr.append(c);
                    }
                } else {
                    if (c == '\"') {
                        inQuote = true;
                    } else if (c == ',') {
                        res.add(curr.toString());
                        curr.setLength(0);
                    } else {
                        curr.append(c);
                    }
                }
            }

            if (curr.length() > 0)
                res.add(curr.toString());

            return String.join("|", res);
        }
    }

    public static class UnitTest {
        @Test
        public void test1() {
            Solution sol = new CSVParser().new Solution();
            String test = "John,Smith,john.smith@gmail.com,Los Angeles,1";
            String expected = "John|Smith|john.smith@gmail.com|Los Angeles|1";
            assertEquals(expected, sol.parseCSV(test));

            test = "Jane,Roberts,janer@msn.com,\"San Francisco, CA\",0";
            expected = "Jane|Roberts|janer@msn.com|San Francisco, CA|0";
            assertEquals(expected, sol.parseCSV(test));

            test = "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1";
            expected = "Alexandra \"Alex\"|Menendez|alex.menendez@gmail.com|Miami|1";
            assertEquals(expected, sol.parseCSV(test));

            test = "\"\"\"Alexandra Alex\"\"\"";
            expected = "\"Alexandra Alex\"";
            assertEquals(expected, sol.parseCSV(test));
        }

        @Test
        public void test2() {
            Solution_2 sol = new CSVParser().new Solution_2();
            String test = "John,Smith,john.smith@gmail.com,Los Angeles,1";
            String expected = "John|Smith|john.smith@gmail.com|Los Angeles|1";
            assertEquals(expected, sol.parseCSV(test));

            test = "Jane,Roberts,janer@msn.com,\"San Francisco, CA\",0";
            expected = "Jane|Roberts|janer@msn.com|San Francisco, CA|0";
            assertEquals(expected, sol.parseCSV(test));

            test = "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1";
            expected = "Alexandra \"Alex\"|Menendez|alex.menendez@gmail.com|Miami|1";
            assertEquals(expected, sol.parseCSV(test));

            test = "\"\"\"Alexandra Alex\"\"\"";
            expected = "\"Alexandra Alex\"";
            assertEquals(expected, sol.parseCSV(test));
        }
    }
}

