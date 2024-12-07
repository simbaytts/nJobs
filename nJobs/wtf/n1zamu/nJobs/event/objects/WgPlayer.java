package wtf.n1zamu.nJobs.event.objects;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.event.events.MovementWay;
import wtf.n1zamu.nJobs.event.events.RegionEnterEvent;
import wtf.n1zamu.nJobs.event.events.RegionEnteredEvent;
import wtf.n1zamu.nJobs.event.events.RegionLeaveEvent;
import wtf.n1zamu.nJobs.event.events.RegionLeftEvent;

public class WgPlayer {
   private final Player player;
   private final NJobs wg;
   private final List<ProtectedRegion> regions;

   public WgPlayer(Player player, NJobs wg) {
      this.player = player;
      this.wg = wg;
      this.regions = new ArrayList();
   }

   public boolean updateRegions(MovementWay way, Location to, Location from, PlayerEvent parent) {
      Objects.requireNonNull(way, "MovementWay 'way' can not be null.");
      Objects.requireNonNull(to, "Location 'to' can not be null.");
      Objects.requireNonNull(from, "Location 'from' can not be null.");
      ApplicableRegionSet toRegions = this.wg.getSimpleWorldGuardAPI().getRegions(to);
      ApplicableRegionSet fromRegions = this.wg.getSimpleWorldGuardAPI().getRegions(from);
      Iterator var7;
      ProtectedRegion region;
      if (!toRegions.getRegions().isEmpty()) {
         var7 = toRegions.iterator();

         while(var7.hasNext()) {
            region = (ProtectedRegion)var7.next();
            if (!this.regions.contains(region)) {
               RegionEnterEvent enter = new RegionEnterEvent(region, this.player, way, parent, from, to);
               this.wg.getServer().getPluginManager().callEvent(enter);
               if (enter.isCancelled()) {
                  return true;
               }

               this.regions.add(region);
               this.wg.getServer().getScheduler().runTaskLater(this.wg, () -> {
                  this.wg.getServer().getPluginManager().callEvent(new RegionEnteredEvent(region, this.player, way, parent, from, to));
               }, 1L);
            }
         }

         Set<ProtectedRegion> toRemove = new HashSet();
         Iterator var12 = fromRegions.iterator();

         while(var12.hasNext()) {
            ProtectedRegion oldRegion = (ProtectedRegion)var12.next();
            if (!toRegions.getRegions().contains(oldRegion)) {
               RegionLeaveEvent leave = new RegionLeaveEvent(oldRegion, this.player, way, parent, from, to);
               this.wg.getServer().getPluginManager().callEvent(leave);
               if (leave.isCancelled()) {
                  return true;
               }

               this.wg.getServer().getScheduler().runTaskLater(this.wg, () -> {
                  this.wg.getServer().getPluginManager().callEvent(new RegionLeftEvent(oldRegion, this.player, way, parent, from, to));
               }, 1L);
               toRemove.add(oldRegion);
            }
         }

         this.regions.removeAll(toRemove);
      } else {
         var7 = this.regions.iterator();

         while(var7.hasNext()) {
            region = (ProtectedRegion)var7.next();
            RegionLeaveEvent leave = new RegionLeaveEvent(region, this.player, way, parent, from, to);
            this.wg.getServer().getPluginManager().callEvent(leave);
            if (leave.isCancelled()) {
               return true;
            }

            this.wg.getServer().getScheduler().runTaskLater(this.wg, () -> {
               this.wg.getServer().getPluginManager().callEvent(new RegionLeftEvent(region, this.player, way, parent, from, to));
            }, 1L);
         }

         this.regions.clear();
      }

      return false;
   }

   public List<ProtectedRegion> getRegions() {
      return this.regions;
   }

   public Player getPlayer() {
      return this.player;
   }
}
