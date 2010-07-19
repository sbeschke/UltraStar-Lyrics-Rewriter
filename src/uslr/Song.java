/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uslr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sebastian
 */
public class Song {

    public Song() {
    }

    public Song(File file) throws Exception {
        load(file);
    }

    public void load(File file)throws Exception {
        String cs = "ISO-8859-1";
        FileInputStream strm = new FileInputStream(file);
        byte[] bomTest = new byte[utf8Bom.length];
        strm.read(bomTest);
        boolean isUtf8 = true;
        for(int i = 0; i < utf8Bom.length; i++) {
            if(utf8Bom[i] != bomTest[i]) {
                isUtf8 = false;
            }
        }
        if(!isUtf8) {
            // Re-open the file to restart reading
            strm.close();
            strm = new FileInputStream(file);
        }
        else {
            // use UTF-8 encoding
            cs = "UTF-8";
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(strm, cs));

        load(reader, file.getPath());

        strm.close();
    }

    public void load(BufferedReader reader, String fileName) throws Exception {
        lyrics = new ArrayList<LyricsLine>();
        metadata = new ArrayList<MetaDataLine>();

        // Regex to extract tag and value of a metadata item
        Pattern metadataPattern = Pattern.compile("^#(.+):(.*)$");
        // Regex to separate a line in the input file into prefix and lyrics
        Pattern syllablePattern = Pattern.compile("(^. -?\\d+ -?\\d+ -?\\d+)( (.*))?$");
        LyricsLine currentLine = new LyricsLine();

        int lineNumber = 1;
        String syllStr = reader.readLine();
        boolean endReached = (syllStr == null);
        while(!endReached) {
            char firstChar = syllStr.charAt(0);
            switch(firstChar) {
                case '#': { // metadata
                    Matcher m = metadataPattern.matcher(syllStr);
                    if(m.matches()) {
                        String tag = m.group(1);
                        String value = m.group(2);
                        this.addMetaData(new MetaDataLine(tag, value));
                    }
                    else {
                        throw new Exception("Unable to process metadata in line "
                                + lineNumber + " of " + fileName);
                    }
                    break;
                }
                case '-': // line break
                    currentLine.setLineBreakMarker(syllStr);
                    this.addLine(currentLine);
                    currentLine = new LyricsLine();
                    break;
                case ':': // regular syllable
                case '*': // golden note
                case 'F': { // freestyle note
                    // Separate the line into prefix and lyrics by way of a regular expression
                    Matcher m = syllablePattern.matcher(syllStr);
                    if(m.matches()) {
                        String syllPrefix = m.group(1);
                        String syllLyrics = m.group(3);
                        currentLine.addSyllable(new LyricsSyllable(currentLine, syllPrefix, syllLyrics));
                    }
                    else {
                        throw new Exception("Unable to process lyrics in line "
                                + lineNumber + " of " + fileName);
                    }
                    break;
                }
                case 'E': // end of song
                    currentLine.setLineBreakMarker(syllStr);
                    endReached = true;
                    break;
            }

            syllStr = reader.readLine();
            lineNumber++;
            if(syllStr == null) endReached = true;
        }
        this.addLine(currentLine);
    }

    public void save(BufferedWriter writer) throws IOException {
        for(Iterator<MetaDataLine> i = metadata.iterator(); i.hasNext();) {
            i.next().write(writer);
        }
        for(Iterator<LyricsLine> i = lyrics.iterator(); i.hasNext();) {
            i.next().write(writer);
        }
    }

    public void addLine(LyricsLine line) {
        lyrics.add(line);
    }

    public void addMetaData(MetaDataLine line) {
        // todo hacky, this goofy data caching has to go
        if(line.getTag().equals("ARTIST")) {
            this.setArtist(line.getValue());
        }
        if(line.getTag().equals("TITLE")) {
            this.setTitle(line.getValue());
        }
        if(line.getTag().equals("LANGUAGE")) {
            this.setLanguage(line.getValue());
        }
        metadata.add(line);
    }

    public ArrayList<LyricsLine> getLyrics() {
        return lyrics;
    }

    public LyricsSyllable getFirstSyllable() throws Exception {
        if(lyrics.size() < 1) {
            throw new Exception("Get first syllable of empty lyrics");
        }
        return lyrics.get(0).getFirstSyllable();
    }

    public LyricsLine getPreviousLine(LyricsLine line) {
        int lineIdx = lyrics.indexOf(line) - 1;
        if(lineIdx < 0) {
            return null;
        }
        else {
            return lyrics.get(lineIdx);
        }
    }

    public LyricsLine getNextLine(LyricsLine line) {
        int lineIdx = lyrics.indexOf(line) + 1;
        if(lineIdx >= lyrics.size()) {
            return null;
        }
        else {
            return lyrics.get(lineIdx);
        }
    }

    public LyricsSyllable getPreviousSyllable(LyricsSyllable syl) throws Exception {
        LyricsLine line = syl.getLine();
        LyricsSyllable newsyl = line.getPreviousSyllable(syl);
        if(newsyl == null) {
            int lineIdx = lyrics.indexOf(line) - 1;
            if(lineIdx < 0) {
                return syl;
            }
            else {
                return lyrics.get(lineIdx).getLastSyllable();
            }
        }
        else {
            return newsyl;
        }
    }

    public LyricsSyllable getNextSyllable(LyricsSyllable syl) throws Exception {
        LyricsLine line = syl.getLine();
        LyricsSyllable newsyl = line.getNextSyllable(syl);
        if(newsyl == null) {
            int lineIdx = lyrics.indexOf(line) + 1;
            if(lineIdx >= lyrics.size()) {
                return syl;
            }
            else {
                return lyrics.get(lineIdx).getFirstSyllable();
            }
        }
        else {
            return newsyl;
        }
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    /**
     * @param artist the artist to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    private final byte[] utf8Bom = {(byte)0xef, (byte)0xbb, (byte)0xbf};
    private ArrayList<MetaDataLine> metadata;
    private ArrayList<LyricsLine> lyrics;

    // todo cached data - hacky, replace with proper metadata implementation
    private String artist;
    private String title;
    private String language;
}
