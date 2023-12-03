# Advent of Code 2023
My solutions to the Advent of Code programming contest December 2023.

Advent of Code website:  [Advent of Code](https://adventofcode.com)

Solutions are in Scala 3 unless otherwise noted.

1. Day  1:  Trebuchet?!
2. Day  2:  Cube Conundrum
3. Day  3:  Gear Ratios

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
- (Amit's Grid parts and relationships)[https://www.redblobgames.com/grids/parts/]
