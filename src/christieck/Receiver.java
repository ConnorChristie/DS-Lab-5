package christieck;

import christieck.interfaces.INetwork;
import christieck.interfaces.IReceiver;

public class Receiver implements IReceiver
{
    private INetwork network;
    private int address;

    public Receiver(INetwork network, int address)
    {
        this.network = network;
        this.address = address;
    }

    @Override
    public int address()
    {
        return address;
    }

    @Override
    public void start()
    {
        // Create new file and open stream
    }

    @Override
    public void tick()
    {
        Packet packet = network.receive(address, 1500);

        if (packet != null) System.out.println("Received packet: " + packet.getSequenceNumber());
    }
}
