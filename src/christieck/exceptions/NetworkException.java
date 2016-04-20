/**
 * CS2852 - 051
 * Spring 2016
 * Lab 5 - Network Simulator
 * Name: Connor Christie
 * Created: Apr 19, 2016
 */
package christieck.exceptions;

/**
 * A general network exception
 */
public class NetworkException extends Exception
{
    public NetworkException() {}

    public NetworkException(String message)
    {
        super(message);
    }
}
