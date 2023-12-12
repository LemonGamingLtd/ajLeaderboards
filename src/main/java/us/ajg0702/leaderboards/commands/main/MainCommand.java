package us.ajg0702.leaderboards.commands.main;

import us.ajg0702.commands.BaseCommand;
import us.ajg0702.commands.CommandSender;
import us.ajg0702.commands.SubCommand;
import us.ajg0702.leaderboards.LeaderboardPlugin;
import us.ajg0702.leaderboards.commands.main.subcommands.*;
import us.ajg0702.leaderboards.commands.main.subcommands.debug.Format;
import us.ajg0702.leaderboards.commands.main.subcommands.debug.Resets;
import us.ajg0702.leaderboards.commands.main.subcommands.debug.Save;
import us.ajg0702.leaderboards.commands.main.subcommands.debug.Time;
import us.ajg0702.leaderboards.commands.main.subcommands.signs.Signs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static us.ajg0702.leaderboards.LeaderboardPlugin.message;

@SuppressWarnings("FieldCanBeLocal")
public class MainCommand extends BaseCommand {
    public MainCommand(LeaderboardPlugin plugin) {
        super("ajleaderboards", Arrays.asList("ajl", "ajlb"), "ajleaderboards.use", "Main comamnd for ajLeaderboards");

        addSubCommand(new Version(plugin));
        addSubCommand(new Reload(plugin));
        addSubCommand(new Add(plugin));
        addSubCommand(new CheckUpdate(plugin));
        addSubCommand(new UpdatePlayer(plugin));
        addSubCommand(new RemovePlayer(plugin));
        addSubCommand(new Remove(plugin));
        addSubCommand(new ListBoards(plugin));
        addSubCommand(new Signs(plugin));
        addSubCommand(new Export(plugin));
        addSubCommand(new Import(plugin));
        addSubCommand(new Viewer(plugin));
        addSubCommand(new Reset(plugin));
        addSubCommand(new UpdateAllOffline(plugin));

        // Debug commands
        addSubCommand(new Time());
        addSubCommand(new Resets(plugin));
        addSubCommand(new Format());
        addSubCommand(new Save(plugin));
    }

    @Override
    public List<String> autoComplete(CommandSender sender, String[] args) {
        if(!checkPermission(sender)) {
            return Collections.emptyList();
        }
        if(args.length == 1) {
            return filterCompletion(subCommandAutoComplete(sender, args), args[0]);
        } else {
            return subCommandAutoComplete(sender, args);
        }
    }

    @Override
    public void execute(CommandSender sender, String[] args, String label) {
        if(!checkPermission(sender)) {
            sender.sendMessage(message("<red>You don't have permission to do this!"));
        }
        if(subCommandExecute(sender, args, label)) return;

        sendHelp(sender, label, getSubCommands());
    }

    public static void sendHelp(CommandSender sender, String label, List<SubCommand> subCommands) {
        sender.sendMessage(message(""));
        for(SubCommand subCommand : subCommands) {
            if(!subCommand.showInTabComplete()) continue;
            String command = "/"+label+" "+subCommand.getName();
            sender.sendMessage(message(
                    "<hover:show_text:'<yellow>Click to start typing <gold>"+command+"'>" +
                            "<click:suggest_command:"+command+" >" +
                            "<gold>"+command+"<yellow> - "+subCommand.getDescription()+"" +
                            "</click>" +
                            "</hover>"
            ));
        }
    }
}
