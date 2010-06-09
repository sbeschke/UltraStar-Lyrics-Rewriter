/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uslr;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Sebastian
 */
public class LyricsLine {

    public LyricsLine() {
    }

    public void addSyllable(LyricsSyllable syllable) {
        syllables.add(syllable);
    }

    public ArrayList<LyricsSyllable> getSyllables() {
        return syllables;
    }

    /** Gets the lyrics of the whole line as a string.
     *
     * @return
     */
    public String getLyrics(LyricsSyllable emphasize) {
        String lyrics = "";
        Iterator<LyricsSyllable> i = syllables.iterator();
        while(i.hasNext()) {
            LyricsSyllable syl = i.next();
            if(syl == emphasize) lyrics += "→";
            lyrics += syl.getLyrics();
            if(syl == emphasize) lyrics += "←";
        }
        return lyrics;
    }

    public LyricsSyllable getFirstSyllable() throws Exception {
        if(this.syllables.size() < 1) throw new Exception("Get first syllable on empty line");
        return syllables.get(0);
    }

    private ArrayList<LyricsSyllable> syllables = new ArrayList<LyricsSyllable>();
}
