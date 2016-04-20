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
import christieck.interfaces.IReceiver;
import christieck.interfaces.ISender;

import java.io.File;
import java.io.IOException;

/**
 * The main entry point, runs the whole program and ticks the network every 500 ms
 */
public class Simulator
{
    /**
     * How long a time unit is in milliseconds
     */
    private static final int TIME_UNIT_MS = 500;

    private static final String INPUT_FILE = "story.txt";
    private static final String OUTPUT_FILE = "story_recv.txt";

    private Simulator() throws IOException
    {
        INetwork network = new Network();
        //INetwork network1 = new Network();

        network.start();
        //network1.start();

        connectSenderReceiver(network, new Sender(network, 2, new File(INPUT_FILE)), new Receiver(network, 4, new File(OUTPUT_FILE)));
        //connectSenderReceiver(network1, new Sender(network1, 4, new File("story2.txt")), new Receiver(network1, 5, new File("story2_recv.txt")));

        tickNetwork(network);
        //tickNetwork(network1);
    }

    /**
     * Connects the sender and receiver with the specified addresses to the network
     *
     * @param network The network to connect to
     * @param sender The sender
     * @param receiver The receiver
     */
    private void connectSenderReceiver(INetwork network, ISender sender, IReceiver receiver)
    {
        try
        {
            network.connect(sender);
            network.connect(receiver);
        } catch (NetworkException e)
        {
            System.out.println("Unable to connect the sender and receiver.");
        } catch (IOException e)
        {
            System.out.println("Error reading file stream: " + e.getMessage());
        }
    }

    /**
     * Ticks the network 1 units worth of work
     *
     * @param network The network to tick
     */
    private void tickNetwork(INetwork network)
    {
        while (network.isConnected())
        {
            network.tick();

            try
            {
                Thread.sleep(TIME_UNIT_MS);
            } catch (InterruptedException e) { }
        }

        System.out.println("Done sending and receiving.");
    }

    public static void main(String[] args) throws IOException
    {
        new Simulator();
    }
}
