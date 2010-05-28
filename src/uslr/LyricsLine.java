/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uslr;

import java.util.ArrayList;

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

    private ArrayList<LyricsSyllable> syllables = new ArrayList<LyricsSyllable>();
}
