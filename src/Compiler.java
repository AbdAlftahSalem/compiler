import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Abd Alftah Nedal Salem : 20191758
Saed mohamed afif        : 20191748
Maslama Alaa Al Zebda : 20191403
*/
public class Compiler {
    private static final String FILE_PATH = "p.txt";
    private static final List<String> KEYWORDS = Arrays.asList(
            "-", "(", ")", ",", "*", ".", "..", "/", ":", ":=", ";", "[", "]", "+",
            "<", "<=", ">", "=", "<>", ">=", "ARRAY", "AND", "BEGIN", "BOOLEAN",
            "DIV", "DO", "ELSE", "END", "FALSE", "INTEGER", "IF", "FOR", "MOD",
            "NOT", "OF", "READ", "PROCEDURE", "PROGRAM", "OR", "TRUE",
            "VAR", "WRITE", "THEN"
    );

    private static List<Token> getListOfToken(List<String> codeLines) {
        List<Token> tokenList = new ArrayList<>();
        int numberLine = 1;

        for (String line : codeLines) {
            String[] wordsFromLine = line.split(" ");

            for (String word : wordsFromLine) {
                tokenList.add(createToken(word, numberLine));
            }

            numberLine++;
        }

        return tokenList;
    }

    private static Token createToken(String word, int lineNumber) {
        if (isNumeric(word)) {
            return new Token(lineNumber, word, Token.TokenType.NUMERIC_TOKEN);
        }

        if (KEYWORDS.contains(word.toUpperCase())) {
            return new Token(lineNumber, word, Token.TokenType.KEYWORD);
        }

        return new Token(lineNumber, word, Token.TokenType.IDENTIFIER);
    }

    private static boolean isNumeric(String inputWord) {
        if (inputWord == null) {
            return false;
        } else {
            return inputWord.matches("-?\\d+(\\.\\d+)?");
        }
    }

    private static List<String> readCodeFromFile() throws IOException {
        List<String> codeLineList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toUpperCase();
                if (!line.isEmpty()) {
                    codeLineList.add(line);
                }
            }
        }

        return codeLineList;
    }

    private static void displayTokensInTable(List<Token> tokens) {
        String[] columnNames = {"Name", "Type", "Line No"};
        String[][] data = new String[tokens.size()][3];

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            data[i][0] = String.valueOf(token.getLine());
            data[i][1] = token.getName();
            data[i][2] = token.getType().name();
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Cairo", Font.PLAIN, 14));
        table.setRowHeight(50);
        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        frame.add(new JScrollPane(table));
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        List<Token> tokens = getListOfToken(readCodeFromFile());

        displayTokensInTable(tokens);
    }
}
