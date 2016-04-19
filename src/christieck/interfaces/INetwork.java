package christieck.interfaces;

import christieck.Packet;
import christieck.exceptions.NetworkException;

public interface INetwork extends ITask
{
    /**
     * Connects the specified sender to the network
     *
     * @param sender The sender to connect
     * @throws NetworkException If the sender with the same address is already connected
     */
    void connect(ISender sender) throws NetworkException;

    /**
     * Connects the specified receiver to the network
     *
     * @param receiver The receiver to connect
     * @throws NetworkException If the receiver with the same address is already connected
     */
    void connect(IReceiver receiver) throws NetworkException;

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
     * @param address The address of the receiver receiving the packets
     * @param size The size available to receive
     */
    Packet receive(int address, int size);

    /**
     * Checks whether there are any senders that are still trying to send packets
     *
     * @return Whether any senders are still sending packets
     */
    boolean isConnected();
}
