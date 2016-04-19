package christieck.interfaces;

public interface ITask
{
    /**
     * Performs one unit of work
     */
    void tick();

    /**
     * Starts the task
     */
    void start();
}
