package com.lzh.game.repository.db.persist;

import com.lzh.game.repository.db.Element;
import com.lzh.game.repository.db.PersistRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class DefaultPersistConsumer implements PersistConsumer {

    private PersistRepository repository;

    public DefaultPersistConsumer(PersistRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onConsumer(Element element) {

        if (Objects.nonNull(element)) {
            if (log.isDebugEnabled()) {
                log.debug("Consume {} to mongodb.", element.getEntity());
            }
            if (serialize(element)) {
                typeStrategy(element);
            }
        }
    }

    @Override
    public PersistRepository repository() {
        return repository;
    }

    @Override
    public boolean serialize(Element element) {
        return element.getEntity().serialize();
    }

    protected void typeStrategy(Element element) {

        element.eventBack(new Element.EventTypeBack() {
            @Override
            public void onSave(Element element) {
                save(element);
            }

            @Override
            public void onUpdate(Element element) {
                update(element);
            }

            @Override
            public void onDeleter(Element element) {
                deleter(element);
            }
        });
    }

    protected void save(Element element) {
        repository.save(element.getEntity());
    }

    protected void update(Element element) {
        repository.update(element.getEntity());
    }

    protected void deleter(Element element) {
        repository.deleter(element.getEntity());
    }
}
