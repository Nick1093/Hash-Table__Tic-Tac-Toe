/**
 * nodes to be stored in the array to create linked lists for separate chaining 
 * implementation of a doubly linked list to make put, remove, get score of board layouts easy 
 * @author nicklam
 *
 */
public class Node {

    // next (which is the node itself) and element (Layout)
    private Layout element;
    private Node next; 

    // here we implement a doubly linked list in order to facilitate putting and removing Layout elements from the dictionary
    private Node prev; 


    // when creating a node we just need the layout, this is the purpose of the constructor
    public Node(Layout layout) {
        this.element = layout;
        this.next = null;  
        this.prev = null;
    }

    // sets next node to the input node
    public void setNext(Node n){
        this.next = n;
    }

    // get the next node in the linked list
    public Node getNext(){
        return this.next;
    }

    // and the getter and setter for prev
    public void setPrev(Node n){
        this.prev = n;
    }

    public Node getPrev(){
        return this.prev;
    }

    // sets the element to the input layout
    public void setElement(Layout l){
        this.element = l;
    }

    // gets the element in the linked list
    public Layout getElement(){
        return this.element;
    }
}
