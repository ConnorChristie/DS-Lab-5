package christieck;

import christieck.exceptions.NetworkException;
import christieck.interfaces.INetwork;
import christieck.interfaces.ISender;

public class Sender implements ISender
{
    private INetwork network;
    private int address;

    private long lastSequenceNumber = 0;

    public Sender(INetwork network, int address)
    {
        this.network = network;
        this.address = address;
    }

    @Override
    public void start()
    {
        // Open the file and create the stream

        System.out.println("Started");
    }

    @Override
    public void tick()
    {
        try
        {
            Packet packet = new Packet(lastSequenceNumber++, new char[0], 1500);

            network.send(packet, 1500);
        } catch (NetworkException e)
        {
            System.out.println("Unable to send message, retrying.");
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
        return true;
    }
}
