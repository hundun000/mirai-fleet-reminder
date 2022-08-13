package com.example.bot.plugin;

import com.example.bot.share.function.TemplateFunction;

import hundun.miraifleet.framework.core.botlogic.BaseJavaBotLogic;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;


public class TemplateBotLogic extends BaseJavaBotLogic {

    public TemplateBotLogic(JavaPlugin plugin) {
        super(plugin, "模板角色");

    }

    @Override
    protected void onFunctionsEnable() {
        var musicMidiFunction = new TemplateFunction(this, plugin, characterName);
        musicMidiFunction.setSkipRegisterCommand(false);

        registerFunction(musicMidiFunction);
    }
    
}
