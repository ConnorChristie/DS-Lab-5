package christieck;

import christieck.exceptions.NetworkException;
import christieck.interfaces.INetwork;

public class Simulator
{
    private INetwork network;

    /**
     * How long a time unit is in milliseconds
     */
    private static final int TIME_UNIT_MS = 500;

    private Simulator()
    {
        network = new Network();
        network.start();

        connectSenderReceiver(2, 4);
        //connectSenderReceiver(3, 5);

        tickNetwork();
    }

    /**
     * Connects the sender and receiver with the specified addresses to the network
     *
     * @param senderAddress The address of the sender
     * @param receiverAddress The address of the receiver
     */
    private void connectSenderReceiver(int senderAddress, int receiverAddress)
    {
        try
        {
            network.connect(new Sender(network, senderAddress));
            network.connect(new Receiver(network, receiverAddress));
        } catch (NetworkException e)
        {
            System.out.println("Unable to connect the sender and receiver.");
        }
    }

    /**
     * Ticks the network 1 units worth of work
     */
    private void tickNetwork()
    {
        while (network.isConnected())
        {
            network.tick();

            try
            {
                Thread.sleep(TIME_UNIT_MS);
            } catch (InterruptedException e) { }
        }
    }

    public static void main(String[] args)
    {
        new Simulator();
    }
}
