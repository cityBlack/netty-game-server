package com.lzh.game.start.model.item.action;

import com.lzh.game.socket.annotation.Action;
import com.lzh.game.socket.annotation.RequestMapping;
import com.lzh.game.start.cmd.CmdMessage;
import com.lzh.game.start.model.item.packet.UseItemRequest;
import com.lzh.game.start.model.i18n.I18n;
import com.lzh.game.start.model.i18n.RequestException;
import com.lzh.game.start.model.item.service.ItemService;
import com.lzh.game.start.model.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Action
@Slf4j
public class ItemAction {

    @Autowired
    private ItemService itemService;

}
