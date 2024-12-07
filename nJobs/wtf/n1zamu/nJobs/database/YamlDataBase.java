package wtf.n1zamu.nJobs.database;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import wtf.n1zamu.nJobs.command.NJobsCommand;

public class YamlDataBase {
   public static final String PATH = "plugins/nJobs/";

   public static void runData() {
      File configFile = new File("plugins/nJobs/", "data.yml");
      FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
      NJobsCommand.playerBoosters = fileConfiguration.getConfigurationSection("boostedPlayers").getValues(false);
      NJobsCommand.globalBoost = fileConfiguration.getBoolean("globalEnabled");
      NJobsCommand.globalTime = fileConfiguration.getInt("globalTime");
   }

   public static void writeData() throws IOException {
      File configFile = new File("plugins/nJobs/", "data.yml");
      FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
      fileConfiguration.createSection("boostedPlayers", NJobsCommand.playerBoosters);
      fileConfiguration.set("boostedPlayers", NJobsCommand.playerBoosters);
      fileConfiguration.set("globalEnabled", NJobsCommand.globalBoost);
      fileConfiguration.set("globalTime", NJobsCommand.globalTime);
      fileConfiguration.save(configFile);
   }
}
