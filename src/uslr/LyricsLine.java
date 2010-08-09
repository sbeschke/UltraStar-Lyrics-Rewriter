/*  This file is part of UltraStar Lyrics Rewriter (USLR),
 *  a tool for rapid UltraStar lyrics editing.
 *
 *  USLR is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  USLR is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with USLR.  If not, see <http://www.gnu.org/licenses/>.
 */

package uslr;

import java.io.BufferedWriter;
import java.io.IOException;
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
            if(syl == emphasize) lyrics += "<font bgcolor='FF8080'>";
            lyrics += syl.getLyrics();
            if(syl == emphasize) lyrics += "</font>";
        }
        return lyrics;
    }

    public int getCharIndex(LyricsSyllable syllable) {
        int result = 0;
        Iterator<LyricsSyllable> i = syllables.iterator();
        while(i.hasNext()) {
            LyricsSyllable syl = i.next();
            if(syl == syllable) {
                break;
            }
            result += syl.getLyrics().length();
        }
        System.out.println(result);
        return result;
    }

    public LyricsSyllable getFirstSyllable() throws Exception {
        if(this.syllables.size() < 1) throw new Exception("Get first syllable on empty line");
        return syllables.get(0);
    }

    public LyricsSyllable getLastSyllable() throws Exception {
        if(this.syllables.size() < 1) throw new Exception("Get last syllable on empty line");
        return syllables.get(syllables.size() - 1);
    }

    public LyricsSyllable getPreviousSyllable(LyricsSyllable syl) {
        int index = syllables.indexOf(syl) - 1;
        if(index < 0) {
            return null;
        }
        else {
            return syllables.get(index);
        }
    }

    public LyricsSyllable getNextSyllable(LyricsSyllable syl) {
        int index = syllables.indexOf(syl) + 1;
        if(index >= syllables.size()) {
            return null;
        }
        else {
            return syllables.get(index);
        }
    }

    /**
     * @return the lineBreakMarker
     */
    public String getLineBreakMarker() {
        return lineBreakMarker;
    }

    /**
     * @param lineBreakMarker the lineBreakMarker to set
     */
    public void setLineBreakMarker(String lineBreakMarker) {
        this.lineBreakMarker = lineBreakMarker;
    }

    public void write(BufferedWriter writer) throws IOException {
        for(Iterator<LyricsSyllable> i = syllables.iterator(); i.hasNext();) {
            i.next().write(writer);
        }
        writer.write(lineBreakMarker + "\n");
    }
    
    private ArrayList<LyricsSyllable> syllables = new ArrayList<LyricsSyllable>();
    private String lineBreakMarker = "";
}
