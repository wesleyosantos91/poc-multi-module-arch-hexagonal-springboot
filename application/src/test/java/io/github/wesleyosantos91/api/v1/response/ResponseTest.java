package io.github.wesleyosantos91.api.v1.response;

import static org.junit.jupiter.api.Assertions.*;

import io.github.wesleyosantos91.utils.GetterAndSetterTester;
import java.io.Serializable;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

class ResponseTest {

    Set<Class<? extends Serializable>> allClasses;
    GetterAndSetterTester tester;

    @BeforeEach
    public void setUp() {
        tester = new GetterAndSetterTester();
        Reflections reflections = new Reflections("io.github.wesleyosantos91.api.v1.response");
        allClasses = reflections.getSubTypesOf(Serializable.class);
    }

    @Test
    @DisplayName("[domain] - Coverage all class the response package")
    void coverageClassTheDomainPackage() {
        for (Class<? extends Object> clazz : allClasses)
            tester.testClass(clazz);
    }
}