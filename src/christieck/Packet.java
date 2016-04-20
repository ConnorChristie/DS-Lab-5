/**
 * CS2852 - 051
 * Spring 2016
 * Lab 5 - Network Simulator
 * Name: Connor Christie
 * Created: Apr 19, 2016
 */
package christieck;

/**
 * The packet, contains the sequence number, data and size of the data
 */
public class Packet
{
    private int sequenceNumber;

    private char[] data;
    private int size;

    public Packet(int sequenceNumber, char[] data, int size)
    {
        this.sequenceNumber = sequenceNumber;

        this.data = data;
        this.size = size;
    }

    /**
     * Gets the size of the packet
     *
     * @return The size of the packet
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Gets the sequence number of the packet
     *
     * @return The sequence number of the packet
     */
    public int getSequenceNumber()
    {
        return sequenceNumber;
    }

    /**
     * Gets the data of the packet
     *
     * @return The data of the packet
     */
    public char[] getData()
    {
        return data;
    }
}
