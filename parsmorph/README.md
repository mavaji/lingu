# Pars Morph: A Persian Morphological Analyzer

Pars Morph is a rule-based Persian morphological analysis system, which analyzes the internal structure of word in Persian and determines the grammatical category and function of the word parts.
Being in link with a lexicon covering about 45000 lexemes and based on morpho-logical rules in Persian, it can analyze the structure of complex lexemes and their possible inflected forms and even out-of-lexicon words.

For the original paper (written in Persian), see: [Pars Morph: A Persian Morphological Analyzer](https://mavaji.github.io/2012/05/16/parsmorph.html)

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
cd parsmorph
lingu/parsmorph$ mvn tomcat:run
```

Via browser:
```
http://localhost:8080/parsmorph/
```