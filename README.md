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
8. Day  8:  Haunted Wasteland
9. Day  9:  Mirage Maintenance
10. Day 10: Pipe Maze
11. Day 11: Cosmic Expansion
12. Day 12: Hot Springs  (DNF)
13. Day 13: Point of Incidence (DNF)
14. Day 14: Parabolic Reflector Dish
15. Day 15: Lens Library

-25. Day 25: Snowverload (DNF)

### Straightforward to solve
4,6,9,15 (part 1)

### Hard to solve
7 (part 2), 8 (part2), 10 (part 2)

### My stats at the end of the official contest
As of end of day 25 December 2023
- 21 stars,
- completed 8 with both parts of puzzle,
- and total of 13 with at least one part complete.
- On par with previous years, but I'd hoped for higher star count.

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
- Part 1 - Go,Part 2 - No go
- I made 3 mistakes 
- 1) My initial idea was to assign a numeric score to each hand within type, but 
this did not work.  Backtracked and took the hint in the description and mapped 
the relative card strengths to letters of the alphabet so that each hand sorts out, ascending, by strength.
- 2) overwriting keys in a mutable.Map - I could have caught this if I'd check # of ranked hands and compared with input data
- 3) not looking for all the type combos - I missed Full House
- So I was late, no points for today, but I did finish and collect 1 star
- [Count chars in a string](https://stackoverflow.com/questions/61631731/char-count-in-string)

```text
Part 1: Find the rank of every hand in your set. What are the total winnings? A: 251806792
```

#### Day 8
- Keep it simple and just pick up the stars today
- Part 1 solved, part 2 no go.  Tried brute force but after 24 hours still had not reached a solution.

```text
Part 1: Starting at AAA, follow the left/right instructions. 
How many steps are required to reach ZZZ?  A: 23147
```

#### Day 9
- One of the easier puzzles

```text
Part 1: Analyze your OASIS report and extrapolate the next value for each history. 
What is the sum of these extrapolated values?  A: 1916822650
Run time approx 71 milliseconds

Part 2: Analyze your OASIS report again, this time extrapolating the previous value for each history. 
What is the sum of these extrapolated values?  A: 966
Run time approx 16 milliseconds
```

#### Day 10
- [DFS](https://en.wikipedia.org/wiki/Depth-first_search)
- Fun activity for a rainy Sunday
- Part 1: do
- Part 2: DNF
- Started with DFS traversal algo, and after a while realized I needed a BFS

```text
BFS:  Part 1: Find the single giant loop starting at S. How many steps along the loop does it take to get 
from the starting position to the point farthest from the starting position?   A: 7005
Run time approx 11579 milliseconds
```

#### Day 11
- [Taxi cab or Manhattan distance](https://en.wikipedia.org/wiki/Taxicab_geometry)
- Straightforward once I realized I need only transform coordinates of the galaxies. 
No need to expand the whole universe.

```text
Part 1: Expand the universe, then find the length of the shortest path between every pair of galaxies. 
What is the sum of these lengths? A: 10292708
Run time approx 75 milliseconds

Part 2: Starting with the same initial image, expand the universe according to these new rules, then 
find the length of the shortest path between every pair of galaxies. What is the sum of these lengths?  A: 790194712336
Run time approx 36 milliseconds
```

#### Day 12
- DNF

#### Day 13
- DNF

#### Day 14
- Part 1 was straightforward
- Part 2 DNF - my naive solution of just rolling in each of the 4 directions didn't work
- Estimate would need 135 days to complete!
- 
```text
Part 1: Tilt the platform so that the rounded rocks all roll north.
 Afterward, what is the total load on the north support beams?  A: 109098
Run time approx 8 milliseconds
```

#### Day 15
- Part 1 straightforward

#### Day 25
- First thought was to find bridges in input data.  Help with algo from:
- [Udemy "Graph Theory Algorithms" by William Fiset](https://www.udemy.com/course/graph-theory-algorithms/)
- Refactored the input.  For the algo to work I needed to
represent the input as an adjacency list which each node represented in the list once
regardless of which side of the ":" it appears on in the input.
- DNF :( no bridges found, need a different approach
- 31 May 2024:  found new approach using [Karger's Algorithm](https://en.wikipedia.org/wiki/Karger's_algorithm)


