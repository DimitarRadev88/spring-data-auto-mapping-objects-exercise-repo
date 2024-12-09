package bg.softuni.gameStore.core;

import javax.naming.OperationNotSupportedException;

public interface CommandParser {

    void parse(String input) throws OperationNotSupportedException;

}
