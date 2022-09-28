package hundun.miraifleet.reminder.plugin;

import org.jetbrains.annotations.NotNull;

import hundun.miraifleet.reminder.plugin.botlogic.ReminderBotLogic;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;


public class ReminderPlugin extends JavaPlugin {
    public static final ReminderPlugin INSTANCE = new ReminderPlugin(); 
    
    ReminderBotLogic botLogic;
    
    public ReminderPlugin() {
        super(new JvmPluginDescriptionBuilder(
                "hundun.fleet.reminder",
                "0.1.3"
            )
            .build());
    }
    
    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        
    }
    
    @Override
    public void onEnable() {
        botLogic = new ReminderBotLogic(this);
        botLogic.onBotLogicEnable();
    }
    
    @Override
    public void onDisable() {
        botLogic.onDisable();
        // 由GC回收即可
        botLogic = null;
    }
}
