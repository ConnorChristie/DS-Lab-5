package christieck;

public class Packet
{
    private long sequenceNumber;

    private char[] data;
    private int size;

    public Packet(long sequenceNumber, char[] data, int size)
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
    public long getSequenceNumber()
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
