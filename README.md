# Herd Behaviour Simulator

## Build
```
cd <project_root>
./gradlew shadowJar
```

## Running
```
java -enableassertions -jar hbs-core/build/libs/hbs-core-1.0-SNAPSHOT-all.jar classpath:config/herd-behaviour-config.xml
```