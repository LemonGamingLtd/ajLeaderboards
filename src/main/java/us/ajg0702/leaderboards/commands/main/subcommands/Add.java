package us.ajg0702.leaderboards.commands.main.subcommands;

import us.ajg0702.commands.CommandSender;
import us.ajg0702.commands.SubCommand;
import us.ajg0702.leaderboards.LeaderboardPlugin;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import static us.ajg0702.leaderboards.LeaderboardPlugin.message;

public class Add extends SubCommand {
    private final LeaderboardPlugin plugin;

    public Add(LeaderboardPlugin plugin) {
        super("add", Collections.emptyList(), "ajleaderboards.use", "Add a placeholder to ajLeaderboards");
        this.plugin = plugin;
    }

    @Override
    public List<String> autoComplete(CommandSender commandSender, String[] strings) {
        return plugin.getCache().getNonExistantBoards();
    }

    @Override
    public void execute(CommandSender sender, String[] args, String label) {
        plugin.getScheduler().getImpl().runAsync(() -> {
            if(args.length < 1) {
                sender.sendMessage(message("&cPlease provide a placeholder to track.\n&7Usage: /"+label+" add <placeholder>"));
                return;
            }
            String placeholder = args[0];
            placeholder = placeholder.replaceAll(Matcher.quoteReplacement("%"), "");
            if(placeholder.startsWith("ajlb")) {
                sender.sendMessage(message("&cYou cannot create a leaderboard out of an ajLeaderboards placeholder!\n&7See how to set up the plugin &r<underlined><click:open_url:https://wiki.ajg0702.us/ajleaderboards/setup/setup>here</click></underlined>"));
                return;
            }
            if(!plugin.validatePlaceholder(placeholder, sender)) {
                sender.sendMessage(message("&cThe placeholder '"+placeholder+"' does not give a numerical value. Make sure that the placeholder returns a number that is not formatted."));
                return;
            }
            boolean r = plugin.getCache().createBoard(placeholder);
            if(r) {
                sender.sendMessage(message("&aBoard '"+placeholder+"' successfully created!"));
            } else {
                sender.sendMessage(message("&cBoard '"+placeholder+"' creation failed! See console for more info."));
            }
        });
    }
}
