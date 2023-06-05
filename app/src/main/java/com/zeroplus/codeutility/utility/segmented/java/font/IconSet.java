package com.zeroplus.codeutility.utility.segmented.java.font;

public interface IconSet {

    /**
     * Returns the unicode character for the current Font Icon.
     *
     * @return the unicode character
     */
    CharSequence unicodeForKey(CharSequence key);


    /**
     * Returns the icon code for the current Font Icon.
     *
     * @return the icon code
     */
    CharSequence iconCodeForAttrIndex(int index);

    /**
     * Specifies the location that the font file resides in, starting from the assets directory
     * e.g."fontawesome-webfont.ttf"
     *
     * @return the font path
     */
    CharSequence fontPath();
}
