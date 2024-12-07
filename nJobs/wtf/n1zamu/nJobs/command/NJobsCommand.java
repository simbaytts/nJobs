package wtf.n1zamu.nJobs.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.booster.GlobalTaskBar;
import wtf.n1zamu.nJobs.booster.PlayerTaskBar;
import wtf.n1zamu.nJobs.util.JobManager;
import wtf.n1zamu.nJobs.util.SalaryGiver;

public class NJobsCommand implements CommandExecutor, TabCompleter {
   public static Map<String, Object> playerBoosters = new HashMap();
   public static int globalTime = 0;
   public static boolean globalBoost = false;

   public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, String[] strings) {
      if (strings.length < 1) {
         return false;
      } else if (strings[0].equalsIgnoreCase("boost")) {
         if (!commandSender.hasPermission("nJobs.admin")) {
            return false;
         } else if (strings.length < 2) {
            return false;
         } else {
            int time;
            try {
               time = Integer.parseInt(strings[2]);
            } catch (NumberFormatException var7) {
               commandSender.sendMessage(this.getConfig("incTime"));
               return false;
            }

            if (strings[1].equalsIgnoreCase("all")) {
               commandSender.sendMessage(this.getConfig("boostAll"));
               globalTime = time;
               globalBoost = true;
               GlobalTaskBar globalTaskBar = new GlobalTaskBar(NJobs.getInstance(), time);
               globalTaskBar.startBooster();
            } else {
               if (!Bukkit.getPlayerExact(strings[1]).isOnline()) {
                  commandSender.sendMessage(this.getConfig("noOnline"));
                  return false;
               }

               playerBoosters.put(Bukkit.getPlayerExact(strings[1]).getName(), time);
               commandSender.sendMessage(this.getConfig("boostPlayer").replace("%player%", Bukkit.getPlayerExact(strings[1]).getName()));
               (new PlayerTaskBar(Bukkit.getPlayerExact(strings[1]), NJobs.getInstance(), time)).startBooster();
            }

            return true;
         }
      } else {
         if (strings[0].equalsIgnoreCase("reload")) {
            if (!commandSender.hasPermission("nJobs.admin")) {
               return false;
            }

            commandSender.sendMessage(this.getConfig("reload"));
            NJobs.getInstance().reloadConfig();
         }

         if (strings[0].equalsIgnoreCase("pass")) {
            Player player = (Player)commandSender;
            NJobs.getInstance().getJobs().forEach((job) -> {
               if (((List)JobManager.inJob.get(job)).contains(player.getUniqueId())) {
                  (new SalaryGiver()).giveSalary(JobManager.getSalary(job.getBreaksMap(), player), player, job);
               }

            });
            return true;
         } else {
            return true;
         }
      }
   }

   public List<String> onTabComplete(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, String[] strings) {
      return !(commandSender instanceof Player) ? null : Arrays.asList("pass", "boost", "reload");
   }

   private String getConfig(String message) {
      return ChatColor.translateAlternateColorCodes('&', NJobs.getConfiguration().getString("adminMessage." + message));
   }
}
