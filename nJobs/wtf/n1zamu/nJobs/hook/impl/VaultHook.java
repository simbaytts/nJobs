package wtf.n1zamu.nJobs.hook.impl;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import wtf.n1zamu.nJobs.hook.IHook;

public class VaultHook implements IHook {
   private static Economy economy;

   public static Economy getEconomy() {
      return economy;
   }

   public boolean initialized() {
      RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
      if (rsp == null) {
         Bukkit.getLogger().warning("[NJobs] Vault не обнаружен!");
         return false;
      } else {
         economy = (Economy)rsp.getProvider();
         return true;
      }
   }
}
