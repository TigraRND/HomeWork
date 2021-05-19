import org.aeonbits.owner.Config;

@Config.Sources({"file:src/main/resources/TestsData.properties"})
public interface TestsData extends Config {
    String mainTitle();
    String celebratoryTitle();
    String contactsTitle();
    String expectedAddress();
    String answerText();
    String testEmail();
    String subscribeConfirm();
    String smartphone01();
    String smartphone02();
    String otusURL();
}
