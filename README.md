# Programming excercise

Implements the provided programming excercise, excluded here.

## Structure
The main code is in:
- `src/main/scala/Problem1.scala`
- `src/main/scala/Problem2.scala`

With each of the problems in its respective class.
Additionally, some helper classes were created:
- `Models.scala` contains all the case classes
- `Readers.scala` contains utilities for reading the input data

All input data is in `src/main/resources`

## Running the application
Each problem can be run on its own:

### Problem 1
```bash
sbt "runMain Problem1"    # Print all Best Group Prices
sbt "runMain Problem1 <ratesFile>" # Best Group Prices using rates from <ratesFile>
sbt "runMain Problem1 <ratesFile> <cabinPricesFile>" # Best Group Prices using rates and cabin prices from custom files
```

### Problem 2
Run using default data files
```bash
sbt "runMain Problem2"    # Print all PromotionCombos, combos for P1, and combos for P3
sbt "runMain Problem2 P2" # Print only combos for P2
```

Problem 2 can also be run with a custom file:
```bash
sbt "runMain PromotionsLoad"               # Print all combinable promotions loaded from data/promotions.txt
sbt "runMain PromotionsLoad otherFile.txt" # Print all combinable promotions loaded from otherFile.txt
```

Any line in this custom file that does not parse as a Promotion will be skipped
