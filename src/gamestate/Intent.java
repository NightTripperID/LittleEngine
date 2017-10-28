package gamestate;

/**
 * Object representing the bridge between GameStates. The Server uses an Intent to instantiate a push a new GameState
 * onto the GameStateManager's GameState stack.
 */
public class Intent extends Bundle {

    private Class<? extends GameState> gameStateClass;

    /**
     * Creates a new Intent object and sets the given GameState class metadata.
     * @param gameStateClass the GameState class metadata associated with the Intent.
     */
    public Intent(Class<? extends  GameState> gameStateClass) {
        this.gameStateClass = gameStateClass;
    }

    /**
     * Returns the GameState class metadata associated with the Intent. This is used by the server and should probably
     * never be called by the API user.
     * @return the GameState class metadata associated with the Intent.
     */
    public final Class<? extends GameState> getGsc() {
        return gameStateClass;
    }
}
