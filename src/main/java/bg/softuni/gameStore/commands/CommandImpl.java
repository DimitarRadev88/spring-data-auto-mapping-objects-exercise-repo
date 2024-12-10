package bg.softuni.gameStore.commands;

import org.springframework.stereotype.Component;

@Component
public abstract class CommandImpl implements Command {
    private String[] commandData;

    protected CommandImpl() {
    }

    @Override
    public void setData(String[] commandData) {
        this.commandData = commandData;
    }

    public String[] getCommandData() {
        return commandData;
    }
}
