package christieck;

import christieck.exceptions.NetworkException;
import christieck.interfaces.INetwork;
import christieck.interfaces.IReceiver;
import christieck.interfaces.ISender;

import java.util.*;

public class Network implements INetwork
{
    private Queue<NetworkPacket> outboundQueue;

    private ISender sender;
    private IReceiver receiver;

    @Override
    public void start()
    {
        outboundQueue = new LinkedList<>();
    }

    @Override
    public void connect(ISender sender) throws NetworkException
    {
        if (this.sender != null)
        {
            throw new NetworkException("This sender is already connected.");
        }

        this.sender = sender;
        sender.start();
    }

    @Override
    public void connect(IReceiver receiver) throws NetworkException
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
        // We really don't have to do any work here...

        sender.tick();
        receiver.tick();
    }

    @Override
    public void send(Packet packet, int size) throws NetworkException
    {
        if (outboundQueue.size() >= 35)
        {
            throw new NetworkException("Too many packets waiting to be sent, retry.");
        }

        outboundQueue.offer(new NetworkPacket(packet));
    }

    @Override
    public Packet receive(int address, int size)
    {
        NetworkPacket packet = outboundQueue.peek();

        if (packet == null || !packet.isReady())
        {
            return null;
        }

        outboundQueue.poll();

        if (packet.isLost())
        {
            // If the packet is lost, still poll it from the queue but return null
            return null;
        }

        return packet.getPacket();
    }

    @Override
    public boolean isConnected()
    {
        return sender.isSending();
    }

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
            return random.nextInt(5) == 0;
        }
    }
}
