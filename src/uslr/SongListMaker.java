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

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author sebastian
 */
public class SongListMaker {
    public class SongComparator implements Comparator<Song> {

        public int compare(Song o1, Song o2) {
            String artist1 = o1.getArtist();
            String artist2 = o2.getArtist();
            if(artist1 == null) {
                if(artist2 == null) {
                    return 0;
                }
                return -1;
            }
            if(artist2 == null) {
                return 1;
            }
            return artist1.compareTo(artist2);
        }

    }

    public SongListMaker(File songDir, File outDir) {
        this.songDir = songDir;
        this.outDir = outDir;
    }

    public void create() {
        songs = new ArrayList<Song>();
        readSongs(songDir);
        USLRApp.getApplication().log("Loaded " + songs.size() + " songs");

        Collections.sort(songs, new SongComparator());

        // Break down into different lists by language
        HashMap<String, ArrayList<Song> > languageBuckets
                = new HashMap<String, ArrayList<Song> >();
        for(Iterator<Song> it = songs.iterator(); it.hasNext();) {
            Song song = it.next();
            String lang = song.getLanguage();
            assert(lang != null);

            if(!languageBuckets.containsKey(lang)) {
                languageBuckets.put(lang, new ArrayList<Song>());
            }
            ArrayList<Song> bucket = languageBuckets.get(lang);
            bucket.add(song);
        }

        for(Iterator<String> langIt = languageBuckets.keySet().iterator(); langIt.hasNext();) {
            String language = langIt.next();
            ArrayList<Song> bucket = languageBuckets.get(language);
            writeList(bucket, "Songs in " + language);
        }
    }

    public void readSongs(File dir) {
        assert(dir.isDirectory());

        File[] children = dir.listFiles();
        for(int i = 0; i < children.length; i++) {
            File child = children[i];
            if(child.isDirectory()) {
                readSongs(child);
            }
            else if(child.isFile()) {
                String filename = child.getName();
                String ext = filename.substring(filename.lastIndexOf('.')+1, filename.length());
                if(ext.equals("txt")) {
                    try {
                        Song song = new Song(child);
                        if(song.getArtist() == null) {
                            log("Song " + child.getPath() + " is missing artist information.");
                            song.setArtist("Unknown Artist");
                        }
                        if(song.getTitle() == null) {
                            log("Song " + child.getPath() + " is missing title information.");
                            song.setTitle("Unknown Title");
                        }
                        if(song.getLanguage() == null) {
                            log("Song " + child.getPath() + " is missing language information.");
                            song.setLanguage("Unknown Language");
                        }

                        // just my personal hack...
                        String lang = song.getLanguage();
                        if(!lang.equals("English") && !lang.equals("German")
                                && !lang.equals("Korean")) {
                            song.setLanguage("other languages");
                        }

                        songs.add(song);
                    }
                    catch(Exception x) {
                        USLRApp.getApplication().log(x.getLocalizedMessage() + " in " + filename);
                    }
                }
            }
        }
    }

    public void writeList(List<Song> list, String title) {
        File outFile = new File(outDir.getPath() + outDir.separator + title + ".html");
        if(outFile.exists()) {
            // todo ask
            outFile.delete();
        }
        try {
            String header =
                    "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv='content-type' content='text/html; charset=UTF-8'>" +
                    "<title>" + title + "</title>\n" +
                    "<link rel='stylesheet' type='text/css' href='style.css' />" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>" + title + "</h1>\n" +
                    "<table>\n";
            String footer =
                    "</table>\n" +
                    "</body>\n" +
                    "</html>";
            FileWriter writer = new FileWriter(outFile);
            writer.write(header);

            for(Iterator<Song> it = list.iterator(); it.hasNext();) {
                Song song = it.next();

                String line =
                        "<tr>\n" +
                        "<td class='artist'>" + song.getArtist() + "</td>\n" +
                        "<td class='title'>" + song.getTitle() + "</td>\n" +
                        "</tr>\n";
                writer.write(line);
            }

            writer.write(footer);
            writer.close();
            log("Wrote list: " + outFile.getPath());
        }
        catch(Exception x) {
            log(x.getLocalizedMessage());
        }
    }

    private void log(String message) {
        USLRApp.getApplication().log(message);
    }

    private ArrayList<Song> songs;
    private File songDir;
    private File outDir;
}
