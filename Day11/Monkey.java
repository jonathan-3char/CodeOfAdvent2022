package Day11;

import java.util.ArrayList;
import java.util.List;

public class Monkey {
    private List<Long> items;
    private final Long divisible, trueMonkey, falseMonkey;
    private long inspections;
    private final String operation, amount;

    public Monkey(ArrayList<Long> startingItems, String operation, String amount, int divisible, int trueMonkey, int falseMonkey) {
        items = startingItems;

        this.amount = amount;
        this.operation = operation;
        this.divisible = (long) divisible;
        this.trueMonkey = (long) trueMonkey;
        this.falseMonkey = (long) falseMonkey;
        inspections = 0;
    }

    public Long getDivisible() {
        return divisible;
    }

    public int whoToThrow(long item) {
        if (item % divisible == 0)
            return Math.toIntExact(trueMonkey);
        return Math.toIntExact(falseMonkey);
    }

    public boolean isItemsEmpty() {
        return items.isEmpty();
    }

    public Long inspectItem() {
        inspections++;

        Long item = items.remove(0);

        if (operation.equals("+"))
            return item + Long.parseLong(amount);
        if (amount.equals("old"))
            return item * item;

        return item * Long.parseLong(amount);
    }

    public void receiveItem(long item) {
        items.add(item);
    }

    public long getInspections() {
        return inspections;
    }
}
