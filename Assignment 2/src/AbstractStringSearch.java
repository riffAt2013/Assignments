public abstract class AbstractStringSearch {
    /**
     * Preprocess the pattern string.
     * @param pattern The pattern string.
     */
    void preprocessPattern(String pattern) {}

    /**
     * Search for the first occurrence of the pattern string in the text string.
     * @param text The text string to be searched.
     * @return The index of the first occurrence of the pattern string in the text string, or -1 if the pattern string is not found.
     */
    int search(String text) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    int search(String pattern, String text) {
        preprocessPattern(pattern);
        return search(text);
    }
}
