/**
 * Exception used for put and remove methods in Dictionary class
 * @author nicklam
 */

public class DictionaryException extends Exception {

    public DictionaryException(String key) {

        super(key);
    }
}
