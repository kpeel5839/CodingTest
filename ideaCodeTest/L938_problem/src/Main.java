import java.util.*;
import java.io.*;
class Solution {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    public static int sum = 0;
    public int dfs(TreeNode node , int low ,int high){
        if(node.left != null){
            dfs(node.left , low , high);
        }
        if(node.right != null){
            dfs(node.right , low , high);
        }
        if(low <= node.val && node.val <= high){
            sum += node.val;
        }
        return sum;
    }
    public int rangeSumBST(TreeNode root, int low, int high) {
        sum = dfs(root , low , high);
        return sum;
    }
}
