package wtf.n1zamu.nJobs.hook.impl;

import org.bukkit.Bukkit;
import wtf.n1zamu.nJobs.hook.IHook;

public class TABHook implements IHook {
   public boolean initialized() {
      if (Bukkit.getServer().getPluginManager().getPlugin("TAB") == null) {
         Bukkit.getLogger().warning("[NJobs] TAB не обнаружен!");
         return false;
      } else {
         return true;
      }
   }
}
