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
public class MetaDataLine {

    public MetaDataLine(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public void write(BufferedWriter writer) throws IOException {
        writer.write("#" + tag + ":" + value + "\n");
    }

    private String tag;
    private String value;
}
