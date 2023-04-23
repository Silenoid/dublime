# Dublime

This tool is born with the main purpose of recording counter-audio files contained in a directory, like Source Engine audio files.

## Build

```shell script
mvn clean compile assembly:single
```

This will trigger the Maven assembly plugin, making sure that the resulting Jar will contain every dependency needed 
(in this case, only the `Flatlaf` one).

## Problems

### The UI not instantiating Form's elements
**Problem**: The app will run smoothly in the IDE but when packed in Jar it will throw an NPE on
 uninitiated GUI elements in the Form.

**Reason**: When IntelliJ runs the app, it manages the form instantiation thanks to a tool embedded in the IDE. This 
helps triggering the `MainForm.$$$setupUI$$$` process in order to get every instance injected in the JPanel class of
the respective form.
Once you have the Jar exported, this tool is not present anymore and should be provided in another way, 
like the Maven plugin:
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>ideauidesigner-maven-plugin</artifactId>
    <version>1.0-beta-1</version>
    <executions>
        <execution>
            <goals>
                <goal>javac2</goal>
            </goals>
        </execution>
    </executions>

    <configuration>
        <fork>true</fork>
        <debug>true</debug>
        <failOnError>true</failOnError>
    </configuration>
</plugin>
```
And, if needed, the dependency:
 ```xml
<dependency>
    <groupId>com.intellij</groupId>
    <artifactId>forms_rt</artifactId>
    <version>7.0.3</version>
</dependency>
```
These artifacts seems **not mature nor supported**.

**Solution**: I decided to let the IntelliJ UI Designer generate the sourcecode for those classes:

> Project > Settings > Editor > GUI Designer > Generate GUI into > Java Source Code

In this way, target will be populated with those classes that the UI setup process is expecting when the 
runtime will be detached from IntelliJ IDEA (and the GUI Designer tool).