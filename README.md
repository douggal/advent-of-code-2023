# Advent of Code 2023
My solutions to the Advent of Code programming contest December 2023.

Advent of Code website:  [Advent of Code](https://adventofcode.com)

Solutions are in Scala 3 unless otherwise noted.

1. Day  1:  Trebuchet?!
2. Day  2:  Cube Conundrum
3. Day  3:  Gear Ratios
4. Day  4:  Scratchcards
5. Day  5:  If You Give A Seed A Fertilizer
6. Day  6:  Wait For It
7. Day  7:  Camel Cards

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

#### Day 5
- Almost gave up, but finally found success
- Used a regex to parse input file w/multiline matching.  Lost a lot of time trying to get this to work.
- Had to backtrack several times. Naieve solutions didn't work but with some extra thought the program completed.

```text
Part 1: What is the lowest location number that corresponds to any of the initial seed numbers?  A: 240320250
Part 1 run time approx 6 milliseconds
Part 2: Consider all of the initial seed numbers listed in the ranges on the first line of the almanac.
What is the lowest location number that corresponds to any of the initial seed numbers?  A: 28580589
Part 2 run time approx 910402 milliseconds (about 15 minutes)
```

#### Day 6
- Easiest day so far
- Used the extra time to compare run times between Python and Scala

```text
Answer part 1:   625968  Nbr ways to win:         131
Answer part 2: 43663323  Nbr ways to win:  43_663_323

Python:
Part 1 Run time:    0.07725 milliseconds
Part 2 Run time: 5701       milliseconds

Scala:
Part 1 Run time:   7 milliseconds
Part 2 Run time: 404 milliseconds
```

#### Day 7
- I made 3 mistakes 
- 1) My initial idea was to assign a numeric score to each hand within type, but 
this did not work.  Backtracked and took the hint in the description and mapped 
the relative card strengths to letters of the alphabet so that each hand sorts out, ascending, by strength.
- 2) overwriting keys in a mutable.Map
- 3) not looking for all the type combos - I missed Full House
- So I was late, no points for today, but I did finish

```text

```
