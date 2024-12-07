package wtf.n1zamu.nJobs.hook.impl;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import wtf.n1zamu.nJobs.hook.IHook;

public class WGHook implements IHook {
   private WorldGuardPlugin worldGuardPlugin;

   public boolean initialized() {
      Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
      if (plugin instanceof WorldGuardPlugin) {
         this.worldGuardPlugin = (WorldGuardPlugin)plugin;
         return true;
      } else {
         Bukkit.getLogger().warning("[NJobs] WorldGuard не обнаружен!");
         return false;
      }
   }
}
