package bg.softuni.gameStore.commands;

public interface Command {

    void execute();

    void setData(String[] data);

}
