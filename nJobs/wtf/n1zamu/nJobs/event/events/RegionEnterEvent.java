package wtf.n1zamu.nJobs.event.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

public class RegionEnterEvent extends RegionEvent implements Cancellable {
   private boolean cancelled;

   public RegionEnterEvent(ProtectedRegion region, Player player, MovementWay movement, PlayerEvent parent, Location from, Location to) {
      super(region, player, movement, parent, from, to);
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean b) {
      if (this.getMovementWay().isCancellable()) {
         this.cancelled = b;
      } else {
         throw new IllegalStateException("Movement '" + this.getMovementWay().getName() + "' is not cancelable.");
      }
   }
}
