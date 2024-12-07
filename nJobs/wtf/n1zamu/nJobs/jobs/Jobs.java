package wtf.n1zamu.nJobs.jobs;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import wtf.n1zamu.nJobs.NJobs;

public enum Jobs {
   MINE("mine"),
   FARM("farm"),
   WOODCUTTER("woodCutter");

   private final String name;
   private final String regionName;
   private final String sbTitle;
   private final List<String> sbLines;
   private final World world;

   private Jobs(String name) {
      this.name = name;
      this.regionName = NJobs.getConfiguration().getString(name + "Region");
      this.sbTitle = NJobs.getConfiguration().getString(name + ".title");
      this.sbLines = NJobs.getConfiguration().getStringList(name + ".scoreboard");
      this.world = Bukkit.getWorld(NJobs.getConfiguration().getString("worldName"));
   }

   public String getRegionName() {
      return this.regionName;
   }

   public String getSbTitle() {
      return this.sbTitle;
   }

   public List<String> getSbLines() {
      return this.sbLines;
   }

   public String getName() {
      return this.name;
   }

   public World getWorld() {
      return this.world;
   }
}
