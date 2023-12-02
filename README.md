# Advent of Code 2023
My solutions to the Advent of Code December 2023.

Advent of Code website:  [Advent of Code](https://adventofcode.com)

Solutions are in Scala 3 unless otherwise noted.

TODO: My goals for this year's AoC are to have fun, ...

1. Day   1:  Trebuchet?!
2. Day   2:  Cube Conundrum

### Notes

#### Day 1
- [Regex101](https://regex101.com/)
- Could not figure out how to use Scala's string filter method with regex.  Discovered later a map and toVector is needed to convert each char in the input to a string and turn the sequence into a Vector before using filter w/regex.
- This was fortunate.  In switching a pattern matcher with capture groups it set me up for success in part 2.

```text
Part 1:  What is the sum of all of the calibration values?  54159
Part 1 run time approx 16 milliseconds
Part 2: What is the sum of all of the calibration values?  53866
Part 2 run time approx 15 milliseconds
```
#### Day 2
