package hundun.miraifleet.reminder.share.function.reminder;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import hundun.miraifleet.framework.core.botlogic.BaseBotLogic;
import hundun.miraifleet.reminder.share.function.reminder.data.ReminderList;
import lombok.Getter;
import net.mamoe.mirai.console.command.AbstractCommand;
import net.mamoe.mirai.console.command.ConsoleCommandSender;
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin;

/**
 * @author hundun
 * Created on 2021/08/13
 */
public class SimpleReminderFunction extends ReminderFunction {
    
    @Getter
    private final CompositeCommandFunctionComponent simpleCommandComponent;

    public SimpleReminderFunction(
            BaseBotLogic baseBotLogic,
            JvmPlugin plugin,
            String characterName,
            @Nullable Supplier<ReminderList> reminderListDefaultDataSupplier
            ) {
        super(
            baseBotLogic,
            plugin,
            characterName,
            reminderListDefaultDataSupplier
            );
        this.simpleCommandComponent = new CompositeCommandFunctionComponent();
        this.setSkipRegisterCommand(false);
    }

    @Override
    public AbstractCommand provideCommand() {
        return simpleCommandComponent;
    }

    public class CompositeCommandFunctionComponent extends AbstractCompositeCommandFunctionComponent {
        public CompositeCommandFunctionComponent() {
            super(plugin, 
                    botLogic.getAdminCommandRootPermission(), 
                    characterName, 
                    functionName,
                    characterName + "Debug"
                    );
        }
        
        @SubCommand("debugList")
        public void listReminderListChatConfig(ConsoleCommandSender sender) {
            SimpleReminderFunction.this.getCommandComponent().listReminderListChatConfig(sender);
        }


        @SubCommand("debugClockArrive")
        public void debugTimerCallReminderItem(ConsoleCommandSender sender, String timeString) {
            SimpleReminderFunction.this.getCommandComponent().debugTimerCallReminderItem(sender, timeString);
        }
    }

}
