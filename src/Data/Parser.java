package Data;

import Knowledge.KnowledgeBase;
import Knowledge.Query;

import java.io.*;
import java.util.ArrayList;

/**
 * Static class to parse the text file to both the knowledge base and query
 */
public class Parser {

    // Reading state
    private enum ReadState { None, Ask, Tell }

    /**
     * Static method to read text file
     * @param filename file name
     * @return true if successfully read
     */
    public static boolean readTextFile(String filename) {
        try {
            // Set buffers
            FileReader fr = new FileReader(new File(filename));
            BufferedReader br = new BufferedReader(fr);

            // Set initial read state
            ReadState state = ReadState.None;
            String line;

            StringBuilder tell = new StringBuilder();
            ArrayList<String> ask = new ArrayList();
            while ((line = br.readLine()) != null) {
                // If line is "TELL", set read state to Tell
                if (line.equalsIgnoreCase("TELL")) {
                    state = ReadState.Tell;
                    continue;
                }
                // If line is "ASK", set read state to Tell
                else if (line.equalsIgnoreCase("ASK")) {
                    state = ReadState.Ask;
                    continue;
                }

                // Based on state, the line read is to be put to tell or ask section
                switch (state) {
                    case Tell:
                        tell.append(line);
                        break;
                    case Ask:
                        ask.add(line.replaceAll("(?m)^\\s+$", ""));
                        break;
                }
            }

            // Separate the tell to separate sentence, clean whitespace and add to knowledge base
            String finalTell = tell.toString().replaceAll("(?m)^\\s+$", "");
            String[] tellArray = finalTell.split(";");
            KnowledgeBase.addSentenceArray(tellArray);

            // Add query
            for (String s : ask) {
                Query.addQuery(s);
            }

            // Catch method for File IO exception
        } catch (FileNotFoundException fe) {
            System.out.println("Error finding file");
            fe.printStackTrace();
            return false;
        } catch (IOException ioe) {
            System.out.println("Error reading file");
            ioe.printStackTrace();
            return false;
        }

        return true;
    }
}
