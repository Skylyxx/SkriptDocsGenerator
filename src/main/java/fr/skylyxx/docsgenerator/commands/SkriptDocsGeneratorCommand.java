package fr.skylyxx.docsgenerator.commands;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.util.Pair;
import fr.skylyxx.docsgenerator.SkriptDocsGenerator;
import fr.skylyxx.docsgenerator.utils.DocBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SkriptDocsGeneratorCommand implements CommandExecutor {

    private final SkriptDocsGenerator skriptDocsGenerator = SkriptDocsGenerator.getPlugin(SkriptDocsGenerator.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Skript.isAcceptRegistrations()) {
            sender.sendMessage(skriptDocsGenerator.getColored("&aSkriptDocsGenerator &6» &cSkript hasn't finished addon registration !"));
            return true;
        }
        for (SkriptAddon addon : Skript.getAddons()) {
            try {
                DocBuilder.generateAddonDoc(new Pair<>(addon.plugin.getClass().getName(), addon));
                sender.sendMessage(skriptDocsGenerator.getColored("&aSkriptDocsGenerator &6» &aDocumentation generated for &2" + addon.getName()));
            } catch (Exception e) {
                sender.sendMessage(skriptDocsGenerator.getColored("&aSkriptDocsGenerator &6» &cAn error has occurred while generating documentation for &4" + addon.getName() + "&c: &4" + e.getMessage()));
            }
        }
        return true;
    }
}
