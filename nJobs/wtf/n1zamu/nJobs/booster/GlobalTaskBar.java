package wtf.n1zamu.nJobs.booster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.command.NJobsCommand;
import wtf.n1zamu.nJobs.util.TimeFormat;

public class GlobalTaskBar {
   private final JavaPlugin plugin;
   private BukkitTask tickTask;
   private final String text;
   public List<String> boostPlayers = new ArrayList();

   public GlobalTaskBar(JavaPlugin plugin, int time) {
      this.plugin = plugin;
      NJobsCommand.globalTime = time;
      this.text = ChatColor.translateAlternateColorCodes('&', NJobs.getConfiguration().getString("globalBoosterBar"));
   }

   public void startBooster() {
      List<String> playersWithBoosters = new ArrayList();
      Iterator var2 = NJobsCommand.playerBoosters.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, Object> entry = (Entry)var2.next();
         if (Bukkit.getPlayerExact((String)entry.getKey()) != null) {
            playersWithBoosters.add(entry.getKey());
         }
      }

      this.tickTask = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
         if (NJobsCommand.globalTime == 0) {
            this.endBooster();
         } else {
            playersWithBoosters.forEach((s) -> {
               Player player = Bukkit.getPlayerExact(s);
               if (player != null && player.isOnline()) {
                  player.sendActionBar(this.text.replace("%time%", TimeFormat.getFormattedCooldown((long)NJobsCommand.globalTime * 1000L)));
               }

            });
            --NJobsCommand.globalTime;
         }
      }, 0L, 20L);
      NJobsCommand.globalBoost = true;
   }

   void endBooster() {
      this.tickTask.cancel();
      NJobsCommand.globalBoost = false;
   }
}
