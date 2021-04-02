package com.lzh.game.start.model.target.model;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface TargetModelManage {
    /**
     * Get Target model by Sign
     * @param sign
     * @return
     */
    TargetModelStrategy getTargetModel(TargetModelSign sign);

    /**
     * 遍历所有TargetModel
     * @param consumer
     */
    void foreach(Consumer<TargetModelStrategy> consumer);

    Stream<TargetModelStrategy> stream();
}
