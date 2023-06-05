package com.zeroplus.codeutility.utility.segmented.kotlin.font

interface IconSet {

    /**
     * Returns the unicode character for the current Font Icon.
     *
     * @return the unicode character
     */
    fun unicodeForKey(key: CharSequence?): CharSequence?


    /**
     * Returns the icon code for the current Font Icon.
     *
     * @return the icon code
     */
    fun iconCodeForAttrIndex(index: Int): CharSequence?

    /**
     * Specifies the location that the font file resides in, starting from the assets directory
     * e.g."fontawesome-webfont.ttf"
     *
     * @return the font path
     */
    fun fontPath(): CharSequence?
}