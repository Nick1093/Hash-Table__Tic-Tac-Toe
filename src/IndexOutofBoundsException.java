/**
 * exception used when trying to access out of bounds location in the array
 * @author nicklam
 *
 */
public class IndexOutofBoundsException extends Exception {
	
	public IndexOutofBoundsException(String index) {
		super(index);
	}
}
