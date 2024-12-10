package bg.softuni.gameStore.commands;

public interface Command {

    String execute();

    void setData(String[] data);

}
