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
 * A sender, sends packets to the network
 */
public interface ISender extends ITask
{
    /**
     * Starts the sender
     *
     * @throws IOException
     */
    void start() throws IOException;

    /**
     * The address of the sender
     *
     * @return The address
     */
    int address();

    /**
     * Whether the sender is still sending packets or not
     *
     * @return If the sender is still sending packets
     */
    boolean isSending();
}
