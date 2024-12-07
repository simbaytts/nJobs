package wtf.n1zamu.nJobs.hook.impl;

import org.bukkit.Bukkit;
import wtf.n1zamu.nJobs.hook.IHook;

public class PAPIHook implements IHook {
   public boolean initialized() {
      if (Bukkit.getServer().getPluginManager().getPlugin("PlaceHolderAPI") == null) {
         Bukkit.getLogger().warning("[NJobs] PlaceHolderAPI не обнаружен!");
         return false;
      } else {
         return true;
      }
   }
}
