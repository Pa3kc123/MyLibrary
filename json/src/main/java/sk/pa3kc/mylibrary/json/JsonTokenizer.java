package sk.pa3kc.mylibrary.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

class JsonTokenizer {
    private final Reader reader;
    private boolean eofReached = false;
    private char prevChar = 0;
    private char prevClearChar = 0;
    private char currChar = 0;

    private int index = 0;
    private long charIndex = 1;
    private long lineIndex = 1;
    private long prevLineCharIndex = 0;

    JsonTokenizer(Reader reader) {
        this.reader = reader;
    }
    JsonTokenizer(String string) {
        this(new StringReader(string));
    }

    public char getPrevChar() {
        return this.prevChar;
    }
    public char getPrevClearChar() {
        return this.prevClearChar;
    }
    public char getCurrChar() {
        return this.currChar;
    }
    public int getIndex() {
        return this.index - 1;
    }
    public long getLineIndex() {
        return this.lineIndex;
    }
    public long getCharIndex() {
        return this.charIndex;
    }
    public boolean isEofReached() {
        return this.eofReached;
    }

    /**
     * @return Next character in stream, INCLUDING whitespace
     * @throws JsonException when error with reading from stream occurs
     */
    public char nextChar() throws JsonException {
        int c;

        try {
            c = this.reader.read();
        } catch (IOException e) {
            throw new JsonException(e);
        }

        if (c == -1) {
            this.eofReached = true;
            return 0;
        }

        this.prevChar = this.currChar;
        this.currChar = (char)c;

        return this.currChar;
    }

    public char nextClearChar() throws JsonException {
        char c;

        if (this.currChar > ' ') {
            this.prevClearChar = this.currChar;
        }

        for (c = this.nextChar(); c <= ' '; c = nextChar()) {
            if (c == 0) { // End of stream
                return 0;
            }

            this.index++;
            switch (c) {
                case '\r':
                    this.lineIndex++;
                    this.prevLineCharIndex = this.charIndex;
                    this.charIndex = 0;
                break;

                case '\n':
                    if (this.currChar != '\r') {
                        this.lineIndex++;
                        this.prevLineCharIndex = this.charIndex;
                    }
                    this.charIndex = 0;
                break;

                default:
                    this.charIndex++;
                break;
            }
        }

        this.currChar = c;

        return this.currChar;
    }

    public char[] nextCharRange(int n) throws JsonException {
        final char[] arr = new char[n];

        for (int i = 0; i < n; i++) {
            arr[i] = this.nextChar();
        }

        return arr;
    }

    /**
     * Get the hex value of a character (base16).
     * @param c A character between '0' and '9' or between 'A' and 'F' or
     * between 'a' and 'f'.
     * @return  An int between 0 and 15, or -1 if c was not a hex digit.
     */
    public static int dehexchar(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - ('A' - 10);
        }
        if (c >= 'a' && c <= 'f') {
            return c - ('a' - 10);
        }
        return -1;
    }
}
