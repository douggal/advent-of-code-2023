
# Advent of Code 2023

![Scala Version](https://img.shields.io/badge/Scala-3.3.1-blue?logo=scala)

This repository contains my personal solutions to the [Advent of Code 2023](https://adventofcode.com) programming challenges, implemented in **Scala 3**.

> ⚠️ Puzzle descriptions and input files are not included due to copyright restrictions.  
> Please visit the official Advent of Code website to view the original puzzles.

## Folder Structure

```
.
├── .idea/                # IDE settings (IntelliJ)
├── project/              # sbt project files
├── src/                  # Scala source code (solutions by day)
│   └── main/
│       └── scala/
│           └── resources/  # (data input files)
├── worksheets/           # Scala worksheets (experiments, drafts)
├── .gitattributes
├── .gitignore
├── LICENSE.txt
├── README.md
├── build.sbt             # sbt build definition
```

## Build and Run Instructions

### Prerequisites

- **Scala 3** installed ([installation guide](https://scala-lang.org/download/))
- **sbt** (Scala Build Tool) installed ([installation guide](https://www.scala-sbt.org/download.html))

### Building the Project

```bash
git clone https://github.com/douggal/advent-of-code-2023.git
cd advent-of-code-2023
sbt compile
```

### Running Solutions

To run a specific day's solution (replace `Day01` with the desired class name):

```bash
sbt "runMain aoc2023.Day01"
```

To run all tests (if available):

```bash
sbt test
```

> Input files are not included due to copyright restrictions. Please download your own puzzle inputs from the [Advent of Code website](https://adventofcode.com/2023).

## Contest Stats

As of December 25, 2023:
- ⭐ **21 stars** earned
- 🏁 **8 days** completed with both parts solved
- ✅ **13 days** completed with at least one part solved
- On par with previous years, though I had hoped for a higher star count.

## Difficulty Highlights

- **Straightforward:** Days 4, 6, 9, 15 (part 1)
- **Hard:** Days 7 (part 2), 8 (part 2), 10 (part 2)

## Daily Notes & Reflections

# Advent of Code 2023
This repository contains my personal solutions to the [Advent of Code 2023](https://adventofcode.com) programming
challenges, implemented in Scala 3.  Solutions are in Rust lang v1.90+ (2024 Edition) unless otherwise noted.

Each day's puzzle is organized into its own module.

> ⚠️ Puzzle descriptions and input files are not included due to copyright restrictions.
> Please visit the official Advent of Code website to view the
> original puzzles.


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

#### Day 2
- I set out to use Functional Programming techniques, but I find mutable vars and for loops get the job done.

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


#### Day 4
- Confidence builder today. Easier than Day 3
- It's a win.  Examples on Stackoverflow clicked and I built immutable data structure from the input file.


#### Day 5
- Almost gave up, but finally found success
- Used a regex to parse input file w/multiline matching.  Lost a lot of time trying to get this to work.
- Had to backtrack several times. Naieve solutions didn't work but with some extra thought the program completed.

#### Day 6
- Easiest day so far
- Used the extra time to compare run times between Python and Scala


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


#### Day 8
- Keep it simple and just pick up the stars today
- Part 1 solved, part 2 no go.  Tried brute force but after 24 hours still had not reached a solution.


#### Day 9
- One of the easier puzzles


#### Day 10
- [DFS](https://en.wikipedia.org/wiki/Depth-first_search)
- Fun activity for a rainy Sunday
- Part 1: do
- Part 2: DNF
- Started with DFS traversal algo, and after a while realized I needed a BFS


#### Day 11
- [Taxi cab or Manhattan distance](https://en.wikipedia.org/wiki/Taxicab_geometry)
- Straightforward once I realized I need only transform coordinates of the galaxies.
  No need to expand the whole universe.

#### Day 12
- DNF

#### Day 13
- DNF

#### Day 14
- Part 1 was straightforward
- Part 2 DNF - my naive solution of just rolling in each of the 4 directions didn't work
- Estimate would need 135 days to complete!


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
- Randomized algorithm using 15 node test data set took anywhere from &lt; 500 interations to over 32000
- DNF works with test data but not real data set.  Concluded the graph is too uniform for Karger's
- Oct 2024:  solve with Python &amp; NetworkX using min_cut() method
