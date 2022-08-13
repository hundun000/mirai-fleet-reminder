package com.example.bot.plugin;

import org.jetbrains.annotations.NotNull;

import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;


public class TemplatePlugin extends JavaPlugin {
    public static final TemplatePlugin INSTANCE = new TemplatePlugin(); 
    
    TemplateBotLogic botLogic;
    
    public TemplatePlugin() {
        super(new JvmPluginDescriptionBuilder(
                "hundun.fleet.template",
                "0.1.0"
            )
            .build());
    }
    
    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        
    }
    
    @Override
    public void onEnable() {
        botLogic = new TemplateBotLogic(this);
        botLogic.onBotLogicEnable();
    }
    
    @Override
    public void onDisable() {
        botLogic.onDisable();
        // 由GC回收即可
        botLogic = null;
    }
}
