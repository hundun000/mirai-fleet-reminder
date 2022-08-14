package hundun.miraifleet.reminder.plugin.botlogic;

import hundun.miraifleet.framework.core.botlogic.BaseJavaBotLogic;
import hundun.miraifleet.framework.starter.botlogic.function.CharacterAdminHelperFunction;
import hundun.miraifleet.reminder.share.function.reminder.ReminderFunction;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;


public class ReminderBotLogic extends BaseJavaBotLogic {

    public ReminderBotLogic(JavaPlugin plugin) {
        super(plugin, "定时提醒");

    }

    @Override
    protected void onFunctionsEnable() {
        registerFunction(new ReminderFunction(this, plugin, characterName, 
                ReminderDefaultConfigAndData.reminderListDefaultDataSupplier(),
                null
                ));

        registerFunction(new CharacterAdminHelperFunction(this, plugin, characterName));

    }
    
}
