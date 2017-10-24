package me.DerekV.ninjaCoin;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ninjaCoin extends JavaPlugin{
	
	ninjaCoin plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	
	static File ExpFile;
	static FileConfiguration Exp;	
	
	@Override
	public void onDisable(){
		this.logger.info("ninjaCoin version V.1 has been disabled");
		
	}
	@Override
	public void onEnable(){
		
		this.logger.info("ninjaCoin version V.1 has been enabled");
		
		
		
		ExpFile = new File(getDataFolder(), "Coins.yml");
		Exp = YamlConfiguration.loadConfiguration(ExpFile);
		saveStats();
		
		
	}
	
	
	public void saveStats(){
		try {
		Exp.save(ExpFile);
		} catch (Exception e) {
			
		}
		}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		
		if(commandLabel.equalsIgnoreCase("nc")){
			if(args.length == 0){
				sender.sendMessage(ChatColor.RED + "/nc give [playername] [amount], /nc send [playername] [amount], /nc check");
			}else{
				if(args[0].equalsIgnoreCase("give") && sender.hasPermission("nc.give.coin")){
			
				if(args.length == 1){
					sender.sendMessage(ChatColor.RED + "/nc give [player] [amount]");
				}
				if(args.length >= 2){
			        @SuppressWarnings("deprecation")
					Player target = Bukkit.getServer().getPlayer(args[1]);
			        if(target != null){
		        	  if(Exp.getString(target.getName() + ".Coins") == null){
		        		  Exp.set(target.getName() + ".Coins", Integer.parseInt(args[2]));  
		        	  }
		        	  else{
		        		  Exp.set(target.getName() + ".Coins", (Integer.parseInt(args[2]) + Exp.getInt(target.getName() + ".Coins"))); 
		        	  }
		        	  
		        	  saveStats();
		        	  sender.sendMessage(ChatColor.GREEN + "You have given " + args[2] + " NinjaCoins");
		        	  target.sendMessage(ChatColor.GREEN + "You have been given " + args[2] + " NinjaCoins");
			        }else{
			        	sender.sendMessage(ChatColor.RED + "Player is not online");
			        }
				
				
			}
			
			
			
		}
			
			
			else if(args[0].equalsIgnoreCase("send") && sender.hasPermission("nc.send.coin")){
				if(args.length == 1){
					sender.sendMessage(ChatColor.RED + "/nc send [player] [amount]");
				}
				if(args.length >= 2){
			        @SuppressWarnings("deprecation")
					Player target = Bukkit.getServer().getPlayer(args[1]);
			        if(target != null){
			        	int Int = Exp.getInt(sender.getName() + ".Coins");
			        	int Int2 = Integer.parseInt(args[2]);
			        	
			        	if(Int2 <= Int){
			        		if(target.getName() != sender.getName()){
		        		  Exp.set(target.getName() + ".Coins", (Integer.parseInt(args[2]) + Exp.getInt(target.getName() + ".Coins"))); 
		        		  Exp.set(sender.getName() + ".Coins", (Exp.getInt(sender.getName() + ".Coins") - Int));
		        	  	  saveStats();
		        	  	  sender.sendMessage(ChatColor.GREEN + "You have given " + args[2] + " NinjaCoins");
		        	  	  target.sendMessage(ChatColor.GREEN + "You have been given " + args[2] + " NinjaCoins");
			        			}else{
			        				sender.sendMessage(ChatColor.RED + "You can't send yourself NinjaCoins!");
			        			}
			        		
			        		}else{
				        		sender.sendMessage(ChatColor.RED + "You don't have enough NinjaCoins!");
			        	}
			        }else{
			        	sender.sendMessage(ChatColor.RED + "Player is not online");
			        }
				
			}
			
			
			
		}
			else if(args[0].equalsIgnoreCase("check") && sender.hasPermission("nc.check")){
				sender.sendMessage(ChatColor.GREEN + "" + Exp.getInt(sender.getName() + ".Coins") + " NinjaCoins");
			}else{
				sender.sendMessage(ChatColor.RED + "/nc give [playername] [amount], /nc send [playername] [amount], /nc check");
			}
			
			
	
		}
	
		}
	return false;
	}
		
		
	
	
	
	
}
