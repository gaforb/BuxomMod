package BuxomMod;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BuxomManager {
    public static int minSize = 0;
    public static int baseSize = 0;
    public static int size = 0;
    public static int buxomCounterThisTurn = 0;
    public static int buxomGainedThisTurn = 0;
    public static int buxomLostThisTurn = 0;
    public static void grow(int amount) {
        size += amount;
        buxomCounterThisTurn++;
        buxomGainedThisTurn += amount;
    }
    public static void shrink(int amount) {
        size -= amount;
    }
    public static int getSize() {
        return size;
    }

}
