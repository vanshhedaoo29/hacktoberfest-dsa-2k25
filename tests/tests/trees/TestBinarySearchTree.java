package tests.trees;

import problems.trees.BinarySearchTree;

public class TestBinarySearchTree {
    public static void main(String[] args) {
        // Create a new BST
        BinarySearchTree bst = new BinarySearchTree();
        
        // Test 1: Insert and Inorder traversal
        System.out.println("Test 1: Insert and Inorder traversal");
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        System.out.print("Expected: 20 30 40 50 70\nActual:   ");
        bst.inorder();
        
        // Test 2: Search functionality
        System.out.println("\nTest 2: Search functionality");
        System.out.println("Search 30 (should be true): " + bst.search(30));
        System.out.println("Search 100 (should be false): " + bst.search(100));
        
        // Test 3: Delete leaf node
        System.out.println("\nTest 3: Delete leaf node (20)");
        bst.delete(20);
        System.out.print("Expected: 30 40 50 70\nActual:   ");
        bst.inorder();
        
        // Test 4: Delete node with one child
        System.out.println("\nTest 4: Delete node with one child (30)");
        bst.delete(30);
        System.out.print("Expected: 40 50 70\nActual:   ");
        bst.inorder();
        
        // Test 5: Delete node with two children
        System.out.println("\nTest 5: Delete node with two children (50)");
        bst.delete(50);
        System.out.print("Expected: 40 70\nActual:   ");
        bst.inorder();
    }
}