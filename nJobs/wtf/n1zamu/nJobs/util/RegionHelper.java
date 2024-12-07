package wtf.n1zamu.nJobs.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RegionHelper {
   public static boolean isPlayerInRegion(String regionName, Player player) {
      Location loc = player.getLocation();
      ApplicableRegionSet set = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(loc.getWorld())).getApplicableRegions(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()));
      return set.getRegions().stream().anyMatch((region) -> {
         return region.getId().equalsIgnoreCase(regionName);
      });
   }

   public static boolean isLocInRegion(String rgId, Location location) {
      ApplicableRegionSet set = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld())).getApplicableRegions(BlockVector3.at(location.getX(), location.getY(), location.getZ()));
      return set.getRegions().stream().anyMatch((region) -> {
         return region.getId().equalsIgnoreCase(rgId);
      });
   }
}
