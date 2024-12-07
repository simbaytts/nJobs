package wtf.n1zamu.nJobs.util;

import java.util.Arrays;
import org.bukkit.Bukkit;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.hook.impl.PAPIHook;
import wtf.n1zamu.nJobs.hook.impl.TABHook;
import wtf.n1zamu.nJobs.hook.impl.VaultHook;
import wtf.n1zamu.nJobs.hook.impl.WGHook;

public class HookUtil {
   public void setupHooks() {
      Arrays.asList(new TABHook(), new WGHook(), new PAPIHook(), new VaultHook()).forEach((iHook) -> {
         if (!iHook.initialized()) {
            Bukkit.getPluginManager().disablePlugin(NJobs.getInstance());
         }

      });
   }
}
