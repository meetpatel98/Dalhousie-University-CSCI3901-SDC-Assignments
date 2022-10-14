import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class Main {

    public static void main(String[] args) throws IOException {

            BufferedReader words = new BufferedReader(new FileReader("words.txt"));
            BufferedReader puzzle = new BufferedReader(new FileReader("puzzle.txt"));
            Boggle boggle = new Boggle();
            boggle.getPuzzle(puzzle);
            boggle.getDictionary(words);
            System.out.println("\n");
            System.out.println(boggle.solve());
            System.out.println("\n");
            System.out.println(boggle.print());

    }
}
