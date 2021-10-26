
public class Dictionary implements DictionaryADT {

    // size of the table
    private int size;

    // creating of the array that stores nodes of linked lists
    private Node[] table;

    /**
     * This is the constructor. We define size and table of size input size
     * 
     * @param size which is the size of the dictonary 
     */
    public Dictionary(int size) {
        this.size = size;
        table = new Node[size];
    }

    /**
     * inserts the Layout object referenced by data in the dictionary if the dictionary does not contain any object with the
     * same key attribute as data, otherwise throw DictionaryException
     * @param data 
     */
    public int put(Layout data) throws DictionaryException {

        // get the hash value for this data
        int keyID = hash(data.getBoardLayout());
       
        // node to store the data
        Node node = new Node(data);

        // if empty then fill 
        if (table[keyID] == null){
            table[keyID] = node;
            
            // returns 0 if null or empty list
            return 0;
        } 

        else { 
        	
            // need to travel until the end
            Node travelNode = table[keyID];
            
            // if we find the key 
            if ( travelNode.getElement().getBoardLayout().equals(data.getBoardLayout())){
                throw new DictionaryException("Key already found, can't insert this key");
            }

            // not equal to null we keep looping through to the last node to place at the end
            while (travelNode.getNext() != null){
                // travel to the end of the linkedlist, so we can add the new node to its tail
                if ( travelNode.getElement().getBoardLayout().equals(data.getBoardLayout())) {
                    throw new DictionaryException("Key already found, can't insert this key");
                }

                travelNode = travelNode.getNext();
            }

            // these 2 lines is to link the new node to existing LinkedList to make the chain 
            travelNode.setNext(node);
            node.setPrev(travelNode); 

            // returns 1 if the list already stores at least one element
            return 1;
        }

    }

    
    /**
     * removes the object with key boardLayout from the dictionary and throws exception if no data item in the dictionary with the key
     */
    public void remove(String boardLayout) throws DictionaryException {
    	
    	// hold the hash value in keyID
        int keyID = hash(boardLayout);

        // this is where a doubly linked list helps us to break the connections of the node if found to be removed
        Node travelNode = table[keyID];
        
        // if its null, then we didnt find it
        if (travelNode == null) {
            throw new DictionaryException("Key not found, can't remove this key");
        } 
        else {
            // compare the 1st node of the LL vs. boardLayout right away
            if (travelNode.getElement().getBoardLayout().equals(boardLayout)){

                // so we know the 1st node is the one we need to remove
                if (travelNode.getNext() == null) { //it is a stand alone node 
                    table[keyID] = null;
                } 
                else {
                    table[keyID] = travelNode.getNext();
                    travelNode.getNext().setPrev(null);
                }
                return;
            }

            //outside of the if above, we will have to travel the linked list as we know the match is not the 1st node (head)
            while ( travelNode.getNext() != null) {

                if ( travelNode.getElement().getBoardLayout().equals(boardLayout)){ // means the match is somewhere in the middle
                    
                    //it has a prev node and next node
                    Node nextNode = travelNode.getNext();
                    Node prevNode = travelNode.getPrev();
                    
                    // set next for next and prev 
                    prevNode.setNext(nextNode);
                    nextNode.setPrev(prevNode);
                    return;
                }
                
                // set to next node
                travelNode = travelNode.getNext();
            }

            // we need another check for the last node
            travelNode = travelNode.getNext();
            if ( travelNode.getElement().getBoardLayout().equals(boardLayout)){ // means this match is at the tail of the LL
                Node prevNode = travelNode.getPrev();
                prevNode.setNext(null);
                return;
            }

            // now if after all this we still don't find any match, we must throw exception
            throw new DictionaryException("Key not found, can't remove this key");
        }

    }

    /**
     * returns the score stored in the object in the dictionary with key boardLayoutj or - 1 if no object in the dictionary with that key
     */
    public int getScore(String boardLayout) {
    	
    	// contains the hash value of the key 
        int keyID = hash(boardLayout);

        // node used to traverse the LL holding the hash value in the table to see if it matches with one that exists in the dictionary
        Node travelNode = table[keyID];
        
        if ( travelNode == null) {
            return -1;
        } 
        else {
        	
        	// if first elem in the LL then get the score 
            if ( travelNode.getElement().getBoardLayout().equals(boardLayout)) {
                return travelNode.getElement().getScore();
            }
            
            // if not then we traverse through the LL using our travel node containing the keyID from the table
            while (travelNode.getNext() != null){
            	
            	// if found, then get the score
                if (travelNode.getElement().getBoardLayout().equals(boardLayout)) {
                    return travelNode.getElement().getScore();
                }
                
                // set next to check next node
                travelNode = travelNode.getNext();
            }
            
            // check the last element 
            if ( travelNode.getElement().getBoardLayout().equals(boardLayout)) {
                return travelNode.getElement().getScore();
            }
            
            // not found return -1
            return -1;
        }
    }

    /**
     * Sets the key's location in the hashset for each character/move
     * @param layout - layout of the board, and string as key
     * @return location in the hashset
     */
    private int hash(String key) {

        /**
         * in this private method we declare 2 variables
         * the first one pos, is used to store the location in the hash table that the key will be placed
         * the second is used specifically for test 10 in TestDict as we need less collisions so we will have a specific
         * equation to get a more diverse set of hash values
         * */ 
		int pos = 0;
        int tracker = 0;

        // this for loop goes through the key converting each char to an integer and adding it to pos to be converted to a location in the hash table
		for(int i = 0; i < key.length(); i++) {

            // create a character to store each char we are looping through to be converted
			char c = key.charAt(i);

            // our main formula: we add what is already in pos with the power of a prime number 17, times the length of the key minus a tracker 
            // that we have just to make sure it is different and then multiply by the current char to minimize collisions
			pos = pos + (int) Math.pow(17, key.length() - tracker) * c;

            // increment tracker each char 
            tracker += 1;
		}

        // we have to mod the pos by the size of the array, but a prime number, to store it in the hash table
        int primeNum = 0;
        // here temp is so that we can store the size to be modified, in case it is even number
        int temp = size;

        // next we have to divide the number by the size, but prime number 
        if ((temp - 1) % 2 == 1) {
            primeNum = temp - 1;
        }
        // if size - 1 is not a prime then we, decrease by 1 and devide by the prime Num size
        else {
            temp -= 1;
            primeNum = temp - 1;
        }

		pos = Math.abs(pos % primeNum);

		return pos;
	}
}
