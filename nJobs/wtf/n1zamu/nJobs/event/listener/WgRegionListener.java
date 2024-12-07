package wtf.n1zamu.nJobs.event.listener;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Iterator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.event.events.MovementWay;
import wtf.n1zamu.nJobs.event.events.RegionLeaveEvent;
import wtf.n1zamu.nJobs.event.events.RegionLeftEvent;
import wtf.n1zamu.nJobs.event.objects.WgPlayer;
import wtf.n1zamu.nJobs.jobs.dto.Job;

public class WgRegionListener implements Listener {
   private final NJobs wg;

   public WgRegionListener(NJobs wg) {
      this.wg = wg;
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
   )
   public void onLogin(PlayerLoginEvent e) {
      if (e.getResult() == Result.ALLOWED) {
         this.wg.getPlayerCache().remove(e.getPlayer().getUniqueId());
         this.wg.getPlayerCache().put(e.getPlayer().getUniqueId(), new WgPlayer(e.getPlayer(), this.wg));
      }
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onJoin(PlayerJoinEvent e) {
      WgPlayer wp = this.wg.getPlayer(e.getPlayer().getUniqueId());
      if (wp != null) {
         wp.updateRegions(MovementWay.SPAWN, e.getPlayer().getLocation(), e.getPlayer().getLocation(), e);
      }
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
   )
   public void onKick(PlayerKickEvent e) {
      WgPlayer wp = this.wg.getPlayer(e.getPlayer().getUniqueId());
      if (wp != null) {
         Iterator var3 = wp.getRegions().iterator();

         while(var3.hasNext()) {
            ProtectedRegion region = (ProtectedRegion)var3.next();
            RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e, e.getPlayer().getLocation(), e.getPlayer().getLocation());
            RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e, e.getPlayer().getLocation(), e.getPlayer().getLocation());
            this.wg.getServer().getPluginManager().callEvent(leaveEvent);
            this.wg.getServer().getPluginManager().callEvent(leftEvent);
         }

         this.wg.getSimpleWorldGuardAPI().isInRegion(e.getPlayer().getLocation(), "name");
         wp.getRegions().clear();
         this.wg.getPlayerCache().remove(e.getPlayer().getUniqueId());
      }
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onQuit(PlayerQuitEvent e) {
      WgPlayer wp = this.wg.getPlayer(e.getPlayer().getUniqueId());
      if (wp != null) {
         Iterator var3 = wp.getRegions().iterator();

         while(var3.hasNext()) {
            ProtectedRegion region = (ProtectedRegion)var3.next();
            RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e, e.getPlayer().getLocation(), e.getPlayer().getLocation());
            RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e, e.getPlayer().getLocation(), e.getPlayer().getLocation());
            this.wg.getServer().getPluginManager().callEvent(leaveEvent);
            this.wg.getServer().getPluginManager().callEvent(leftEvent);
         }

         wp.getRegions().clear();
         this.wg.getPlayerCache().remove(e.getPlayer().getUniqueId());
      }
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
   )
   public void onMove(PlayerMoveEvent e) {
      if (e.getPlayer().getWorld() == ((Job)NJobs.getInstance().getJobs().get(1)).getJob().getWorld()) {
         WgPlayer wp = this.wg.getPlayer(e.getPlayer().getUniqueId());
         if (wp != null) {
            e.setCancelled(wp.updateRegions(MovementWay.MOVE, e.getTo(), e.getFrom(), e));
         }
      }
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.HIGHEST
   )
   public void onMove(PlayerTeleportEvent e) {
      WgPlayer wp = this.wg.getPlayer(e.getPlayer().getUniqueId());
      if (wp != null) {
         e.setCancelled(wp.updateRegions(MovementWay.TELEPORT, e.getTo(), e.getFrom(), e));
      }
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onRespawn(PlayerRespawnEvent e) {
      WgPlayer wp = this.wg.getPlayer(e.getPlayer().getUniqueId());
      if (wp != null) {
         wp.updateRegions(MovementWay.SPAWN, e.getRespawnLocation(), e.getPlayer().getLocation(), e);
      }
   }
}
