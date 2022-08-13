package com.example.bot.share.function;

import hundun.miraifleet.framework.core.botlogic.BaseBotLogic;
import hundun.miraifleet.framework.core.function.BaseFunction;
import hundun.miraifleet.framework.core.function.FunctionReplyReceiver;
import lombok.Getter;
import net.mamoe.mirai.console.command.AbstractCommand;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin;


public class TemplateFunction extends BaseFunction<Void> {

    @Getter
    private final CompositeCommandFunctionComponent commandComponent;

    public TemplateFunction(
            BaseBotLogic botLogic,
            JvmPlugin plugin, 
            String characterName
            ) {
        super(
                botLogic,
                plugin, 
                characterName, 
                "TemplateFunction",
                null
                );
        this.commandComponent = new CompositeCommandFunctionComponent();
    }

    
    @Override
    public AbstractCommand provideCommand() {
        return commandComponent;
    }

    public class CompositeCommandFunctionComponent extends AbstractCompositeCommandFunctionComponent {

        public CompositeCommandFunctionComponent() {
            super(plugin, botLogic.getUserCommandRootPermission(), characterName, functionName, "midi");
        }

        @SubCommand
        public void mySubCommand(CommandSender sender) {
            if (!checkCosPermission(sender)) {
                return;
            }
            FunctionReplyReceiver receiver = new FunctionReplyReceiver(sender, log);
            receiver.sendMessage("Hello world");
        }
    }
}
