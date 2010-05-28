/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uslr;

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
    
    

    private String tag;
    private String value;
}
