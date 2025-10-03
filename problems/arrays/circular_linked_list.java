class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class CircularLinkedList {
    private Node last;  // pointer to the last node

    public CircularLinkedList() {
        last = null;
    }

    // Insert at the end
    public void insert(int value) {
        Node newNode = new Node(value);

        if (last == null) {
            last = newNode;
            last.next = last; // points to itself
        } else {
            newNode.next = last.next; // new node points to head
            last.next = newNode;      // last points to new node
            last = newNode;           // update last
        }
    }

    // Delete a node
    public void remove(int value) {
        if (last == null) {
            System.out.println("List is empty!");
            return;
        }

        Node curr = last.next;
        Node prev = last;

        do {
            if (curr.data == value) {
                if (curr == last && curr.next == last) {
                    // Only one node
                    last = null;
                } else {
                    prev.next = curr.next;
                    if (curr == last) {
                        last = prev; // update last if deleting last node
                    }
                }
                System.out.println("Deleted " + value);
                return;
            }
            prev = curr;
            curr = curr.next;
        } while (curr != last.next);

        System.out.println("Value " + value + " not found.");
    }

    // Display the list
    public void display() {
        if (last == null) {
            System.out.println("List is empty!");
            return;
        }

        Node temp = last.next; // head node
        do {
            System.out.print(temp.data + " ");
            temp = temp.next;
        } while (temp != last.next);
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        CircularLinkedList cll = new CircularLinkedList();

        cll.insert(10);
        cll.insert(20);
        cll.insert(30);
        cll.insert(40);

        System.out.print("Circular Linked List: ");
        cll.display();

        cll.remove(20);
        System.out.print("After deleting 20: ");
        cll.display();

        cll.remove(10);
        System.out.print("After deleting 10: ");
        cll.display();

        cll.remove(40);
        System.out.print("After deleting 40: ");
        cll.display();
    }
}
