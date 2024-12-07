package wtf.n1zamu.nJobs.booster;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.command.NJobsCommand;
import wtf.n1zamu.nJobs.util.TimeFormat;

public class PlayerTaskBar {
   private int timeLeft;
   private final int timeOut;
   private final Player player;
   private final JavaPlugin plugin;
   private String text;
   private BukkitTask tickTask;

   public PlayerTaskBar(Player player, JavaPlugin plugin, int time) {
      this.player = player;
      this.plugin = plugin;
      this.timeOut = time;
      this.text = ChatColor.translateAlternateColorCodes('&', NJobs.getConfiguration().getString("personalBoosterBar"));
   }

   public void startBooster() {
      this.timeLeft = this.timeOut;
      this.tickTask = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
         if (this.timeLeft == 0) {
            this.endBooster();
         } else {
            this.player.sendActionBar(this.text.replace("%time%", TimeFormat.getFormattedCooldown((long)this.timeLeft * 1000L)));
            --this.timeLeft;
            NJobsCommand.playerBoosters.put(this.player.getName(), this.timeLeft);
         }
      }, 0L, 20L);
   }

   public void endBooster() {
      this.tickTask.cancel();
      NJobsCommand.playerBoosters.remove(this.player.getName());
   }
}
