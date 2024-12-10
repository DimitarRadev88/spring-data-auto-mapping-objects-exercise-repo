package bg.softuni.gameStore.dtos;

import java.util.List;

public class UserOwnedGamesDto {
    private List<String> games;

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), games);
    }
}
