/**
 * CS2852 - 051
 * Spring 2016
 * Lab 5 - Network Simulator
 * Name: Connor Christie
 * Created: Apr 19, 2016
 */
package christieck;

import christieck.exceptions.NetworkException;
import christieck.interfaces.INetwork;
import christieck.interfaces.ISender;

import java.io.*;

/**
 * The packet sender, sends packets to the network
 */
public class Sender implements ISender
{
    private INetwork network;
    private File file;

    private int address;

    private int lastSequenceNumber = 0;
    private boolean isSending = true;

    private BufferedReader fileReader;

    public Sender(INetwork network, int address, File file)
    {
        this.network = network;
        this.address = address;
        this.file = file;
    }

    @Override
    public void start() throws FileNotFoundException
    {
        System.out.println("Started");

        fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    @Override
    public void tick()
    {
        if (!isSending) return;

        char[] buffer = new char[1500];

        try
        {
            int sizeRead = fileReader.read(buffer);

            if (sizeRead == -1)
            {
                isSending = false;
                fileReader.close();

                return;
            }

            network.send(new Packet(lastSequenceNumber++, buffer, sizeRead), sizeRead);
        } catch (IOException e)
        {
            System.out.println("Unable to read file stream: " + e.getMessage());
        } catch (NetworkException e)
        {
            System.out.println("Unable to send packet, retrying.");
        }
    }

    @Override
    public int address()
    {
        return address;
    }

    @Override
    public boolean isSending()
    {
        return isSending;
    }
}
