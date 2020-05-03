package likou.top.interview.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author infear
 */
public class StringSolution {
    public static void main(String args[]) {
        new StringSolution().lengthOfLongestSubstring("12312322");
    }

    /* 验证回文串
     给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。

     说明：本题中，我们将空字符串定义为有效的回文串。

     示例 1:

     输入: "A man, a plan, a canal: Panama"
     输出: true
     示例 2:

     输入: "race a car"
     输出: false*/
    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        int i = 0, j = s.length() - 1;
        s = s.toLowerCase();
        while (i < j) {
            while (i != s.length() && !Character.isAlphabetic(s.charAt(i)) && !Character.isDigit(s.charAt(i))) {
                i++;
            }
            if (i == s.length()) {
                return true;
            }
            while (j != -1 && !Character.isAlphabetic(s.charAt(j)) && !Character.isDigit(s.charAt(j))) {
                j--;
            }
            if (j == -1) {
                return true;
            }
            if (i < j) {
                if (s.charAt(i) == s.charAt(j)) {
                    i++;
                    j--;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /*    编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
        不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
        你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。

        示例 1：

        输入：["h","e","l","l","o"]
        输出：["o","l","l","e","h"]
        示例 2：

        输入：["H","a","n","n","a","h"]
        输出：["h","a","n","n","a","H"]*/
    public void reverseString(char[] s) {
        if (s == null || s.length == 0) {
            return;
        }
        int i = 0, j = s.length - 1;
        char tmp;
        while (i < j) {
            tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
            i++;
            j--;
        }
    }

    /* 字符串中的第一个唯一字符
     给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。

     案例:

     s = "leetcode"
     返回 0.

     s = "loveleetcode",
     返回 2.*/
    public int firstUniqChar(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        // use linked hashmap to reserve order
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i != s.length(); i++) {
            char c = s.charAt(i);
            if (!set.contains((int) c)) {
                map.put((int) c, i);
                set.add((int) c);
            } else {
                map.remove((int) c);
            }
        }
        if (map.isEmpty()) {
            return -1;
        }
        return map.entrySet().iterator().next().getValue();
    }

    /*   分割回文串
       给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。

       返回 s 所有可能的分割方案。

       示例:

       输入: "aab"
       输出:
               [
               ["aa","b"],
               ["a","a","b"]
               ]*/
    public List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return new LinkedList<>();
        }
        List<List<String>> result = new LinkedList<>();
        for (int i = 1; i <= s.length(); i++) {
            if (isReverse(s.substring(0, i))) {
                List<List<String>> subs = partition(s.substring(i));
                if (subs.isEmpty()) {
                    List<String> tmp = new LinkedList<>();
                    tmp.add(s.substring(0, i));
                    result.add(tmp);
                } else {
                    for (List<String> sub : subs) {
                        sub.add(0, s.substring(0, i));
                        result.add(sub);
                    }
                }
            }
        }
        return result;
    }

