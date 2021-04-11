package fr.skylyxx.docsgenerator.commands;

import ch.njol.skript.doc.*;
import ch.njol.skript.lang.*;
import ch.njol.util.Pair;
import fr.skylyxx.docsgenerator.SkriptDocsGenerator;
import fr.skylyxx.docsgenerator.types.DocumentationElement;
import fr.skylyxx.docsgenerator.types.ElementType;
import fr.skylyxx.docsgenerator.types.JsonDocOutput;
import fr.skylyxx.docsgenerator.utils.DocBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SkriptDocsGeneratorCommand implements CommandExecutor, TabCompleter {

    private SkriptDocsGenerator skriptDocsGenerator = SkriptDocsGenerator.getPlugin(SkriptDocsGenerator.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println(skriptDocsGenerator.getAddons());
        skriptDocsGenerator.getAddons().forEach((s, skriptAddon) -> {
            Bukkit.getScheduler().runTaskAsynchronously(skriptDocsGenerator, () -> {
                DocBuilder.generateAddonDoc(new Pair<>(s, skriptAddon));
                sender.sendMessage(skriptDocsGenerator.getColored("&aSkriptDocsGenerator &6» &aDocumentation generated for " + skriptAddon.getName()));
            });
        });

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        skriptDocsGenerator.getAddons().forEach((s, skriptAddon) -> list.add(s));
        return list;
    }
}
