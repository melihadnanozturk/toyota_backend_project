package com.mao.tytauth.service.impl;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
import org.jeasy.random.randomizers.text.StringRandomizer;

public class TestDataBuilder<T> {

    protected final EasyRandom generator;
    protected EasyRandomParameters parameters;

    protected T data;
    protected Class<T> clazz;

    public T build() {
        return data;
    }

    public TestDataBuilder(Class<T> clazz, boolean isRelation) {
        this.clazz = clazz;
        this.parameters = setRandomParameter();
        generator = new EasyRandom();

        if (isRelation) {
            createObject();
            return;
        }
        parameters.excludeField(FieldPredicates.isAnnotatedWith(
                OneToOne.class, OneToMany.class, ManyToOne.class, ManyToMany.class));
        createObject();
    }

    protected void createObject() {
        data = generator.nextObject(clazz);
    }

    protected EasyRandomParameters setRandomParameter() {
        this.parameters = new EasyRandomParameters();
        this.parameters.collectionSizeRange(1, 3);
        this.parameters.randomize(Long.class, LONG_RANDOMIZER);
        this.parameters.randomize(String.class, STRING_RANDOMIZER);

        return parameters;
    }

    private static final Randomizer<Long> LONG_RANDOMIZER = new LongRangeRandomizer(1L, 10L);
    private static final Randomizer<String> STRING_RANDOMIZER = new StringRandomizer(1, 10, 10);

}
