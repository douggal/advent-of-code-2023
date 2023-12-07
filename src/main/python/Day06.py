# Advent of Code 2023
# --- Day 6: Wait For It ---
# https://adventofcode.com/2023/day/6

import re
import math
import time

# fn = "06-test.txt"
fn = "./data/06-input.txt"

with open(fn) as f:
    for line in f:
        if line.strip() != "":
            if line[0:5] == "Time:":
                time_list = re.split(' +', line[5:].strip())
                # print(time_list)

            if line[0:9] == "Distance:":
                dist_list = re.split(' +', line[9:].strip())
                # print(dist_list)

# Part 1
start_time_p1 = time.time()

ts = list(map(int, time_list))
ds = list(map(int, dist_list))
# print(ts)
# print(ds)

ws = []  # wins

for t, rd in zip(ts, ds):
    # print("Race:", t, rd)
    ways_to_win = 0 # Accumulator(0)
    # t is time, d is the record distance
    for h in range(0, t + 1):
        d = (t - h) * h
        if d > rd:
            # print("Win! time, hold, rate, dist", t, h, h, d)
            ways_to_win += 1 # ways_to_win.AddValue(1)
    ws.append(ways_to_win)

answerP1 = math.prod(ws)

print("Answer part 1:", answerP1, " Nbr ways to win: ", sum(ws))
print("Part 1:  %s  milliseconds" % ((time.time() - start_time_p1)*1000))

# Part 2
start_time_p2 = time.time()

ts2 = int(re.sub(r"\s+", "", ' '.join(time_list)))
ds2 = int(re.sub(r"\s+", "", ' '.join(dist_list)))

ws = []  # wins

ways_to_win = 0  # Accumulator(0)
# t is time, d is the record distance
for h in range(0, ts2 + 1):
    d = (ts2 - h) * h
    if d > ds2:
        # print("Win! time, hold, rate, dist", t, h, h, d)
        ways_to_win += 1  # ways_to_win.AddValue(1)

answerP2 = ways_to_win

print("Answer part 2:", answerP2, " Nbr ways to win: ", ways_to_win)
print("Part 2:  %s milliseconds" % ((time.time() - start_time_p2)*1000))
