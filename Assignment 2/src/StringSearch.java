/**
 * This class implements string searching using the Knuth-Morris-Pratt algorithm
 * with support for wildcard characters represented by '?'.
 * The algorithm efficiently searches for a pattern in a given text, supporting wildcard character
 * matching during the search. It utilizes the Partial Match Table (PMT) to optimize the search process.
 *
 * @author Rifat Bin Masud
 * @version 0.1
 */
public class StringSearch extends AbstractStringSearch {
    // The Partial Match Table (PMT) used for efficient pattern matching.
    private int[] pmt;

    // The pattern to search for in the text
    private String pattern;

    /**
     * Searches for a pattern in the given text.
     *
     * @param text The text in which to search for the pattern.
     * @return The index of the first occurrence of the pattern in the text, or -1 if not found.
     */
    @Override
    public int search(String text) {
        int textLen = text.length();
        int patLen = pattern.length();
        int textIterator = 0;
        int patIterator = 0;

        while (textIterator < textLen) {
            // check for identical character or a wildcard '?'
            if (patIterator < patLen && ( pattern.charAt(patIterator) == text.charAt(textIterator) || pattern.charAt(patIterator) == '?')) {
                textIterator = textIterator + 1;
                patIterator = patIterator + 1;
            } else {
                if (patIterator == patLen) {
                    return textIterator - patIterator;
                }
                else if (patIterator > 0) {
                    patIterator = pmt[patIterator - 1];
                }else {
                    textIterator++;
                }
            }
        }

        return (patIterator == patLen) ? textIterator - patIterator : -1;
    }

    /**
     * Preprocesses the pattern to construct the Partial Match Table (PMT).
     *
     * @param pattern The pattern to preprocess.
     */
    @Override
    public void preprocessPattern(String pattern) {
        this.pattern = pattern;
        int patLen = pattern.length();
        pmt = new int[patLen];
        int patIterator = 1;

        int len = 0;

        while (patIterator < patLen) {
            if (pattern.charAt(patIterator) == pattern.charAt(len) || pattern.charAt(patIterator) == '?') {
                len++;
                pmt[patIterator] = len;
                patIterator++;
            } else {
                if (len != 0) {
                    len = pmt[len - 1];
                } else {
                    pmt[patIterator] = 0;
                    patIterator++;
                }
            }
        }
    }
}
