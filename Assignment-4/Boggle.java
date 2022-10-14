import javax.sound.midi.Soundbank;
import javax.swing.plaf.SplitPaneUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.FileNameMap;
import java.nio.CharBuffer;
import java.util.*;
import java.util.stream.Collectors;

public class Boggle {

    private int P = 0; // Indicates thye number of rows
    private int Q = 0; // indicates the number of columns
    private int x; // used to set and get the location of (starting x)
    private int y; // used to set and get the location os (Starting y)
    private int n = 0; // used to store the number of words in dictionary
    private char[][] puzzleMatrix; // used to store the puxxle row and column
    private final TrieNode rootNode = new TrieNode(); // trie's root node

    private final List<String> dictionary; // read the words from file and store it in dictionary
    private final List<String> puzzleWords; // read the puzzle rows from filw and store it in the dictionary
    private final List<Integer> puzzleColumns; // used to store the size of each row in puzzle
    private final List<String> words; // used to store tha words which are found in the puzzlw
    private final List<String> finalWords; // used to store the final string which contains word,
                                            //starting x, starting y and navigation sequence>
    private final StringBuffer path;  // used to store the navigation sequence

    // constructor
    public Boggle() {
        dictionary = new LinkedList<>();
        puzzleWords = new LinkedList<>();
        puzzleColumns = new LinkedList<>();
        words = new LinkedList<>();
        finalWords = new LinkedList<>();
        path = new StringBuffer();
    }

