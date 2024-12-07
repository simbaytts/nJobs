package wtf.n1zamu.nJobs.placeholder;

import java.util.Iterator;
import java.util.List;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.jobs.dto.Job;
import wtf.n1zamu.nJobs.util.JobManager;

public class JobPlaceholder extends PlaceholderExpansion {
   @Nullable
   public String onPlaceholderRequest(Player player, @NonNull String identifier) {
      Iterator var3 = NJobs.getInstance().getJobs().iterator();

      Job job;
      do {
         if (!var3.hasNext()) {
            return super.onPlaceholderRequest(player, identifier);
         }

         job = (Job)var3.next();
      } while(!((List)JobManager.inJob.get(job)).contains(player.getUniqueId()));

      if (identifier.equalsIgnoreCase("salary")) {
         return String.valueOf(JobManager.getSalary(job.getBreaksMap(), player));
      } else if (job.equals(NJobs.getInstance().getJobs().get(0)) && identifier.equalsIgnoreCase("count")) {
         return String.valueOf(JobManager.getBrokenCount(job.getBreaksMap(), player.getUniqueId(), "WOOD"));
      } else {
         return String.valueOf(JobManager.getBrokenCount(job.getBreaksMap(), player.getUniqueId(), identifier.toUpperCase()));
      }
   }

   @NonNull
   public String getIdentifier() {
      return "nJobs";
   }

   @NonNull
   public String getAuthor() {
      return "n1zamu";
   }

   @NonNull
   public String getVersion() {
      return "1";
   }
}
