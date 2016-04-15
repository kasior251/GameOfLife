package model;

/**
 * Created by kasia on 14.04.2016.
 */
public class TooLargePatternException extends Exception {

    public TooLargePatternException() {
        super("The pattern is too large");
    }
}
