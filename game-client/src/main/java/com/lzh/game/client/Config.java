package com.lzh.game.client;

import com.lzh.game.client.dispatcher.ResponseDispatcher;
import com.lzh.game.client.bootstrap.GameClientBootstrap;
import com.lzh.game.client.bootstrap.GameClientHandler;
import com.lzh.game.client.bootstrap.TcpClient;
import com.lzh.game.client.support.ActionMethodSupport;
import com.lzh.game.client.support.ActionMethodSupportImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class Config {

    @Bean
    public ActionMethodSupport methodSupport() {
        return new ActionMethodSupportImpl();
    }

    @Bean
    public ResponseDispatcher responseDispatcher(ActionMethodSupport methodSupport) {
        ResponseDispatcher dispatcher = new ResponseDispatcher(methodSupport);
        return dispatcher;
    }

    @Bean
    public GameClientHandler clientHandler(ResponseDispatcher responseDispatcher) {
        GameClientHandler clientHandler = new GameClientHandler(responseDispatcher);
        return clientHandler;
    }

    @Bean
    public TcpClient tcpClient(ResponseDispatcher responseDispatcher) {
        return new GameClientBootstrap(responseDispatcher);
    }
}
