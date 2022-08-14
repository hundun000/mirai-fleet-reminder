package hundun.miraifleet.reminder.plugin.botlogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import hundun.miraifleet.reminder.share.function.reminder.data.HourlyChatConfigV2;
import hundun.miraifleet.reminder.share.function.reminder.data.ReminderItem;
import hundun.miraifleet.reminder.share.function.reminder.data.ReminderList;


/**
 * @author hundun
 * Created on 2022/08/18
 */
public class ReminderDefaultConfigAndData {

    public static Supplier<ReminderList> reminderListDefaultDataSupplier() {
        return () -> {
            ReminderList reminderList = new ReminderList();
            reminderList.setItems(Arrays.asList(
                    ReminderItem.Factory.create("* 0 22 ? * 1", "现在是周日晚上10点。请博士记得完成本周剿灭作战。")
                    ));
            return reminderList;
        };
    }

    public static Supplier<HourlyChatConfigV2> hourlyChatConfigDefaultDataSupplier() {
        return () -> {
            HourlyChatConfigV2 hourlyChatConfig = new HourlyChatConfigV2();
            List<ReminderItem> chatTexts = new ArrayList<>();
            chatTexts.add(ReminderItem.Factory.createHourly(9, "九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？", "阿米娅_交谈2.amr", "九点.png"));
            chatTexts.add(ReminderItem.Factory.createHourly(10, "十点到了。欸嘿嘿......"));
            hourlyChatConfig.setItems(chatTexts);
            return hourlyChatConfig;
        };
    }
    
    

}
