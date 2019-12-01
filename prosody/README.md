For the original paper (written in Persian), see: [Automatic Detection of Prosodic Meter of Classical Persian Poetry](https://mavaji.github.io/2013/05/15/fltl2013.html)

## How to run:
### Unzip database
```shell script
cd lingu
lingu$ unzip ling_db.zip
```

### Build
```shell script
cd lingu
lingu$ mvn clean install
```

### Run
```shell script
cd prosody
lingu/prosody$ java -jar target/prodosy-jar-with-dependencies.jar
```