# TextAnalysis

A Java library and set of programs for analyzing text — computing readability scores, counting syllables, building word frequency buckets, and working with a dictionary.

## Requirements

- **Java 8 or later** — download from [Adoptium](https://adoptium.net/) (works on macOS, Windows, Linux)
- **Maven 3.6+** — download from [maven.apache.org](https://maven.apache.org/download.cgi), or use your IDE's built-in Maven

## Project Structure

```
TextAnalysis/
├── src/                        # Java source files
│   ├── Readability.java        # Main program — computes Flesch-Kincaid readability
│   ├── DictionaryUseExample.java  # Example program — demonstrates Dictionary usage
│   ├── TextLib.java            # Utility functions (file reading, sentence splitting)
│   ├── Document.java           # Document class with readability and word analysis
│   ├── Dictionary.java         # Singleton dictionary backed by a word list file
│   ├── FileInfo.java           # Data class holding filename + readability scores
│   ├── Word.java               # Data class: word + syllable count
│   ├── Word2.java              # Data class: word + frequency count
│   ├── WordBucket.java         # Word frequency container (list-based)
│   ├── WordBucket2.java        # Word frequency container (sorted, more efficient)
│   └── UseData.java            # Data class: word + usage frequency
├── data/
│   ├── syllables.txt           # Word-to-syllable-count reference list
│   └── Texts/
│       ├── allfeatures-ose-final.csv   # Ground-truth readability scores
│       └── AllTexts/           # Text files to analyze
└── pom.xml                     # Maven build file
```

## Building

```bash
# Compile all source files
mvn compile

# Package into a runnable JAR (output: out/text-analysis-1.0-SNAPSHOT.jar)
mvn package
```

## Running

### Readability analysis (main program)

Reads all texts listed in `data/Texts/allfeatures-ose-final.csv`, computes the Flesch-Kincaid readability score for each, and prints the average prediction error.

```bash
# After mvn package:
java -jar out/text-analysis-1.0-SNAPSHOT.jar

# Or run directly via Maven from the project root:
mvn exec:java -Dexec.mainClass=Readability
```

### Dictionary example

```bash
mvn exec:java -Dexec.mainClass=DictionaryUseExample
```

> **Note:** `DictionaryUseExample` expects a word list at `data/words.txt`. Place a plain-text file there with one lowercase word per line.

## Key Classes

| Class | Purpose |
|---|---|
| `Readability` | Flesch-Kincaid score calculation across a corpus |
| `Document` | OOP wrapper — sentences, words, vocab, syllables, FK score |
| `Dictionary` | Singleton dictionary; `buildDictionary(filename)` loads a word list |
| `TextLib` | Static helpers: read file, split sentences, parse syllable data |
| `WordBucket` | Stores words with duplicate-based frequency counting |
| `WordBucket2` | Same as `WordBucket` but keeps words sorted by frequency |

## Flesch-Kincaid Formula

```
Score = 206.835 - 1.015 * (words / sentences) - 84.6 * (syllables / words)
```

Higher scores indicate easier reading. Scores typically range from 0 (very difficult) to 100 (very easy).
