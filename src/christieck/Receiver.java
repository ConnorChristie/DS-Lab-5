/**
 * CS2852 - 051
 * Spring 2016
 * Lab 5 - Network Simulator
 * Name: Connor Christie
 * Created: Apr 19, 2016
 */
package christieck;

import christieck.interfaces.INetwork;
import christieck.interfaces.IReceiver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The packet receiver, receives the packets and then reconstructs them into a new file
 */
public class Receiver implements IReceiver
{
    private INetwork network;
    private File file;

    private int address;
    private boolean isDone;

    private BufferedWriter fileWriter;
    private Map<Integer, Packet> outputBuffer;

    public Receiver(INetwork network, int address, File file)
    {
        this.network = network;
        this.address = address;
        this.file = file;
    }

    @Override
    public void start() throws IOException
    {
        fileWriter = new BufferedWriter(new FileWriter(file));
        outputBuffer = new HashMap<>();
    }

    @Override
    public void tick()
    {
        Packet packet = network.receive(1500);

        if (packet != null)
        {
            System.out.println("Received packet: " + packet.getSequenceNumber());

            network.acknowledgePacket(packet);
            outputBuffer.put(packet.getSequenceNumber(), packet);
        }

        if (network.isClosed())
        {
            try
            {
                String out = outputBuffer.entrySet().stream()
                    .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
                    .map(this::packetToString)
                    .reduce((acc, e) -> acc + e)
                    .get();

                fileWriter.write(out);
                fileWriter.flush();
                fileWriter.close();

                isDone = true;
            } catch (IOException e)
            {
                System.out.println("Failed to write packet to file: " + e.getMessage());
            }
        }
    }

    /**
     * Converts a packets data to a string
     *
     * @param packet The packet
     * @return The string of the characters
     */
    private String packetToString(Map.Entry<Integer, Packet> packet)
    {
        return new String(Arrays.copyOf(packet.getValue().getData(), packet.getValue().getSize()));
    }

    @Override
    public int address()
    {
        return address;
    }

    @Override
    public boolean isDone()
    {
        return isDone;
    }
}
