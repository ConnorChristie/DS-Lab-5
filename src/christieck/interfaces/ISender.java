package christieck.interfaces;

public interface ISender extends ITask
{
    /**
     * The address of the sender
     *
     * @return The address
     */
    int address();

    /**
     * Whether the sender is still sending packets or not
     *
     * @return If the sender is still sending packets
     */
    boolean isSending();
}
