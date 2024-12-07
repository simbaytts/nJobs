package wtf.n1zamu.nJobs.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.booster.GlobalTaskBar;
import wtf.n1zamu.nJobs.command.NJobsCommand;

public class PlayerLeaveListener implements Listener {
   @EventHandler
   public void onLeave(PlayerQuitEvent event) {
      (new GlobalTaskBar(NJobs.getInstance(), NJobsCommand.globalTime)).boostPlayers.remove(event.getPlayer().getName());
   }
}
