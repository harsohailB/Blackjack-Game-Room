package ServerController;

/**
 * Constants interface to store string and messages that con repetitively sent
 */
public interface Constants {

    static final String BREAK_LINE =      "**********************************************";
    static final String NEW_CHAT_HEADER = "*************** New Chat Message *************";
    static final String ACCOUNT_BALANCE = "accountBalance";
    static final String READY = "ready";
    static final String WAITING_FOR_PLAYERS = "Waiting for players";
    static final String WELCOME_MESSAGE = BREAK_LINE + "\n"
                                        + "************ Welcome to Blackjack! ***********\n"
                                        + BREAK_LINE + "\n"
                                        + "- Game will start when lobby is filled\n"
                                        + "- Type '/all' to chat\n"
                                        + "- Give hit or stand decision when prompted\n"
                                        + BREAK_LINE + "\n"
                                        + "******************* G L H F ******************\n"
                                        + BREAK_LINE + "\n";
    static final String VERIFIED = "verified";
    static final String TURN = "turn";
    static final String HIT = "hit";
    static final String STAND = "stand";
    static final String DOUBLE = "double";
    static final String RAISE = "raise";
    static final String OBSERVER = "observer";
    static final String OBSERVER_PASSWORD = "123";
    static final String PING = "ping";
    static final String CHECK = "check";
    static final String PASSED = "passed";
    static final String FAILED = "failed";

}
