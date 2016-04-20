/**
 * CS2852 - 051
 * Spring 2016
 * Lab 5 - Network Simulator
 * Name: Connor Christie
 * Created: Apr 19, 2016
 */
package christieck.interfaces;

import java.io.IOException;

/**
 * A receiver, receives the packets and then reconstructs them into a new file
 */
public interface IReceiver extends ITask
{
    /**
     * Starts the receiver
     *
     * @throws IOException
     */
    void start() throws IOException;

    /**
     * The address of the receiver
     *
     * @return The address
     */
    int address();

    /**
     * If the receiver is done writing the file
     *
     * @return Whether the receiver is done writing the file or not
     */
    boolean isDone();
}
