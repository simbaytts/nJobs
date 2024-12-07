package wtf.n1zamu.nJobs.jobs.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.jobs.Jobs;
import wtf.n1zamu.nJobs.util.JobManager;

public abstract class Job {
   private final Map<UUID, Map<String, Integer>> breaksMap;
   private final Jobs job;
   private final Scoreboard scoreboard;

   public Job(Jobs job) {
      this.job = job;
      this.breaksMap = new HashMap();
      this.scoreboard = NJobs.getInstance().getScoreboardManager().createScoreboard(this.getJob().getName(), this.getJob().getSbTitle(), this.getJob().getSbLines());
      JobManager.inJob.put(this, new ArrayList());
   }

   protected void enter(Player player) {
      if (!((List)JobManager.inJob.get(this)).contains(player.getUniqueId())) {
         ((List)JobManager.inJob.get(this)).add(player.getUniqueId());

         try {
            NJobs.getInstance().getScoreboardManager().showScoreboard(TabAPI.getInstance().getPlayer(player.getUniqueId()), this.scoreboard);
         } catch (IllegalStateException var3) {
            Bukkit.getLogger().info("[NJobs] TAB был перезагружен! Устанавливаю новую инстанцию TABApi!");
            TabAPI.setInstance(TabAPI.getInstance());
            NJobs.getInstance().setScoreboardManager(TabAPI.getInstance().getScoreboardManager());
         }

      }
   }

   protected void quit(Player player) {
      if (((List)JobManager.inJob.get(this)).contains(player.getUniqueId())) {
         ((List)JobManager.inJob.get(this)).remove(player.getUniqueId());

         try {
            NJobs.getInstance().getScoreboardManager().resetScoreboard(TabAPI.getInstance().getPlayer(player.getUniqueId()));
         } catch (IllegalStateException var3) {
            Bukkit.getLogger().info("[NJobs] TAB был перезагружен! Устанавливаю новую инстанцию TABApi!");
            TabAPI.setInstance(TabAPI.getInstance());
            NJobs.getInstance().setScoreboardManager(TabAPI.getInstance().getScoreboardManager());
         }

         this.getBreaksMap().remove(player.getUniqueId());
      }
   }

   protected Material getRandomMaterial(Material[] materials, int[] weights) {
      int totalWeight = 0;
      int[] var4 = weights;
      int i = weights.length;

      for(int var6 = 0; var6 < i; ++var6) {
         int weight = var4[var6];
         totalWeight += weight;
      }

      int cumulativeWeight = 0;

      for(i = 0; i < weights.length; ++i) {
         cumulativeWeight += weights[i];
         if ((new Random()).nextInt(totalWeight) < cumulativeWeight) {
            return materials[i];
         }
      }

      return null;
   }

   public Jobs getJob() {
      return this.job;
   }

   public Map<UUID, Map<String, Integer>> getBreaksMap() {
      return this.breaksMap;
   }
}
