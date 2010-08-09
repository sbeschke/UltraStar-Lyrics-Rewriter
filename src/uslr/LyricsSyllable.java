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

    public int getLineNumber() {
	return line.getLineNumber();
    }

    private LyricsLine line; /// The line this syllable belongs to.
    private String prefix; /// Everything in the line before the actual lyrics.
    private String lyrics; /// The bit of lyrics in this syllable.
}
