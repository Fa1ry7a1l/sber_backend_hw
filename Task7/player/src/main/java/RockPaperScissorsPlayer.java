public class RockPaperScissorsPlayer implements RockPaperScissorsPlayable {
    @Override
    public RockPaperScissorsEnum play() {

        var allVariants = RockPaperScissorsEnum.values();

        int selectedIndex = (int) (Math.random() * 10000) % allVariants.length;

        return allVariants[selectedIndex];
    }

}
