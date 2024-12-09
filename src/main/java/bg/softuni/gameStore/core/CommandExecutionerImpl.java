package bg.softuni.gameStore.core;

import bg.softuni.gameStore.commands.Command;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class CommandExecutionerImpl implements CommandExecutioner {

    private Deque<String[]> commandsData;

    public CommandExecutionerImpl() {
        this.commandsData = new ArrayDeque<>();
    }

    @Override
    public void add(String[] data) {
        this.commandsData.push(data);
    }

    @Override
    public void execute(Command command) {
        if (!this.commandsData.isEmpty()) {
            command.setData(this.commandsData.pop());
        }

        command.execute();
    }
}
