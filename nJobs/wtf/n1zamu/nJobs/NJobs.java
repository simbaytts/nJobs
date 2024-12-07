package wtf.n1zamu.nJobs;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.n1zamu.nJobs.booster.GlobalTaskBar;
import wtf.n1zamu.nJobs.booster.PlayerTaskBar;
import wtf.n1zamu.nJobs.command.NJobsCommand;
import wtf.n1zamu.nJobs.database.YamlDataBase;
import wtf.n1zamu.nJobs.event.api.SimpleWorldGuardAPI;
import wtf.n1zamu.nJobs.event.listener.WgRegionListener;
import wtf.n1zamu.nJobs.event.objects.WgPlayer;
import wtf.n1zamu.nJobs.jobs.dto.Job;
import wtf.n1zamu.nJobs.jobs.dto.impl.FarmJob;
import wtf.n1zamu.nJobs.jobs.dto.impl.MineJob;
import wtf.n1zamu.nJobs.jobs.dto.impl.WoodcutterJob;
import wtf.n1zamu.nJobs.listener.PlayerJoinListener;
import wtf.n1zamu.nJobs.listener.PlayerLeaveListener;
import wtf.n1zamu.nJobs.placeholder.JobPlaceholder;
import wtf.n1zamu.nJobs.util.HookUtil;
import wtf.n1zamu.nJobs.util.woodcutter.Animation;
import wtf.n1zamu.nJobs.util.woodcutter.tree.TreeBackup;

public class NJobs extends JavaPlugin {
   ScoreboardManager scoreboardManager;
   public HashMap<TreeBackup, Long> trees = new HashMap();
   public HashMap<Long, Integer> strength = new HashMap();
   private HashMap<UUID, WgPlayer> playerCache;
   private SimpleWorldGuardAPI simpleWorldGuardAPI;
   private List<Job> jobs;
   private static NJobs INSTANCE;

   public void onLoad() {
      INSTANCE = this;
      this.playerCache = new HashMap();
      this.simpleWorldGuardAPI = new SimpleWorldGuardAPI();
   }

   public void onEnable() {
      (new HookUtil()).setupHooks();
      this.scoreboardManager = TabAPI.getInstance().getScoreboardManager();
      this.saveDefaultConfig();
      this.saveResource("data.yml", false);
      YamlDataBase.runData();
      this.getLogger().info("======NJobs loaded successfully======");
      this.playerCache.clear();
      this.jobs = Arrays.asList(new WoodcutterJob(), new MineJob(), new FarmJob());
      this.getJobs().forEach((job) -> {
         Bukkit.getPluginManager().registerEvents((Listener)job, this);
      });
      Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
      Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);
      Bukkit.getPluginManager().registerEvents(new WgRegionListener(getInstance()), this);
      (new JobPlaceholder()).register();
      this.getServer().getOnlinePlayers().forEach((player) -> {
         WgPlayer var10000 = (WgPlayer)this.playerCache.put(player.getUniqueId(), new WgPlayer(player, getInstance()));
      });
      this.getCommand("nJobs").setExecutor(new NJobsCommand());
      this.getCommand("nJobs").setTabCompleter(new NJobsCommand());
      Bukkit.getOnlinePlayers().forEach((player) -> {
         if (player.isOnline() && NJobsCommand.playerBoosters.containsKey(player.getName())) {
            PlayerTaskBar playerTaskBar = new PlayerTaskBar(player, this, Integer.parseInt(NJobsCommand.playerBoosters.get(player.getName()).toString()));
            playerTaskBar.startBooster();
         }

      });
      if (NJobsCommand.globalBoost) {
         (new GlobalTaskBar(this, NJobsCommand.globalTime)).startBooster();
      }

   }

   public void onDisable() {
      try {
         YamlDataBase.writeData();
      } catch (IOException var2) {
         var2.printStackTrace();
      }

      this.getLogger().info("=====NJobs unloaded successfully======");
      this.trees.keySet().forEach(Animation::hardBack);
   }

   public static NJobs getInstance() {
      return INSTANCE;
   }

   public static FileConfiguration getConfiguration() {
      return getInstance().getConfig();
   }

   public List<Job> getJobs() {
      return this.jobs;
   }

   public ScoreboardManager getScoreboardManager() {
      return this.scoreboardManager;
   }

   public void setScoreboardManager(ScoreboardManager scoreboardManager) {
      this.scoreboardManager = scoreboardManager;
   }

   public SimpleWorldGuardAPI getSimpleWorldGuardAPI() {
      return this.simpleWorldGuardAPI;
   }

   public HashMap<UUID, WgPlayer> getPlayerCache() {
      return this.playerCache;
   }

   public WgPlayer getPlayer(UUID uuid) {
      return (WgPlayer)this.playerCache.get(uuid);
   }
}