    // This method will reads thw words from the file
    // the methods will return false if while reading it encounters a word of length less then 2 char
    // The method will also retrun false if the file is empty and there are no words to read
    // the method returns true if all the words in the file are read and are ready to use for puzzle solving
    public boolean getDictionary(BufferedReader stream) {
        char ch; // used to store the converted lowecase case letters
        String line; // used to store word of the file while reading
        StringBuilder lowerCase = new StringBuilder(); // used to store store the words after it is
                                                        // converted to lowercase
        int lines = 0; // used to calculate numbers of lines in the file
        try {
            // read the file untill we are at end-of-file
            while ((line = stream.readLine()) != null) {
                if(line.length() == 0 ){
                    break;
                }
                lines++;
                //read every character of the word and convert it into lowercase
                for (int i = 0; i < line.length(); i++) {
                    if ((line.charAt(i) >= 'A' && line.charAt(i) <= 'Z') || (line.charAt(i) >= 'a' && line.charAt(i) <= 'z')) {
                        ch = Character.toLowerCase(line.charAt(i));
                        lowerCase.append(ch);
                    } else {
                        ch = line.charAt(i);
                        lowerCase.append(ch);
                    }
                }
                // it skip the word if its lenght is less than 2
                if ((line.length() < 2)) {
                    System.out.println(line + " -->Skipped... length of word is less then 2 character");
                } else {
                    dictionary.add(lowerCase.toString());
                }
                lowerCase.delete(0, lowerCase.length());
            }
        } catch (Exception e) {
            System.out.println("Message - " + e.getMessage());
        }
        // if no words are found in the file then it will return false
        if (lines == 0) {
            System.out.println("No words in file. File is empty");
            return false;
        }
        n = dictionary.size();
        return true;
    }
        public boolean getPuzzle(BufferedReader stream) {
        int q; // used to store the length of eaach word in the puzzle
        int r; // used to store the lenght of first word
        char ch; // used to store the converted lowecase case letters
        String line; // used to store word of the file while reading
        int rowCount = 0; // used to sount number of rows
        StringBuilder lowerCase = new StringBuilder(); // used to store store the words after it is
                                                        // converted to lowercase
        try {
            // read the file untill we are at end-of-file
            while ((line = stream.readLine()) != null) {
                rowCount++;
                for (int i = 0; i < line.length(); i++) {
                    if ((line.charAt(i) >= 'A' && line.charAt(i) <= 'Z') || (line.charAt(i) >= 'a' && line.charAt(i) <= 'z')) {
                        ch = Character.toLowerCase(line.charAt(i));
                        lowerCase.append(ch);
                    }else {
                        ch = line.charAt(i);
                        lowerCase.append(ch);
                    }
                }
                puzzleWords.add(lowerCase.toString());
                lowerCase.delete(0, lowerCase.length());
            }
        } catch (IOException e) {
            System.out.println("Message - " + e.getMessage());
            return false;
        }
        // used to stores the lenght of each row
        for (String puzzleWord : puzzleWords) {
            q = puzzleWord.length();
            puzzleColumns.add(q);
        }
        // if all the word in the puzzle have the same lenght we can assume that
        // length of  each column is same adn this can be confirm by samColumn boolen variable
        // it will return true if length of each column is same else return false
        boolean sameColumn = puzzleColumns.stream().distinct().count() <= 1;

        // this will retrun true if there are words in the puzzle file and has
        // equal lenght of column
        if (rowCount != 0) {
            if (sameColumn) {
                r = puzzleColumns.get(0);
                P = rowCount;
                Q = r;
                puzzleMatrix = new char[P][Q];
                // create a puzzle of M * N
                for (int i = 0; i < puzzleWords.size(); i++) {
                    for (int j = 0; j < puzzleWords.get(i).length(); j++) {
                        puzzleMatrix[i][j] = puzzleWords.get(i).charAt(j);
                    }
                }
                return true;
            } else {
                System.out.println("Every row of the puzzle should have the same number of letters");
                return false;
            }
        } else {
            System.out.println("No puzzle found. File is empty.");
            return false;
        }
    }
    // This method will retrun the string in the following formate
    // <word> <starting X> <staring Y> <navigation sequence>
    // retrun empty list when puzzle is not found or when words are not given
    public List<String> solve() {
        // intially we mark all the characters as not visistied
        // we will not visited character as false as as we visit the character we will mark it true
        boolean[][] visitedNode = new boolean[P][Q];
        TrieNode findNode = rootNode;

        // used to store the character one by one from the puzzleMatrix so that we can find that character
        StringBuilder str = new StringBuilder();

        // here we are trying to insert the each character of words into the trie
        // if the character is not present then insert it into the trie
        // if character is the last character then mark it by setting setLastNode to true
        for (int i = 0; i < n; i++) {
            int x = dictionary.get(i).length();
            TrieNode insertNode = rootNode;
            for (int j = 0; j < x; j++) {
                int findIndex = dictionary.get(i).charAt(j) - '!';
                if (insertNode.childNode[findIndex] == null){
                    insertNode.childNode[findIndex] = new TrieNode();
                }
                insertNode = insertNode.childNode[findIndex];
            }
            insertNode.setLastNode(true);
        }
        // here we will traverse all the matrix element
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < Q; j++) {
                // here we will start searching for the word
                // we will search the word characters by charcaters of the words
                if (findNode.childNode[(puzzleMatrix[i][j]) - '!'] != null) {
                    str.append(puzzleMatrix[i][j]);
                    setX(j);
                    setY(i);
                    findWord(findNode.childNode[(puzzleMatrix[i][j]) - '!'],
                            puzzleMatrix, i, j, visitedNode, str.toString(), "");
                    str = new StringBuilder();
                }
            }
        }
        Collections.sort(finalWords);
        return finalWords;
    }

    // this is recursive method which is used to find the word and path
    // If we found the word which we are searching then we will add that word in a list
    private void findWord(TrieNode rootNode, char[][] boggle, int i,
                            int j, boolean[][] visitedNode, String nodeCharacter, String direction) {
        if (rootNode.isLastNode()) {
            if (!words.contains(nodeCharacter)) {
                words.add(nodeCharacter);
                String finalCharacter = nodeCharacter;
                finalWords.add(finalCharacter + "\t" + (getX() + 1) + "\t" + (getY() + P - (2 * getY())) + "\t" + path.toString());
            }
        }

        // here if we are visiting the character for the first time then we will mark that
        // character as true
        if (i >= 0 && i < P && j >= 0 && j < Q && !visitedNode[i][j]) {
            visitedNode[i][j] = true;
            int size = 255;

            // we will traverse/visit all the character of trie and try to find that in the puxzlleMartix
            for (int K = 0; K < size; K++) {
                if (rootNode.childNode[K] != null) {
                    char currentchar = (char) (K + '!'); // this is the current charcater of the word

                    // now we will search recursively the remaining characters of the word which are in trie
                    // We will search that characters from the 8 adjacent cell of the boggle puzzle
                    // we are showing how we navigate through the grid to form the desired word.
                    if (i + 1 < P && j + 1 < Q && !visitedNode[i + 1][j + 1] && boggle[i + 1][j + 1] == currentchar) {
                        findWord(rootNode.childNode[K], boggle,
                                i + 1, j + 1,
                                visitedNode, nodeCharacter + currentchar, path.append("S").toString());
                        path.append(direction);
                    }
                    if (i < P && j + 1 < Q && !visitedNode[i][j + 1] && boggle[i][j + 1] == currentchar) {
                        findWord(rootNode.childNode[K], boggle,
                                i, j + 1,
                                visitedNode, nodeCharacter + currentchar, path.append("R").toString());
                        path.append(direction);
                    }
                    if (i - 1 >= 0 && i - 1 < P && j + 1 < Q && !visitedNode[i - 1][j + 1] && boggle[i - 1][j + 1] == currentchar) {
                        findWord(rootNode.childNode[K], boggle,
                                i - 1, j + 1,
                                visitedNode, nodeCharacter + currentchar, path.append("E").toString());
                        path.append(direction);

                    }
                    if (i + 1 < P && j < Q && !visitedNode[i + 1][j] && boggle[i + 1][j] == currentchar) {
                        findWord(rootNode.childNode[K], boggle,
                                i + 1, j,
                                visitedNode, nodeCharacter + currentchar, path.append("D").toString());
                        path.append(direction);

                    }
                    if (j - 1 >= 0 && i + 1 < P && j - 1 < Q && !visitedNode[i + 1][j - 1] && boggle[i + 1][j - 1] == currentchar) {
                        findWord(rootNode.childNode[K], boggle,
                                i + 1, j - 1,
                                visitedNode, nodeCharacter + currentchar, path.append("W").toString());
                        path.append(direction);

                    }
                    if (j - 1 >= 0 && i < P && j - 1 < Q && !visitedNode[i][j - 1] && boggle[i][j - 1] == currentchar) {
                        findWord(rootNode.childNode[K], boggle,
                                i, j - 1,
                                visitedNode, nodeCharacter + currentchar, path.append("L").toString());
                        path.append(direction);

                    }
                    if ((i-1)>=0 && (j-1) >= 0 && (i-1) < P && (j-1) < Q && !visitedNode[i-1][j-1]
                            && boggle[i - 1][j - 1] == currentchar) {
                        findWord(rootNode.childNode[K], boggle,
                                i - 1, j - 1,
                                visitedNode, nodeCharacter + currentchar, path.append("N").toString());
                        path.append(direction);

                    }
                    if (i - 1 >= 0 && i - 1 < P && j < Q && !visitedNode[i - 1][j] && boggle[i - 1][j] == currentchar) {
                        findWord(rootNode.childNode[K], boggle,
                                i - 1, j,
                                visitedNode, nodeCharacter + currentchar, path.append("U").toString());
                        path.append(direction);

                    }
                }
            }
            // at last mark the visited node as false
            visitedNode[i][j] = false;
            // make the stringBuilder blank
            path.delete(0, path.length());
        }
    }
    // This method will print the current puzzle
    public String print() {
        StringBuilder sb = new StringBuilder();
        for (String puzzleWord : puzzleWords) {
            sb.append(puzzleWord).append("\n");
        }
        return sb.toString();
    }

    // retrun the value of x
    private int getX() {
        return x;
    }

    // used to set the value of x
    private void setX(int x) {
        this.x = x;
    }

    // retrun the value of y
    private int getY() {
        return y;
    }

    //used to set the value of y
    private void setY(int y) {
        this.y = y;
    }
}