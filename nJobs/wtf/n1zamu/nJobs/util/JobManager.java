package wtf.n1zamu.nJobs.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import org.bukkit.entity.Player;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.jobs.dto.Job;

public class JobManager {
   public static Map<Job, List<UUID>> inJob = new HashMap();

   public static int getBrokenCount(Map<UUID, Map<String, Integer>> map, UUID uuid, String type) {
      return (Integer)((Map)map.getOrDefault(uuid, new HashMap())).getOrDefault(type, 0);
   }

   public static void addBlockBreak(Map<UUID, Map<String, Integer>> map, UUID uuid, String type) {
      map.computeIfAbsent(uuid, (k) -> {
         return new HashMap();
      });
      Map<String, Integer> playerBreaks = (Map)map.get(uuid);
      playerBreaks.put(type, (Integer)playerBreaks.getOrDefault(type, 0) + 1);
   }

   public static int getSalary(Map<UUID, Map<String, Integer>> map, Player player) {
      int totalSalary = 0;

      Entry entry;
      int price;
      for(Iterator var3 = ((Map)map.getOrDefault(player.getUniqueId(), new HashMap())).entrySet().iterator(); var3.hasNext(); totalSalary += (Integer)entry.getValue() * price) {
         entry = (Entry)var3.next();
         price = NJobs.getConfiguration().getInt("prices." + (String)entry.getKey() + "_price");
      }

      return totalSalary;
   }
}
