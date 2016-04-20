/**
 * CS2852 - 051
 * Spring 2016
 * Lab 5 - Network Simulator
 * Name: Connor Christie
 * Created: Apr 19, 2016
 */
package christieck.interfaces;

import christieck.Packet;
import christieck.exceptions.NetworkException;

import java.io.IOException;

/**
 * A network, the "middle man" for a sender and receiver
 */
public interface INetwork extends ITask
{
    /**
     * Starts the network
     */
    void start();

    /**
     * Connects the specified sender to the network
     *
     * @param sender The sender to connect
     * @throws NetworkException If the sender with the same address is already connected
     */
    void connect(ISender sender) throws NetworkException, IOException;

    /**
     * Connects the specified receiver to the network
     *
     * @param receiver The receiver to connect
     * @throws NetworkException If the receiver with the same address is already connected
     */
    void connect(IReceiver receiver) throws NetworkException, IOException;

    /**
     * Sends the packet to the receiver
     *
     * @param packet The packet to send
     * @param size The size of the packet
     * @throws NetworkException When there are too many packets in the queue
     */
    void send(Packet packet, int size) throws NetworkException;

    /**
     * Receives a packet from the network
     *
     * @param size The size available to receive
     */
    Packet receive(int size);

    /**
     * Checks whether there are any senders that are still trying to send packets
     *
     * @return Whether any senders are still sending packets
     */
    boolean isConnected();

    /**
     * Checks whether the sender is done sending packets
     *
     * @return Whether the sender has stopped sending packets
     */
    boolean isClosed();

    /**
     * Acknowledges a packet has been received
     *
     * @param packet The packet
     */
    void acknowledgePacket(Packet packet);
}
