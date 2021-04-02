package com.lzh.game.client.support;

import com.lzh.game.common.bean.HandlerMethod;

public interface ActionMethodSupport {

    HandlerMethod getMethod(int cmd);

    void register(int cmd, HandlerMethod method);
}
