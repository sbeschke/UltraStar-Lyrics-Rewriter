/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uslr;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Sebastian
 */
public class LyricsSyllable {
    LyricsSyllable(LyricsLine line, String prefix, String lyrics) {
        this.line = line;
        this.prefix = prefix;
        this.lyrics = lyrics;
    }

    public LyricsLine getLine() {
        return line;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void write(BufferedWriter writer) throws IOException {
        writer.write(prefix + " " + lyrics + "\n");
    }

    private LyricsLine line; /// The line this syllable belongs to.
    private String prefix; /// Everything in the line before the actual lyrics.
    private String lyrics; /// The bit of lyrics in this syllable.
}
