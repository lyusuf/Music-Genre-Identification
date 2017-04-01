import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ParseLyrics {

    //array list of all words found in the songs for that genre
    public static ArrayList<String> countryWords = new ArrayList<String>();
    public static ArrayList<String> popWords = new ArrayList<String>();
    public static ArrayList<String> rapWords = new ArrayList<String>();
    public static ArrayList<String> rockWords = new ArrayList<String>();

    //matches with countryWords, popWords, rapWords, rockWords
    //count of word occurrences 
    public static ArrayList<Integer> countryWordsCount = new ArrayList<Integer>();
    public static ArrayList<Integer> popWordsCount = new ArrayList<Integer>();
    public static ArrayList<Integer> rapWordsCount = new ArrayList<Integer>();
    public static ArrayList<Integer> rockWordsCount = new ArrayList<Integer>();

    //matches with countryWords, popWords, rapWords, rockWords
    //probability of word occurring  
    public static ArrayList<Double> countryWordsProb = new ArrayList<Double>();
    public static ArrayList<Double> popWordsProb = new ArrayList<Double>();
    public static ArrayList<Double> rapWordsProb = new ArrayList<Double>();
    public static ArrayList<Double> rockWordsProb = new ArrayList<Double>();

    //array list of total number of words for each genre
    //[country, pop, rap, rock]
    public static ArrayList<Integer> genreTotalNumWords = new ArrayList<>(
            Arrays.asList(0, 0, 0, 0));

    public ParseLyrics() {
        for (int i = 1; i <= 30; i++) {
            String songFile = "src/Country/country" + i + ".txt";
            readSongFileCountry(songFile);
        }
        for (int i = 1; i <= 30; i++) {
            String songFile = "src/Pop/pop" + i + ".txt";
            readSongFilePop(songFile);
        }

        for (int i = 1; i <= 30; i++) {
            String songFile = "src/Rap/rap" + i + ".txt";
            readSongFileRap(songFile);
        }

        for (int i = 1; i <= 30; i++) {
            String songFile = "src/Rock/rock" + i + ".txt";
            readSongFileRock(songFile);
        }

        computeWordProb();
    }

    //compute prob of words for each genre 
    private static void computeWordProb() {
        double totalWords = genreTotalNumWords.get(0);
        for (int i = 0; i < countryWordsCount.size(); i++) {
            countryWordsProb
                    .add((double) countryWordsCount.get(i) / totalWords);
        }

        totalWords = genreTotalNumWords.get(1);
        for (int i = 0; i < popWordsCount.size(); i++) {
            popWordsProb.add((double) popWordsCount.get(i) / totalWords);
        }

        totalWords = genreTotalNumWords.get(2);
        for (int i = 0; i < rapWordsCount.size(); i++) {
            rapWordsProb.add((double) rapWordsCount.get(i) / totalWords);
        }

        totalWords = genreTotalNumWords.get(3);
        for (int i = 0; i < rockWordsCount.size(); i++) {
            rockWordsProb.add((double) rockWordsCount.get(i) / totalWords);
        }

    }

    //read in lines from song file and store word values - Country
    private static void readSongFileCountry(String fileName) {
        //some symbols that appear in song lyrics which can be ignored
        String ignoreSymbols = "\",.?;:[]!()";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            int totalWordCount = 0;
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
                        boolean found = false;
                        for (int i = 0; i < countryWords.size(); i++) {
                            //word already existing in genre's word list
                            if (word.equals(countryWords.get(i))) {
                                //increase count of specific word
                                int foundWordCount = countryWordsCount.get(i) + 1;
                                countryWordsCount.set(i, foundWordCount);
                                found = true;
                                break;
                            }
                        }
                        //did not find matching word - add to list 
                        if (!found) {
                            countryWords.add(word);
                            countryWordsCount.add(1);
                        }
                        word = "";
                        totalWordCount++;
                    }
                    //current char is a letter - continue building word 
                    else {
                        word += currChar;
                    }
                    start++;
                    if (start == line.length() && word.length() != 0) {
                        word = word.toLowerCase();
                        boolean found = false;
                        for (int i = 0; i < countryWords.size(); i++) {
                            //word already existing in genre's word list
                            if (word.equals(countryWords.get(i))) {
                                //increase count of specific word
                                int foundWordCount = countryWordsCount.get(i) + 1;
                                countryWordsCount.set(i, foundWordCount);
                                found = true;
                                break;
                            }
                        }
                        //did not find matching word - add to list 
                        if (!found) {
                            countryWords.add(word);
                            countryWordsCount.add(1);
                        }
                        word = "";
                        totalWordCount++;
                    }
                }
            }
            //update genre's total num words with this song
            int newTotalWords = genreTotalNumWords.get(0) + totalWordCount;
            genreTotalNumWords.set(0, newTotalWords);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        }
    }

    //read in lines from song file and store word values - Pop
    private static void readSongFilePop(String fileName) {
        //some symbols that appear in song lyrics which can be ignored
        String ignoreSymbols = "\",.?;:[]!()";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            int totalWordCount = 0;
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
                        boolean found = false;
                        for (int i = 0; i < popWords.size(); i++) {
                            //word already existing in genre's word list
                            if (word.equals(popWords.get(i))) {
                                //increase count of specific word
                                int foundWordCount = popWordsCount.get(i) + 1;
                                popWordsCount.set(i, foundWordCount);
                                found = true;
                                break;
                            }
                        }
                        //did not find matching word - add to list 
                        if (!found) {
                            popWords.add(word);
                            popWordsCount.add(1);
                        }
                        word = "";
                        totalWordCount++;
                    }
                    //current char is a letter - continue building word 
                    else {
                        word += currChar;
                    }
                    start++;
                    if (start == line.length() && word.length() != 0) {
                        word = word.toLowerCase();
                        boolean found = false;
                        for (int i = 0; i < popWords.size(); i++) {
                            //word already existing in genre's word list
                            if (word.equals(popWords.get(i))) {
                                //increase count of specific word
                                int foundWordCount = popWordsCount.get(i) + 1;
                                popWordsCount.set(i, foundWordCount);
                                found = true;
                                break;
                            }
                        }
                        //did not find matching word - add to list 
                        if (!found) {
                            popWords.add(word);
                            popWordsCount.add(1);
                        }
                        word = "";
                        totalWordCount++;
                    }
                }
            }
            //update genre's total num words with this song
            int newTotalWords = genreTotalNumWords.get(1) + totalWordCount;
            genreTotalNumWords.set(1, newTotalWords);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        }
    }

    //read in lines from song file and store word values - Rap
    private static void readSongFileRap(String fileName) {
        //some symbols that appear in song lyrics which can be ignored
        String ignoreSymbols = "\",.?;:[]!()";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            int totalWordCount = 0;
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
                        boolean found = false;
                        for (int i = 0; i < rapWords.size(); i++) {
                            //word already existing in genre's word list
                            if (word.equals(rapWords.get(i))) {
                                //increase count of specific word
                                int foundWordCount = rapWordsCount.get(i) + 1;
                                rapWordsCount.set(i, foundWordCount);
                                found = true;
                                break;
                            }
                        }
                        //did not find matching word - add to list 
                        if (!found) {
                            rapWords.add(word);
                            rapWordsCount.add(1);
                        }
                        word = "";
                        totalWordCount++;
                    }
                    //current char is a letter - continue building word 
                    else {
                        word += currChar;
                    }
                    start++;
                    if (start == line.length() && word.length() != 0) {
                        word = word.toLowerCase();
                        boolean found = false;
                        for (int i = 0; i < rapWords.size(); i++) {
                            //word already existing in genre's word list
                            if (word.equals(rapWords.get(i))) {
                                //increase count of specific word
                                int foundWordCount = rapWordsCount.get(i) + 1;
                                rapWordsCount.set(i, foundWordCount);
                                found = true;
                                break;
                            }
                        }
                        //did not find matching word - add to list 
                        if (!found) {
                            rapWords.add(word);
                            rapWordsCount.add(1);
                        }
                        word = "";
                        totalWordCount++;
                    }
                }
            }
            //update genre's total num words with this song
            int newTotalWords = genreTotalNumWords.get(2) + totalWordCount;
            genreTotalNumWords.set(2, newTotalWords);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        }
    }

    //read in lines from song file and store word values - Rock
    private static void readSongFileRock(String fileName) {
        //some symbols that appear in song lyrics which can be ignored
        String ignoreSymbols = "\",.?;:[]!()";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            int totalWordCount = 0;
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
                        boolean found = false;
                        for (int i = 0; i < rockWords.size(); i++) {
                            //word already existing in genre's word list
                            if (word.equals(rockWords.get(i))) {
                                //increase count of specific word
                                int foundWordCount = rockWordsCount.get(i) + 1;
                                rockWordsCount.set(i, foundWordCount);
                                found = true;
                                break;
                            }
                        }
                        //did not find matching word - add to list 
                        if (!found) {
                            rockWords.add(word);
                            rockWordsCount.add(1);
                        }
                        word = "";
                        totalWordCount++;
                    }
                    //current char is a letter - continue building word 
                    else {
                        word += currChar;
                    }
                    start++;
                    if (start == line.length() && word.length() != 0) {
                        word = word.toLowerCase();
                        boolean found = false;
                        for (int i = 0; i < rockWords.size(); i++) {
                            //word already existing in genre's word list
                            if (word.equals(rockWords.get(i))) {
                                //increase count of specific word
                                int foundWordCount = rockWordsCount.get(i) + 1;
                                rockWordsCount.set(i, foundWordCount);
                                found = true;
                                break;
                            }
                        }
                        //did not find matching word - add to list 
                        if (!found) {
                            rockWords.add(word);
                            rockWordsCount.add(1);
                        }
                        word = "";
                        totalWordCount++;
                    }
                }
            }
            //update genre's total num words with this song
            int newTotalWords = genreTotalNumWords.get(3) + totalWordCount;
            genreTotalNumWords.set(3, newTotalWords);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            System.exit(1);
        }
    }
}
