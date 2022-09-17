package hundun.miraifleet.reminder.share.function.reminder;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.quartz.CronExpression;

import hundun.miraifleet.framework.core.botlogic.BaseBotLogic;
import hundun.miraifleet.framework.core.function.BaseFunction;
import hundun.miraifleet.framework.core.function.FunctionReplyReceiver;
import hundun.miraifleet.framework.helper.repository.SingletonDocumentRepository;
import hundun.miraifleet.reminder.share.function.reminder.data.ReminderItem;
import hundun.miraifleet.reminder.share.function.reminder.data.ReminderList;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.command.AbstractCommand;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.ConsoleCommandSender;
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;

/**
 * @author hundun
 * Created on 2021/08/13
 */
public class ReminderFunction extends BaseFunction {
    public static final String NAME_PART_SPLIT = "|";
    public static final String IMAGE_CODE_PREFIX = "IMAGE:";
    public static final String AUDIO_CODE_PREFIX = "AUDIO:";
    
    private SingletonDocumentRepository<ReminderList> reminderListRepository;
    
    private Map<String, CronExpression> cronExpressionCaches = new HashMap<>();
    @Setter
    private boolean logMinuteClockArrival = false;
    @Getter
    private final CompositeCommandFunctionComponent commandComponent;
    
    private final ReminderMessageCodeParser reminderMessageCodeParser;
    
    public ReminderFunction(
            BaseBotLogic baseBotLogic,
            JvmPlugin plugin,
            String characterName,
            @Nullable Supplier<ReminderList> reminderListDefaultDataSupplier
            ) {
        super(
            baseBotLogic,
            plugin,
            characterName,
            "ReminderFunction"
            );
        this.reminderListRepository = new SingletonDocumentRepository<>(plugin, 
                resolveDataRepositoryFile("ReminderListRepository.json"), 
                ReminderList.class, 
                reminderListDefaultDataSupplier);
        botLogic.getPluginScheduler().repeating(60 * 1000, new ReminderTimerTask());
        this.commandComponent = new CompositeCommandFunctionComponent();
        this.reminderMessageCodeParser = new ReminderMessageCodeParser();
    }

    @Override
    public AbstractCommand provideCommand() {
        return commandComponent;
    }
    
    @Override
    public AbstractCommand provideDebugCommand() {
        return new DebugCompositeCommandFunctionComponent();
    }

    public class CompositeCommandFunctionComponent extends AbstractCompositeCommandFunctionComponent {
        public CompositeCommandFunctionComponent() {
            super(plugin, botLogic, new UserLevelFunctionComponentConstructPack(characterName, functionName));
        }

        
        @Deprecated
        //@SubCommand("查询提醒")
        public void listReminderListChatConfig(CommandSender sender) {
            if (!checkCosPermission(sender)) {
                return;
            }
            ReminderList reminderList  = reminderListRepository.findSingleton();
            sender.sendMessage(itemsToText(reminderList.getItems()));
        }

        @Deprecated
        //@SubCommand("删除提醒")
        public void deleteReminderListChatConfig(CommandSender sender, int id) {
            if (!checkCosPermission(sender)) {
                return;
            }
            ReminderList reminderList  = reminderListRepository.findSingleton();
            reminderList.getItems().remove(id);
            sender.sendMessage("OK");
        }

        @Deprecated
        //@SubCommand("创建提醒")
        public void insertReminderListChatConfig(CommandSender sender,
                String cornRawFomat,
                String countRawFomat,
                String text
                ) {
            if (!checkCosPermission(sender)) {
                return;
            }
            Integer count;
            if (countRawFomat.contains("无限")) {
                count = null;
            } else {
                try {
                    countRawFomat = countRawFomat.replace("次", "");
                    count = Integer.valueOf(countRawFomat);
                } catch (Exception e) {
                    sender.sendMessage("参数格式不正确：" + countRawFomat);
                    return;
                }
            }
            ReminderList reminderList  = reminderListRepository.findSingleton();
            reminderList.getItems().add(ReminderItem.Factory.create(cornRawFomat, text, count, null, null));
            reminderListRepository.saveSingleton(reminderList);
            sender.sendMessage("OK");
        }

        
    }
    
    public class DebugCompositeCommandFunctionComponent extends AbstractCompositeCommandFunctionComponent {
        public DebugCompositeCommandFunctionComponent() {
            super(plugin, 
                    botLogic, 
                    new DebugLevelFunctionComponentConstructPack(characterName, functionName)
                    );
        }
        
        @SubCommand("list")
        public void listReminderListChatConfig(ConsoleCommandSender sender) {
            if (!checkCosPermission(sender)) {
                return;
            }
            ReminderList reminderList  = reminderListRepository.findSingleton();
            sender.sendMessage(itemsToText(reminderList.getItems()));
        }


