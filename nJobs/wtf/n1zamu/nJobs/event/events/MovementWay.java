package wtf.n1zamu.nJobs.event.events;

import java.util.Objects;

public class MovementWay {
   public static final MovementWay MOVE = new MovementWay("MOVE", 0, true);
   public static final MovementWay TELEPORT = new MovementWay("TELEPORT", 1, true);
   public static final MovementWay SPAWN = new MovementWay("SPAWN", 2, false);
   public static final MovementWay DISCONNECT = new MovementWay("DISCONNECT", 3, false);
   private static final MovementWay[] values;
   private final String name;
   private final int ordinal;
   private final boolean cancellable;

   public MovementWay(String name, int ordinal, boolean cancellable) {
      this.name = name;
      this.ordinal = ordinal;
      this.cancellable = cancellable;
   }

   public String getName() {
      return this.name;
   }

   public int ordinal() {
      return this.ordinal;
   }

   public boolean isCancellable() {
      return this.cancellable;
   }

   public String name() {
      return this.name;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MovementWay that = (MovementWay)o;
         return this.ordinal == that.ordinal && this.cancellable == that.cancellable && Objects.equals(this.name, that.name);
      } else {
         return false;
      }
   }

   public static MovementWay[] values() {
      return values;
   }

   static {
      values = new MovementWay[]{MOVE, TELEPORT, SPAWN, DISCONNECT};
   }
}
