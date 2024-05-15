public enum RockPaperScissorsEnum {
    ROCK,
    PAPER,
    SCISSORS;

    public static boolean isFirstWin(RockPaperScissorsEnum firstVal, RockPaperScissorsEnum secondVal) {
        switch (firstVal) {
            case ROCK -> {
                return secondVal == SCISSORS;
            }
            case PAPER -> {
                return secondVal == ROCK;
            }
            case SCISSORS -> {
                return secondVal == PAPER;
            }
        }
        return false;
    }

}
