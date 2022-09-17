package hundun.miraifleet.reminder.plugin.botlogic;

import java.util.Arrays;
import java.util.function.Supplier;

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
                    ReminderItem.Factory.createHourly(9, "九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？", "阿米娅_交谈2.amr", "九点_0.png|九点_1.png"),
                    ReminderItem.Factory.createHourly(10, "十点到了。欸嘿嘿......"),
                    ReminderItem.Factory.create("* 0 22 ? * 1", "现在是周日晚上10点。请博士记得完成本周剿灭作战。")
                    ));
            return reminderList;
        };
    }


    public static void main(String[] args) {
        String[] a = new String[] {};
        Object[] b = a;
        
    }

}
