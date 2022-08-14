package hundun.miraifleet.reminder.plugin;

import hundun.miraifleet.reminder.plugin.ReminderPlugin;
import net.mamoe.mirai.console.plugin.PluginManager;
import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal;
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;

public class PluginTest {
    public static void main(String[] args) throws InterruptedException {
        MiraiConsoleTerminalLoader.INSTANCE.startAsDaemon(new MiraiConsoleImplementationTerminal());
        
        PluginManager.INSTANCE.loadPlugin(ReminderPlugin.INSTANCE);
        
        PluginManager.INSTANCE.enablePlugin(ReminderPlugin.INSTANCE);
    }
}
