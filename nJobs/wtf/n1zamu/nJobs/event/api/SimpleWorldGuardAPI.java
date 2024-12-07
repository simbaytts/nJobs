package wtf.n1zamu.nJobs.event.api;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.event.objects.WgPlayer;

public class SimpleWorldGuardAPI {
   public ProtectedRegion getRegion(String regionId) {
      Iterator var2 = Bukkit.getWorlds().iterator();

      ProtectedRegion rg;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         World world = (World)var2.next();
         rg = this.getRegion(regionId, world);
      } while(rg == null);

      return rg;
   }

   public boolean isInRegion(Location loc, String regionId) {
      Iterator var3 = this.getRegions(loc).iterator();

      ProtectedRegion region;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         region = (ProtectedRegion)var3.next();
      } while(!region.getId().equalsIgnoreCase(regionId));

      return true;
   }

   public ProtectedRegion getRegion(String regionId, World world) {
      RegionManager regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
      return regions != null ? regions.getRegion(regionId) : null;
   }

   public Map<String, ProtectedRegion> getRegions() {
      Map<String, ProtectedRegion> regions = new HashMap();
      RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
      Iterator var3 = Bukkit.getWorlds().iterator();

      while(var3.hasNext()) {
         World world = (World)var3.next();
         regions.putAll(container.get(BukkitAdapter.adapt(world)).getRegions());
      }

      return regions;
   }

   public Map<String, ProtectedRegion> getRegions(World world) {
      return WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world)).getRegions();
   }

   public ApplicableRegionSet getRegions(Location loc) {
      return WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(loc));
   }

   public List<Player> getPlayersInRegion(String regionId) {
      List<Player> players = new ArrayList();
      Iterator var3 = NJobs.getInstance().getPlayerCache().values().iterator();

      while(var3.hasNext()) {
         WgPlayer wgPlayer = (WgPlayer)var3.next();
         wgPlayer.getRegions().forEach((protectedRegion) -> {
            if (regionId.equals(protectedRegion.getId())) {
               players.add(wgPlayer.getPlayer());
            }

         });
      }

      return players;
   }

   public List<Player> getPlayersInRegion(ProtectedRegion protectedRegion) {
      List<Player> players = new ArrayList();
      Iterator var3 = NJobs.getInstance().getPlayerCache().values().iterator();

      while(var3.hasNext()) {
         WgPlayer wgPlayer = (WgPlayer)var3.next();
         if (wgPlayer.getRegions().contains(protectedRegion)) {
            players.add(wgPlayer.getPlayer());
         }
      }

      return players;
   }
}
