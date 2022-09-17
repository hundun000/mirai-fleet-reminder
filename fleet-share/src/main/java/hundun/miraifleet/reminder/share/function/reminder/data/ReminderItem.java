package hundun.miraifleet.reminder.share.function.reminder.data;

import java.util.ArrayList;
import java.util.List;

import hundun.miraifleet.reminder.share.function.reminder.ReminderFunction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2021/08/13
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReminderItem {
    Integer count;
    List<String> reminderMessageCodes;
    String cron;
    
    
    
    public static class Factory {

        public static ReminderItem createHourly(
                int hourCondition,
                String text
                ) {
            return createHourly(hourCondition, text, null, null);
        }
        
        public static ReminderItem createHourly(
                int hourCondition,
                String text,
                String audioFileName,
                String imageFileName
                ) {
            String cron = "* 0 " + hourCondition + " * * ?";

            return create(cron, text, null, audioFileName, imageFileName);
        }

        public static ReminderItem create(String cron, String text) {
            return create(cron, text, null, null, null);
        }

        public static ReminderItem create(
                String cornRawFomat,
                String text,
                Integer count,
                String audioFileName,
                String imageFileName
                ) {
            ReminderItem task = new ReminderItem();
            String cron = cornRawFomat.replace("~", " ");
            task.setCron(cron);
            task.setCount(count);
            List<String> codes = new ArrayList<>();
            if (text != null) {
                codes.add(text);
            }
            if (imageFileName != null) {
                codes.add(ReminderFunction.IMAGE_CODE_PREFIX + imageFileName);
            }
            if (audioFileName != null) {
                codes.add(ReminderFunction.AUDIO_CODE_PREFIX + audioFileName);
            }
            task.setReminderMessageCodes(codes);
            return task;
        }
    }
}
