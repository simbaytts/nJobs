package wtf.n1zamu.nJobs.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.booster.GlobalTaskBar;
import wtf.n1zamu.nJobs.booster.PlayerTaskBar;
import wtf.n1zamu.nJobs.command.NJobsCommand;

public class PlayerJoinListener implements Listener {
   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      if (NJobsCommand.playerBoosters.containsKey(player.getName())) {
         (new PlayerTaskBar(player, NJobs.getInstance(), Integer.parseInt(NJobsCommand.playerBoosters.get(player.getName()).toString()))).startBooster();
      }

      if (NJobsCommand.globalBoost) {
         (new GlobalTaskBar(NJobs.getInstance(), NJobsCommand.globalTime)).startBooster();
      }

   }
}
