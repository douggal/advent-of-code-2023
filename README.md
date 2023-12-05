# Advent of Code 2023
My solutions to the Advent of Code programming contest December 2023.

Advent of Code website:  [Advent of Code](https://adventofcode.com)

Solutions are in Scala 3 unless otherwise noted.

1. Day  1:  Trebuchet?!
2. Day  2:  Cube Conundrum
3. Day  3:  Gear Ratios
4. Day  4:  Scratchcards

### Notes

#### Day 1
- [Regex101](https://regex101.com/)
- I planned to simply filter each string for digits, but I could not figure out how to use Scala's string filter 
method with regex.  (I discovered later a map and toVector is needed to 
convert each char in the input to a string and turn the sequence into 
a Vector before using filter w/regex.)
- This was fortunate.  In switching to a pattern matcher with a vector of 
captured groups it set me up for success in part 2.

```text
Part 1:  What is the sum of all of the calibration values?  54159
Part 1 run time approx 16 milliseconds
Part 2: What is the sum of all of the calibration values?  53866
Part 2 run time approx 15 milliseconds
```
#### Day 2
- I set out to use Functional Programming techniques, but I find mutable vars and for loops get the job done. 

```text
Part 1: Determine which games would have been possible if the bag had been loaded with only
12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?  2331
Part 2: For each game, find the minimum set of cubes that must have been present. 
What is the sum of the power of these sets?  71585
```

#### Day 3
- Today's puzzle might serve as a good example of why mutable variables can be a problem
- For part 2, I put the part numbers adjacent to gear into a mutable Map (dictionary) and then found 
the answer by finding pairs of parts in the Map connected by same gear.
- What I didn't see and didn't think of is what happens when a part number appears more than once in the engine diagram data.
- Grids (Amit's Grid parts and relationships)[https://www.redblobgames.com/grids/parts/]
- 1-D array rather than 2-D array: (Chutes and Ladders)[https://docs.swift.org/swift-book/documentation/the-swift-programming-language/controlflow/]
- Seemed like a good idea to use a 1-D array avoid end of line checking, but 
in fact the problem is still there.  My data set didn't have a  number end on one line 
with new number on next, so I got away with w/o checking for this.

```text
Part 1: What is the sum of all of the part numbers in the engine schematic? A:  529618
Part 2: What is the sum of all of the gear ratios in your engine schematic?  A:  77509019
```

#### Day 4
- Confidence builder today. Easier than Day 3
- It's a win.  Examples on Stackoverflow clicked and I built immutable data structure from the input file.

```text
Part 1: How many points are they worth in total?  A: 23941 in approx 42 milliseconds
Part 2: Including the original set of scratchcards, how many total scratchcards do you end up with? A: 5571760 in approx 5635 milliseconds
```
