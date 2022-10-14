public class TrieNode {

    // If lastNode is true, then we are at end of the word
    private boolean lastNode;

    // array of type TrieNode
    TrieNode[] childNode = new TrieNode[255];

    // Constructor
    // Create an empty trie
    public TrieNode()
    {
        lastNode = false;
        for (int i = 0; i < 255; i++){
            childNode[i] = null;
        }
    }

    // return lastNode
    public boolean isLastNode() {
        return lastNode;
    }

    // used to set the value of lastNode
    public void setLastNode(boolean lastNode) {
        this.lastNode = lastNode;
    }
}
