package ch.dempsey.pointsapi.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.dempsey.pointsapi.Main;
import net.md_5.bungee.api.ChatColor;

public class Points implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(sender.hasPermission("points.admin"))) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
		}else {
			if(cmd.getName().equalsIgnoreCase("points")) {
				switch(args.length) {
				 default:
					 sender.sendMessage(ChatColor.RED + "/points <give|take> <player> <amount>");
					 sender.sendMessage(ChatColor.RED + "/points balance <player>");
					 break;
				 case 3:
					 if(args[0].equalsIgnoreCase("give")) {
						 if(Bukkit.getPlayer(args[1]) != null) {
							 Player p = Bukkit.getPlayer(args[1]);
							 try {
								 int amnt = Integer.parseInt(args[2]);
								 Main.getAPIInstance().givePoints(p, amnt);
								 sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " points to " + args[1]);
							 }catch(Exception e) {
								 sender.sendMessage(ChatColor.RED + "Invalid amount!");
							 }
						 }else {
							 sender.sendMessage(ChatColor.RED + "Invalid player!");
						 }
					 }else if(args[0].equalsIgnoreCase("take")) {
						 if(Bukkit.getPlayer(args[1]) != null) {
							 Player p = Bukkit.getPlayer(args[1]);
							 try {
								int amnt = Integer.parseInt(args[2]);
								if(Main.getAPIInstance().getPoints(p) >= amnt) {
									Main.getAPIInstance().takePoints(p, amnt);
									sender.sendMessage(ChatColor.GREEN + "Took " + args[2] + " points from " + args[1]);
								}else {
									sender.sendMessage(ChatColor.RED + "Transaction failed, player only has " + String.valueOf(Main.getAPIInstance().getPoints(p)) + " points, requesting " + args[2]);
								}
							 }catch(Exception e) {
								 sender.sendMessage(ChatColor.RED + "Invalid amount!");
							 }
						 }else {
							 sender.sendMessage(ChatColor.RED + "Invalid player!");
						 }
					 } else {
						 sender.sendMessage(ChatColor.RED + "Invalid subcommand!");
						 break;
					 }
					 break;
				 case 2:
					 if(args[0].equalsIgnoreCase("balance")) {
						 if(Bukkit.getPlayer(args[1]) != null) {
							 Player p = Bukkit.getPlayer(args[1]);
							 sender.sendMessage(ChatColor.GREEN + args[1] + "'s balance is " + String.valueOf(Main.getAPIInstance().getPoints(p)) + " points.");
						 }else {
							 sender.sendMessage(ChatColor.RED + "Invalid player!");
						 }
					 }else {
						 sender.sendMessage(ChatColor.RED + "Invalid subcommand!");
						 break;
					 }
					 break;
					 
				}
			}
		}
		
		return true;
	}
	
}
