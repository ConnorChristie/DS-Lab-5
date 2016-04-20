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

import java.io.IOException;
import java.util.*;

/**
 * The main network class, the "middle man" for a sender and receiver
 */
public class Network implements INetwork
{
    private Queue<NetworkPacket> outboundQueue;
    private Map<Packet, Integer> sentPackets;

    private ISender sender;
    private IReceiver receiver;

    @Override
    public void start()
    {
        outboundQueue = new LinkedList<>();
        sentPackets = new HashMap<>();
    }

    @Override
    public void connect(ISender sender) throws NetworkException, IOException
    {
        if (this.sender != null)
        {
            throw new NetworkException("This sender is already connected.");
        }

        this.sender = sender;
        sender.start();
    }

    @Override
    public void connect(IReceiver receiver) throws NetworkException, IOException
    {
        if (this.receiver != null)
        {
            throw new NetworkException("This receiver is already connected.");
        }

        this.receiver = receiver;
        receiver.start();
    }

    @Override
    public void tick()
    {
        if (sender == null || receiver == null) return;

        sender.tick();
        receiver.tick();

        checkLostPackets();
    }

    /**
     * Checks for lost packets, resend packet if it has been more than 4 ticks
     */
    private void checkLostPackets()
    {
        for (Iterator<Map.Entry<Packet, Integer>> iter = sentPackets.entrySet().iterator(); iter.hasNext();)
        {
            Map.Entry<Packet, Integer> packet = iter.next();

            if (packet.getValue() > 4)
            {
                iter.remove();
                outboundQueue.offer(new NetworkPacket(packet.getKey()));

                System.out.println("Receiver did not receive packet, resending.");
            } else
            {
                sentPackets.put(packet.getKey(), packet.getValue() + 1);
            }
        }
    }

    @Override
    public void send(Packet packet, int size) throws NetworkException
    {
        if (outboundQueue.size() >= 32)
        {
            throw new NetworkException("Too many packets waiting to be sent, retry.");
        }

        outboundQueue.offer(new NetworkPacket(packet));
    }

    @Override
    public Packet receive(int size)
    {
        NetworkPacket packet = outboundQueue.peek();

        if (packet == null)
        {
            return null;
        }

        sentPackets.put(packet.getPacket(), 0);

        if (packet.isLost())
        {
            outboundQueue.poll();
            return null;
        }

        if (!packet.isReady())
        {
            return null;
        }

        outboundQueue.poll();
        return packet.getPacket();
    }

    @Override
    public boolean isConnected()
    {
        return sender != null && (sender.isSending() || !receiver.isDone() || outboundQueue.size() > 0 || sentPackets.size() > 0);
    }

    @Override
    public boolean isClosed()
    {
        return !sender.isSending() && outboundQueue.size() == 0 && sentPackets.size() == 0;
    }

    @Override
    public void acknowledgePacket(Packet packet)
    {
        sentPackets.remove(packet);
    }

    /**
     * A network specific packet, contains some helper methods for the simulator
     */
    private class NetworkPacket
    {
        private Packet packet;
        private int lifespan;

        private Random random = new Random();

        private NetworkPacket(Packet packet)
        {
            this.packet = packet;
        }

        /**
         * Gets the actual packet
         *
         * @return The packet
         */
        private Packet getPacket()
        {
            return packet;
        }

        /**
         * Checks if the packet is ready to be sent
         * If the lifespan is greater than 3 or it is randomly chosen
         *
         * @return Whether the packet is ready to be sent
         */
        private boolean isReady()
        {
            if (lifespan >= 4 || random.nextInt(4) == 1)
            {
                return true;
            }

            lifespan++;
            return false;
        }

        /**
         * If the packet has been lost, 1% chance this happens
         *
         * @return If the packet has been lost
         */
        private boolean isLost()
        {
            return random.nextInt(100) == 0;
        }
    }
}
