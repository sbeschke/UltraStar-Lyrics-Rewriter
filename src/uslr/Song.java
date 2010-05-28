/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uslr;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sebastian
 */
public class Song {

    public Song() {
    }

    public void load(BufferedReader file) throws Exception {
        lyrics = new ArrayList<LyricsLine>();
        metadata = new ArrayList<MetaDataLine>();

        // Regex to extract tag and value of a metadata item
        Pattern metadataPattern = Pattern.compile("^#(\\p{javaUpperCase}+):(.*)$");
        // Regex to separate a line in the input file into prefix and lyrics
        Pattern syllablePattern = Pattern.compile("(^. \\d+ -?\\d+ -?\\d+ )(.*)$");
        LyricsLine currentLine = new LyricsLine();

        String syllStr = file.readLine();
        boolean endReached = (syllStr == null);
        while(!endReached) {
            char firstChar = syllStr.charAt(0);
            switch(firstChar) {
                case '#': { // metadata
                    Matcher m = metadataPattern.matcher(syllStr);
                    System.out.println(syllStr);
                    if(m.matches()) {
                        String tag = m.group(1);
                        String value = m.group(2);
                        System.out.println("match: " + tag + ":" + value);
                        this.addMetaData(new MetaDataLine(tag, value));
                    }
                    else {
                        System.out.println("NO MATCH");
                    }
                    break;
                }
                case '-': // line break
                    this.addLine(currentLine);
                    currentLine = new LyricsLine();
                    break;
                case ':': // regular syllable
                case '*': // golden note
                case 'F': { // freestyle note
                    // Separate the line into prefix and lyrics by way of a regular expression
                    Matcher m = syllablePattern.matcher(syllStr);
                    System.out.println(syllStr);
                    if(m.matches()) {
                        String syllPrefix = m.group(1);
                        String syllLyrics = m.group(2);
                        System.out.println("match: " + syllLyrics);
                        currentLine.addSyllable(new LyricsSyllable(syllPrefix, syllLyrics));
                    }
                    else {
                        System.out.println("NO MATCH");
                    }
                    break;
                }
                case 'E': // end of song
                    endReached = true;
                    break;
            }

            syllStr = file.readLine();
            if(syllStr == null) endReached = true;
        }
    }

    public void addLine(LyricsLine line) {
        lyrics.add(line);
    }

    public void addMetaData(MetaDataLine line) {
        metadata.add(line);
    }

    public ArrayList<LyricsLine> getLyrics() {
        return lyrics;
    }

    private ArrayList<MetaDataLine> metadata;
    private ArrayList<LyricsLine> lyrics;
}
