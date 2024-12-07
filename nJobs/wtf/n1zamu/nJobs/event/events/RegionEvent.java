package wtf.n1zamu.nJobs.event.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class RegionEvent extends PlayerEvent {
   private static final HandlerList handlerList = new HandlerList();
   private final ProtectedRegion region;
   private final MovementWay movement;
   public final PlayerEvent parentEvent;
   private final Location from;
   private final Location to;

   public RegionEvent(ProtectedRegion region, Player player, MovementWay movement, PlayerEvent parent, Location from, Location to) {
      super(player);
      this.region = region;
      this.movement = movement;
      this.parentEvent = parent;
      this.to = to;
      this.from = from;
   }

   public HandlerList getHandlers() {
      return handlerList;
   }

   public ProtectedRegion getRegion() {
      return this.region;
   }

   public static HandlerList getHandlerList() {
      return handlerList;
   }

   public MovementWay getMovementWay() {
      return this.movement;
   }

   public Location getFrom() {
      return this.from;
   }

   public Location getTo() {
      return this.to;
   }

   public PlayerEvent getParentEvent() {
      return this.parentEvent;
   }
}
