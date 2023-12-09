package BuxomMod.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import BuxomMod.powers.CommonPower;
import basemod.BaseMod;
import basemod.ConsoleTargetedPower;
import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class GrowCommand extends ConsoleCommand {
    public GrowCommand() {
        this.requiresPlayer = true;
        this.minExtraTokens = 1;
        this.maxExtraTokens = 1;
    }

    public void execute(String[] tokens, int depth) {
        int amount = ConvertHelper.tryParseInt(tokens[1], 0);
        if (tokens.length != 2) {
            cmdBuxomHelp();
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CommonPower(AbstractDungeon.player, AbstractDungeon.player, amount), amount));
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        if (tokens[depth].matches("\\d+")) {
            complete = true;
        }

        return ConsoleCommand.smallNumbers();
    }

    private static void cmdBuxomHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("[amt]");
    }
}

