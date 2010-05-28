/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uslr;

/**
 *
 * @author Sebastian
 */
public class LyricsSyllable {
    LyricsSyllable(String prefix, String lyrics) {
        this.prefix = prefix;
        this.lyrics = lyrics;
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

    private String prefix; /// Everything in the line before the actual lyrics
    private String lyrics; /// The bit of lyrics in this syllable
}
