import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class NaiveBayes {

    public static ArrayList<String> key = new ArrayList<String>();
    public static int totalTests;
    public static int totalCorrect;
    public static int totalIncorrect;

    public static void main(String[] args) {
        ParseLyrics parseLyrics = new ParseLyrics();

        //read in actual genres from key
        readKEYFile("src/KEY.txt");
        System.out.println(key);

        //iterate through all test cases
        for (int i = 1; i <= 20; i++) {
            String songFile = "src/Test/test" + i + ".txt";
            ArrayList<String> words = readTestSongFile(songFile);
            String guess = guessGenre(words, parseLyrics);
            System.out.println(guess);
            if (guess.equals(key.get(i))) {
                totalCorrect++;
            } else {
                totalIncorrect++;
            }
            totalTests++;
        }

        System.out.println(totalTests);
        System.out.println(totalCorrect);
        System.out.println(totalIncorrect);

        System.out.println("Accuracy: "
                + ((double) totalCorrect / (double) totalTests) * 100 + "%");

        System.out.println("Error: "
                + ((double) totalIncorrect / (double) totalTests) * 100 + "%");

    }

    //read in lines from KEY file and store word values
    private static void readKEYFile(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                //position in line
                int start = 0;
                String word = "";
                while (start < line.length()) {
                    char currChar = line.charAt(start);
                    word += currChar;
                    start++;
                    if (start == line.length() && word.length() != 0) {
                        key.add(word);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        }
    }

    private static String guessGenre(ArrayList<String> words,
            ParseLyrics parseLyrics) {
        String guess = "";
        Double countryProb = 0.0;
        Double popProb = 0.0;
        Double rapProb = 0.0;
        Double rockProb = 0.0;

        //product of all P(word | genre) 
        //iterate through all words of test song
        for (int j = 0; j < words.size(); j++) {
            String word = words.get(j);

            //country
            for (int i = 0; i < ParseLyrics.countryWords.size(); i++) {
                //word exists in genre's word list
                if (word.equals(ParseLyrics.countryWords.get(i))) {
                    int compare = Double.compare(countryProb, 0.0);
                    if (compare == 0) {
                        countryProb = ParseLyrics.countryWordsProb.get(i);
                    } else {
                        countryProb *= ParseLyrics.countryWordsProb.get(i);
                    }
                    break;
                }
            }

            //pop
            for (int i = 0; i < ParseLyrics.popWords.size(); i++) {
                //word exists in genre's word list
                if (word.equals(ParseLyrics.popWords.get(i))) {
                    int compare = Double.compare(popProb, 0.0);
                    if (compare == 0) {
                        popProb = ParseLyrics.popWordsProb.get(i);
                    } else {
                        popProb *= ParseLyrics.popWordsProb.get(i);
                    }
                    break;
                }
            }

            //rap
            for (int i = 0; i < ParseLyrics.rapWords.size(); i++) {
                //word exists in genre's word list
                if (word.equals(ParseLyrics.rapWords.get(i))) {
                    int compare = Double.compare(rapProb, 0.0);
                    if (compare == 0) {
                        rapProb = ParseLyrics.rapWordsProb.get(i);
                    } else {
                        rapProb *= ParseLyrics.rapWordsProb.get(i);
                    }
                    break;
                }
            }

            //rock
            for (int i = 0; i < ParseLyrics.rockWords.size(); i++) {
                //word exists in genre's word list
                if (word.equals(ParseLyrics.rockWords.get(i))) {
                    int compare = Double.compare(rockProb, 0.0);
                    if (compare == 0) {
                        rockProb = ParseLyrics.rockWordsProb.get(i);
                    } else {
                        rockProb *= ParseLyrics.rockWordsProb.get(i);
                    }
                    break;
                }
            }
        }

        //multiply by P(genre)
        countryProb *= 0.25;
        popProb *= 0.25;
        rapProb *= 0.25;
        rockProb *= 0.25;

        //alpha norm 
        double totalProb = countryProb + popProb + rapProb + rockProb;
        countryProb /= totalProb;
        popProb /= totalProb;
        rapProb /= totalProb;
        rockProb /= totalProb;

        //find which prob is greatest
        if (countryProb > popProb && countryProb > rapProb
                && countryProb > rockProb) {
            guess = "country";
        } else if (popProb > countryProb && popProb > rapProb
                && popProb > rockProb) {
            guess = "pop";
        } else if (rapProb > countryProb && rapProb > popProb
                && rapProb > rockProb) {
            guess = "rap";
        } else if (rockProb > countryProb && rockProb > popProb
                && rockProb > rapProb) {
            guess = "rock";
        }
        return guess;
    }

    //read in lines from song file and store word values
    private static ArrayList<String> readTestSongFile(String fileName) {
        ArrayList<String> allSongwords = new ArrayList<String>();

        //some symbols that appear in song lyrics which can be ignored
        String ignoreSymbols = "\",.?;:[]!()";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                //position in line
                int start = 0;
                String word = "";
                while (start < line.length()) {
                    char currChar = line.charAt(start);
                    //current char is a symbol or space - end of word 
                    if (ignoreSymbols.contains(String.valueOf(currChar))
                            || Character.isWhitespace(currChar)
                            && word.length() != 0) {
                        word = word.toLowerCase();
                        allSongwords.add(word);
                        word = "";
                    }
                    //current char is a letter - continue building word 
                    else {
                        word += currChar;
                    }
                    start++;
                    if (start == line.length() && word.length() != 0) {
                        word = word.toLowerCase();
                        allSongwords.add(word);
                        word = "";
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        }
        return allSongwords;
    }

}
