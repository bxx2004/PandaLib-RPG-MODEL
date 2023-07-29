package net.bxx2004.pandalib.bukkit.rpg.view;

import net.bxx2004.pandalib.PandaLib;
import net.bxx2004.pandalib.bukkit.language.application.DialogWindow;
import net.bxx2004.pandalib.bukkit.language.component.ActionChatText;
import net.bxx2004.pandalib.bukkit.listener.PListener;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.function.Consumer;

public class EntityTalk {
    private DialogWindow window;
    private String sender;
    private String content;
    private ActionChatText[] text;
    private PListener listener;
    private Entity entity;
    private boolean animation;
    public EntityTalk(Entity entity){
        this.entity = entity;
        this.sender = entity.getCustomName() == null ? "服务器: " : entity.getCustomName();
    }
    public EntityTalk content(String content){
        this.content = content;
        return this;
    }
    public EntityTalk answer(ActionChatText... text){
        this.text = text;
        return this;
    }
    public EntityTalk build(){
        this.window = new DialogWindow(sender,content,text);
        listener = new PListener(){
            @EventHandler
            public void onClick(PlayerInteractEntityEvent event){
                if (event.getRightClicked().equals(entity)){
                    if (animation){
                        window.sendAnimation(event.getPlayer());
                    }else {
                        window.send(event.getPlayer());
                    }
                    unhook();
                }
            }
        }.hook(PandaLib.initPlugin.getName());
        return this;
    }
    public EntityTalk animation(boolean a){
        animation = a;
        return this;
    }
    public EntityTalk click(Consumer<Integer> consumer){
        window.click( (u,s) -> {
            consumer.accept(s);
        });
        return this;
    }
    public void destroy(){
        listener.unhook();
    }
}
