package wtf.n1zamu.nJobs.util;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.neznamy.tab.api.TabAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.command.NJobsCommand;
import wtf.n1zamu.nJobs.hook.impl.VaultHook;
import wtf.n1zamu.nJobs.jobs.dto.Job;

public class SalaryGiver {
   public void giveSalary(int salary, Player player, Job job) {
      NJobs.getInstance().getScoreboardManager().resetScoreboard(TabAPI.getInstance().getPlayer(player.getUniqueId()));
      ((List)JobManager.inJob.get(job)).remove(player.getUniqueId());
      job.getBreaksMap().remove(player.getUniqueId());
      int bonus = (int)((double)salary * ThreadLocalRandom.current().nextDouble((double)NJobs.getConfiguration().getInt("minBonus"), (double)NJobs.getConfiguration().getInt("maxBonus")) / 100.0D);
      if (NJobsCommand.globalBoost || NJobsCommand.playerBoosters.containsKey(player.getName())) {
         salary *= 2;
      }

      Iterator var5 = NJobs.getConfiguration().getStringList("getMoneyMethod.cmdMessage").iterator();

      while(var5.hasNext()) {
         String line = (String)var5.next();
         player.sendMessage(ChatColor.translateAlternateColorCodes('&', line).replace("%bonus%", String.valueOf(bonus)).replace("%salary%", String.valueOf(salary + bonus)));
      }

      VaultHook.getEconomy().depositPlayer(player, (double)(salary + bonus));
   }
}