        @SubCommand("clockArrive")
        public void debugTimerCallReminderItem(ConsoleCommandSender sender, String timeString) {
            if (!checkCosPermission(sender)) {
                return;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日H时m分");
            Date date;
            try {
                date = dateFormat.parse(timeString);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            logHourlyHeatBeat(calendar);
            //hourlyChatClockArrive(calendar);
            customRemiderClockArrive(calendar);
        }
    }




    private static String itemsToText(List<ReminderItem> items) {
        StringBuilder builder = new StringBuilder();
        builder.append("items:\n");
        for (int i = 0; i < items.size(); i++) {
            ReminderItem item = items.get(i);
            builder.append("id:").append(i).append("\t");
            builder.append(item.toString()).append("\n");
        }
        return builder.toString();
    }

    protected CronExpression getCronExpression(String cronText) {
        if (!cronExpressionCaches.containsKey(cronText)) {
            try {
                CronExpression cronExpression = new CronExpression(cronText);
                cronExpressionCaches.put(cronText, cronExpression);
            } catch (ParseException e) {
                log.error(e);
            }

        }
        return cronExpressionCaches.get(cronText);
    }




    protected boolean useReminderList(List<ReminderItem> items, Collection<Bot> bots, Calendar now) {
        List<ReminderItem> needRemove = new ArrayList<>();
        boolean modified = false;
        for (int i = 0; i < items.size(); i++) {
            ReminderItem reminderItem = items.get(i);
            if (!checkTimeConditions(reminderItem, now) || (reminderItem.getCount() != null && reminderItem.getCount() == 0)) {
                continue;
            }
            log.info(String.format("定时任务 id = %s 时间条件满足", 
                    i
                    ));
            for (Bot bot: bots) {
                useReminderItem(reminderItem, bot, now);
            }
            if (reminderItem.getCount() != null && reminderItem.getCount() > 0) {
                reminderItem.setCount(reminderItem.getCount() - 1);
                log.info(String.format("定时任务 id = %s 次数变为 %s", 
                        i,
                        reminderItem.getCount()
                        ));
                modified = true;
                if (reminderItem.getCount() == 0) {
                    needRemove.add(reminderItem);
                }
            }
        }
        items.removeAll(needRemove);
        
        return modified;
    }

    protected void useReminderItem(ReminderItem reminderItem, Bot bot, Calendar now) {

        for (Group group : bot.getGroups()) {
            if (!checkCosPermission(bot, group)) {
                continue;
            }
            FunctionReplyReceiver receiver = new FunctionReplyReceiver(group, log);
            if (reminderItem.getReminderMessageCodes() != null) {
                List<Message> messages = reminderMessageCodeParser.parse(receiver, reminderItem.getReminderMessageCodes());
                messages.forEach(it -> receiver.sendMessage(it));
            }
        }

    }

    protected boolean checkTimeConditions(ReminderItem reminderItem, Calendar now) {
        Date date = now.getTime();
        CronExpression cronExpression = getCronExpression(reminderItem.getCron());
        return cronExpression != null && cronExpression.isSatisfiedBy(date);
    }



    


    

    private void logHourlyHeatBeat(Calendar now) {
        if (now.get(Calendar.MINUTE) == 0) {
            log.info("HourlyHeatBeat");
        }
    }

//    private void hourlyChatClockArrive(Calendar now) {
//        Collection<Bot> bots = Bot.getInstances();
//        useReminderList(hourlyChatReminderItems, bots, now);
//    }

    protected void customRemiderClockArrive(Calendar now) {
        ReminderList reminderList = reminderListRepository.findSingleton();
        if (reminderList == null) {
            return;
        }
        Collection<Bot> bots = Bot.getInstances();
        boolean modified = useReminderList(reminderList.getItems(), bots, now);
        if (modified) {
            reminderListRepository.saveSingleton(reminderList);
        }

    }
    
    private class ReminderMessageCodeParser {
        
        
        
        private static final String IMAGE_FOLDER = "images/";
        private static final String AUDIO_FOLDER = "audios/";
        
        public ReminderMessageCodeParser() {
            resolveFunctionDataFile(IMAGE_FOLDER).mkdir();
            resolveFunctionDataFile(AUDIO_FOLDER).mkdir();
        }
        
        public List<Message> parse(FunctionReplyReceiver receiver, List<String> codes) {
            return codes.stream()
                    .map(it -> parse(receiver, it))
                    .filter(it -> it != null)
                    .collect(Collectors.toList());
        }
        
        @Nullable
        public Message parse(FunctionReplyReceiver receiver, String code) {
            if (code.startsWith(IMAGE_CODE_PREFIX)) {
                String namePart = code.substring(IMAGE_CODE_PREFIX.length());
                String[] fileNameCandidates = namePart.split(NAME_PART_SPLIT);
                int usingIndex = (int) Math.random() * fileNameCandidates.length;
                String fileName = fileNameCandidates[usingIndex];
                File file = resolveFunctionDataFile(IMAGE_FOLDER + fileName);
                log.info(String.format("ReminderMessageCodeParser using %s%s, exists = %s", 
                        fileName, 
                        fileNameCandidates.length > 1 ? (" from random size" + fileNameCandidates.length) : "",
                        file.exists()
                        ));
                if (!file.exists()) {
                    return null;
                }
                var externalResource = ExternalResource.create(file);
                return receiver.uploadImageAndCloseOrNotSupportPlaceholder(externalResource);
            } else if (code.startsWith(AUDIO_CODE_PREFIX)) {
                String fileName = code.substring(AUDIO_CODE_PREFIX.length());
                File file = resolveFunctionDataFile(AUDIO_FOLDER + fileName);
                log.info(String.format("ReminderMessageCodeParser using %s, exists = %s", fileName, file.exists()));
                if (!file.exists()) {
                    return null;
                }
                var externalResource = ExternalResource.create(file);
                return receiver.uploadVoiceAndCloseOrNotSupportPlaceholder(externalResource);
            } else {
                return new PlainText(code);
            }
        }
    }

    private class ReminderTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                if (logMinuteClockArrival) {
                    log.info("MinuteClockArrival, this = " + Integer.toHexString(hashCode()));
                }
                Calendar now = Calendar.getInstance();
                logHourlyHeatBeat(now);
                //hourlyChatClockArrive(now);
                customRemiderClockArrive(now);
            } catch (Exception e) {
                log.error("ReminderTimerTask error:", e);
            }
        }



    }

}