    private boolean isReverse(String substring) {
        if (substring.length() == 0) {
            return false;
        }
        int i = 0, j = substring.length() - 1;
        while (i < j) {
            if (substring.charAt(i) != substring.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    /* 单词拆分
     给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。

     说明：

     拆分时可以重复使用字典中的单词。
     你可以假设字典中没有重复的单词。
     示例 1：

     输入: s = "leetcode", wordDict = ["leet", "code"]
     输出: true
     解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
     示例 2：

     输入: s = "applepenapple", wordDict = ["apple", "pen"]
     输出: true
     解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
     注意你可以重复使用字典中的单词。
     示例 3：

     输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
     输出: false*/
    public boolean wordBreak(String s, List<String> wordDict) {
        int l = s.length();
        boolean[] isMatch = new boolean[l + 1];
        isMatch[l] = true;
        Set<String> dict = new HashSet<>(wordDict);
        for (int j = l - 1; j >= 0; j--) {
            for (int k = j + 1; k <= l; k++) {
                if (!isMatch[k]) {
                    continue;
                }
                if (dict.contains(s.substring(j, k))) {
                    isMatch[j] = true;
                    break;
                }
            }
        }
        return isMatch[0];
    }

    /*单词拆分 II
    给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，在字符串中增加空格来构建一个句子，使得句子中所有的单词都在词典中。返回所有这些可能的句子。
    说明：

    分隔时可以重复使用字典中的单词。
    你可以假设字典中没有重复的单词。
    示例 1：

    输入:
    s = "catsanddog"
    wordDict = ["cat", "cats", "and", "sand", "dog"]
    输出:
            [
            "cats and dog",
            "cat sand dog"
            ]
    示例 2：

    输入:
    s = "pineapplepenapple"
    wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
    输出:
            [
            "pine apple pen apple",
            "pineapple pen apple",
            "pine applepen apple"
            ]
    解释: 注意你可以重复使用字典中的单词。
    示例 3：

    输入:
    s = "catsandog"
    wordDict = ["cats", "dog", "sand", "and", "cat"]
    输出:
            []*/
    public List<String> wordBreak2(String s, List<String> wordDict) {
        int l = s.length();
        List<List<Integer>>[] r = new List[l + 1];
        // use index instead of string
        // List<String>[] r = new List[l + 1];
        r[l] = new LinkedList<>();
        Set<String> dict = new HashSet<>(wordDict);
        for (int outIndex = l - 1; outIndex >= 0; outIndex--) {
            List<List<Integer>> tmp = new LinkedList<>();
            for (int innerIndex = outIndex + 1; innerIndex <= l; innerIndex++) {
                String sub = s.substring(outIndex, innerIndex);
                if (dict.contains(sub) && r[innerIndex] != null) {
                    if (r[innerIndex].isEmpty()) {
                        tmp.add(Arrays.asList(outIndex, innerIndex));
                    } else {
                        for (List<Integer> indexes : r[innerIndex]) {
                            List copy = new LinkedList(indexes);
                            copy.add(0, innerIndex);
                            copy.add(0, outIndex);
                            tmp.add(copy);
                        }
                    }
                }
            }
            if (tmp.isEmpty()) {
                r[outIndex] = null;
            } else {
                r[outIndex] = tmp;
            }
        }
        List<String> result = new LinkedList<>();
        if (r[0] == null) {
            return result;
        }
        for (List<Integer> indexes : r[0]) {
            int i = 0;
            StringBuilder builder = new StringBuilder(s.length());
            while (i != indexes.size()) {
                builder.append(s, indexes.get(i), indexes.get(i + 1)).append(" ");
                i += 2;
            }
            builder.deleteCharAt(builder.length() - 1);
            result.add(builder.toString());
        }
        return result;
    }

    /*
        有效的字母异位词
        给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。

        示例 1:

        输入: s = "anagram", t = "nagaram"
        输出: true
        示例 2:

        输入: s = "rat", t = "car"
        输出: false
        说明:
        你可以假设字符串只包含小写字母。

        进阶:
        如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？*/
    public boolean isAnagram(String s, String t) {
        HashMap<Integer, Integer> stats1 = new HashMap<>();
        HashMap<Integer, Integer> stats2 = new HashMap<>();
        for (char c : s.toCharArray()) {
            stats1.putIfAbsent((int) c, 0);
            stats1.put((int) c, stats1.get((int) c) + 1);
        }
        for (char c : t.toCharArray()) {
            stats2.putIfAbsent((int) c, 0);
            stats2.put((int) c, stats2.get((int) c) + 1);
        }

        for (Integer key : stats1.keySet()) {
            if (!stats1.get(key).equals(stats2.getOrDefault(key, null))) {
                return false;
            }
            stats2.remove(key);
        }
        if (stats2.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<String> findWords2(char[][] board, String[] words) {
        List<String> result = new LinkedList<>();
        HashMap<Integer, Set<Coodinate>> pos = new HashMap();
        for (String word : words) {
            boolean isFind = false;
            for (int i = 0; i != board.length; i++) {
                for (int j = 0; j != board[0].length; j++) {
                    if (find(word, 0, board, new Coodinate(i, j), new HashSet<>())) {
                        result.add(word);
                        isFind = true;
                        break;
                    }
                }
                if (isFind) {
                    break;
                }
            }
        }
        return result;
    }

    private boolean find(String word, int i, char board[][], Coodinate c, HashSet path) {
        if (i == word.length()) {
            return true;
        }
        if (c.getX() < 0 || c.getX() >= board.length || c.getY() < 0 || c.getY() >= board[0].length) {
            return false;
        }
        char start = word.charAt(i);
        if (board[c.getX()][c.getY()] != start) {
            return false;
        }
        path.add(c);
        if (!path.contains(new Coodinate(c.getX(), c.getY() - 1))) {
            boolean left = find(word, i + 1, board, new Coodinate(c.getX(), c.getY() - 1), path);
            if (left) {
                return true;
            }
        }

        if (!path.contains(new Coodinate(c.getX() - 1, c.getY()))) {
            boolean up = find(word, i + 1, board, new Coodinate(c.getX() - 1, c.getY()), path);
            if (up) {
                return true;
            }
        }

        if (!path.contains(new Coodinate(c.getX(), c.getY() + 1))) {
            boolean right = find(word, i + 1, board, new Coodinate(c.getX(), c.getY() + 1), path);
            if (right) {
                return true;
            }
        }

        if (!path.contains(new Coodinate(c.getX() + 1, c.getY()))) {
            boolean down = find(word, i + 1, board, new Coodinate(c.getX() + 1, c.getY()), path);
            if (down) {
                return true;
            }
        }
        path.remove(c);
        return false;
    }

    public List<String> findWords(char[][] board, String[] words) {
        Set<String> result = new HashSet<>();
        Trie root = new Trie();
        for (String word : words) {
            root.insert(word);
        }
        boolean path[][] = new boolean[board.length][board[0].length];
        for (int i = 0; i != board.length; i++) {
            for (int j = 0; j != board[0].length; j++) {
                search(board, i, j, root, result, path);
            }
        }
        return new LinkedList<>(result);
    }

    private void search(char[][] board, int i, int j, Trie root, Set<String> result, boolean[][] path) {
        char c = board[i][j];
        if (!root.startsWith(String.valueOf(c))) {
            return;
        }
        path[i][j] = true;
        Trie sub = root.getSourceByValue((int) c);
        if (sub.hasEnd()) {
            result.add(sub.getStr());
        }
        if (i + 1 < board.length && !path[i + 1][j]) {
            search(board, i + 1, j, sub, result, path);
        }
        if (i - 1 >= 0 && !path[i - 1][j]) {
            search(board, i - 1, j, sub, result, path);
        }
        if (j + 1 < board[0].length && !path[i][j + 1]) {
            search(board, i, j + 1, sub, result, path);
        }
        if (j - 1 >= 0 && !path[i][j - 1]) {
            search(board, i, j - 1, sub, result, path);
        }
        path[i][j] = false;
    }

    /*    字母异位词分组
        给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。

        示例:

        输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
        输出:
                [
                ["ate","eat","tea"],
                ["nat","tan"],
                ["bat"]
                ]
        说明：

        所有输入均为小写字母。
        不考虑答案输出的顺序。*/
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> m = new HashMap<>();
        for (String s : strs) {
            char arr[] = s.toCharArray();
            Arrays.sort(arr);
            String sorted = new String(arr);
            m.putIfAbsent(sorted, new LinkedList<>());
            m.get(sorted).add(s);
        }
        List<List<String>> r = new LinkedList<>();
        r.addAll(m.values());
        return r;
    }

    /*    无重复字符的最长子串
        给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

        示例 1:

        输入: "abcabcbb"
        输出: 3
        解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
        示例 2:

        输入: "bbbbb"
        输出: 1
        解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
        示例 3:

        输入: "pwwkew"
        输出: 3
        解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
        请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。*/
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int max = 0, start = -1;
        char[] array = s.toCharArray();
        HashMap<Integer, Integer> charToPos = new HashMap<>();
        for (int index = 0; index != array.length; index++) {
            int key = array[index];
            // key first appears or the last appear post is before start, which should be overwrite.
            if (!charToPos.containsKey(key) || charToPos.get(key) <= start) {
                charToPos.put(key, index);
                max = Math.max(index - start, max);
            } else {
                int pos = charToPos.get(key);
                // remove all pair before pos and recount from new start.
                start = pos;
                charToPos.put(key, index);
            }
        }
        return max;
    }

    /*    三数之和
        给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。

        注意：答案中不可以包含重复的三元组。



        示例：

        给定数组 nums = [-1, 0, 1, 2, -1, -4]，

        满足要求的三元组集合为：
                [
                [-1, 0, 1],
                [-1, -1, 2]
                ]*/
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length <= 2) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        HashMap<Integer, Integer> pos = new HashMap<>();
        // record each element last position(nearest to array end)
        for (int i = 0; i != nums.length; i++) {
            pos.put(nums[i], i);
        }
        List<List<Integer>> r = new ArrayList();
        int lastFirst = nums[0] - 1, lastSecond = nums[0] - 1;
        for (int i = 0; i != nums.length; i++) {
            if (lastFirst == nums[i]) {
                continue;
            }
            lastFirst = nums[i];
            for (int j = i + 1; j != nums.length; j++) {
                if (nums[j] == lastSecond) {
                    continue;
                }
                lastSecond = nums[j];
                int val = nums[i] + nums[j];
                if (!pos.containsKey(-val) || pos.get(-val) <= j) {
                    continue;
                }
                r.add(Arrays.asList(nums[i], nums[j], -val));
            }
        }
        return r;
    }

/*    矩阵置零
    给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法。

    示例 1:

    输入:
            [
            [1,1,1],
            [1,0,1],
            [1,1,1]
            ]
    输出:
            [
            [1,0,1],
            [0,0,0],
            [1,0,1]
            ]
    示例 2:

    输入:
            [
            [0,1,2,0],
            [3,4,5,2],
            [1,3,1,5]
            ]
    输出:
            [
            [0,0,0,0],
            [0,4,5,0],
            [0,3,1,0]
            ]
    进阶:

    一个直接的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
    一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
    你能想出一个常数空间的解决方案吗？*/

    public void setZeroes(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        List<Integer> pos = new ArrayList<>();
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    pos.add(i);
                    pos.add(j);
                }
            }
        }
        int i = 0;
        while (i != pos.size()) {
            int row = pos.get(i);
            i++;
            int col = pos.get(i);
            i++;
            for (int j = 0; j != matrix[row].length; j++) {
                matrix[row][j] = 0;
            }
            for (int j = 0; j != matrix.length; j++) {
                matrix[j][col] = 0;
            }
        }
    }

    class Coodinate {
        int x;
        int y;

        public Coodinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coodinate coodinate = (Coodinate) o;
            return x == coodinate.x &&
                    y == coodinate.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
