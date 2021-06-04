package utils;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/main/resources/TestsData.properties"})
public interface TestsData extends Config {
    //Common
    String otusURL();

    //HomeWork01
    String mainTitle();
    String celebratoryTitle();

    //HomeWork02
    String contactsTitle();
    String expectedAddress();
    String answerText();
    String testEmail();
    String subscribeConfirm();

    //HomeWork03
    String smartphone01();
    String smartphone02();
    String perforator01();
    String perforator02();

    //HomeWork04
    String firstNameRus();
    String lastNameRus();
    String firstNameEng();
    String lastNameEng();
    String birthday();
    String blogName();
    String country();
    String city();
    String englishSkill();
    String vk();
    String Facebook();
}