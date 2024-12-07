package wtf.n1zamu.nJobs.util;

import java.util.concurrent.TimeUnit;

public class TimeFormat {
   public static String getFormattedCooldown(long millis) {
      if (millis <= 60000L) {
         return getFormattedCooldownSeconds(millis);
      } else if (millis <= 3600000L) {
         return getFormattedCooldownMinutes(millis);
      } else {
         return millis <= 86400000L ? getFormattedCooldownHours(millis) : getFormattedCooldownDays(millis);
      }
   }

   private static String getFormattedCooldownDays(long millis) {
      return String.format("%02d дней %02d часов %02d минут %02d секунд", TimeUnit.MILLISECONDS.toDays(millis), TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
   }

   private static String getFormattedCooldownHours(long millis) {
      return String.format("%02d часов %02d минут %02d секунд", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
   }

   private static String getFormattedCooldownMinutes(long millis) {
      return String.format("%02d минут %02d секунд", TimeUnit.MILLISECONDS.toMinutes(millis), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
   }

   private static String getFormattedCooldownSeconds(long millis) {
      return millis / 1000L + " секунд ";
   }
}
