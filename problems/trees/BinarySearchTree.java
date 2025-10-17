// Binary Search Tree Implementation
// Time Complexities:
// - Search: O(log n) average, O(n) worst
// - Insert: O(log n) average, O(n) worst  
// - Delete: O(log n) average, O(n) worst

class Node {
    int data;
    Node left;
    Node right;
    
    Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BinarySearchTree {
    Node root;
    
    public BinarySearchTree() {
        root = null;
    }
    
    /**
     * Insert a new key into the BST
     * @param data the value to insert
     */
    public void insert(int data) {
        root = insertRec(root, data);
    }
    
    private Node insertRec(Node root, int data) {
        // If tree is empty or we've reached a leaf node
        if (root == null) {
            root = new Node(data);
            return root;
        }
        
        // Recursively traverse the tree
        if (data < root.data)
            root.left = insertRec(root.left, data);
        else if (data > root.data)
            root.right = insertRec(root.right, data);
            
        return root;
    }
    
    /**
     * Search for a key in the BST
     * @param data the value to search for
     * @return true if found, false otherwise
     */
    public boolean search(int data) {
        return searchRec(root, data);
    }
    
    private boolean searchRec(Node root, int data) {
        // Base cases: root is null or key is present at root
        if (root == null || root.data == data)
            return root != null;
            
        // Value is greater than root's data
        if (data > root.data)
            return searchRec(root.right, data);
            
        // Value is less than root's data
        return searchRec(root.left, data);
    }
    
    /**
     * Delete a key from the BST
     * @param data the value to delete
     */
    public void delete(int data) {
        root = deleteRec(root, data);
    }
    
    private Node deleteRec(Node root, int data) {
        // Base case: if tree is empty
        if (root == null)
            return root;
            
        // Recursively traverse the tree
        if (data < root.data)
            root.left = deleteRec(root.left, data);
        else if (data > root.data)
            root.right = deleteRec(root.right, data);
        else {
            // Case 1: Node with no or one child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
                
            // Case 2: Node with two children
            // Get the inorder successor (smallest value in right subtree)
            root.data = minValue(root.right);
            
            // Delete the inorder successor
            root.right = deleteRec(root.right, root.data);
        }
        return root;
    }
    
    private int minValue(Node root) {
        int minVal = root.data;
        while (root.left != null) {
            minVal = root.left.data;
            root = root.left;
        }
        return minVal;
    }
    
    /**
     * Print the tree in inorder traversal
     */
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }
    
    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }
    
    /**
     * Main method to test the BST implementation
     */
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        
        // Insert some values
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);
        
        System.out.println("Original BST (inorder traversal):");
        bst.inorder();  // Should print: 20 30 40 50 60 70 80
        
        // Test search
        System.out.println("Search for 40: " + bst.search(40));  // true
        System.out.println("Search for 90: " + bst.search(90));  // false
        
        // Test deletion
        System.out.println("\nAfter deleting 20:");
        bst.delete(20);
        bst.inorder();  // Should print: 30 40 50 60 70 80
        
        System.out.println("After deleting 30:");
        bst.delete(30);
        bst.inorder();  // Should print: 40 50 60 70 80
        
        System.out.println("After deleting 50 (root):");
        bst.delete(50);
        bst.inorder();  // Should print: 40 60 70 80
    }
}