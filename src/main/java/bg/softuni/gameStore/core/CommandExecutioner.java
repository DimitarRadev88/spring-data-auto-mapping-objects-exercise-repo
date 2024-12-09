package bg.softuni.gameStore.core;

import bg.softuni.gameStore.commands.Command;

public interface CommandExecutioner {

    void add(String[] data);

    void execute(Command command);

}
