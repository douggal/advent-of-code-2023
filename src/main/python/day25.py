# Advent of Code 2023
# --- Day 25: Snowverload ---
# https://adventofcode.com/2023/day/25

# Date written:  9/27/2024

import re
import math
import time
import copy
import networkx as nx
import matplotlib.pyplot as plt

# Get the current time
current_time = time.time()

# Get the current time as a struct_time object
current_time_struct = time.localtime()

# Format the time
formatted_time = time.strftime("%Y-%m-%d %H:%M:%S", current_time_struct)
print("Local time, start:", formatted_time)

# fn = "06-test.txt"
#fn = "./data/25-test-2.txt"
fn = "./data/25-input.txt"

# Regex pattern to extract 3 char strings (component names) from each line in the input file
p = re.compile("[a-z]{3}")

# Model the wiring diagram as graph of components (vertexes) stored as an adjacency list in a dict
# G zero is the graph as read from the input file
G0 = {}

# read input data, apply pattern, and begin to populate the graph, G
with open(fn) as f:
    for line in f:
        if line.strip() != "":
            l = p.findall(line)
            key = l[0]
            if key not in G0:
                G0[key] = {}
            for component in l[1:]:
                G0[key][component] = { "capacity":1}

for key in G0:
    print(f"Key {key}: ", " ".join(G0[key]))

# 2nd step - the adjacency list must have each vertex in the graph as a key in its dict,
# but input file is compressed to remove redundant data.
# For each vertex not listed in G, add vertex and populate what it's connected to.
new_vertices = []
G0_keys = [*G0]   # unpack keys into a List()
for key in G0:
    for node in G0[key]:
        if node not in G0_keys and node not in new_vertices:
            new_vertices.append(node)

print(f"New vertices: ", new_vertices)

# Create the full adjacency list representation of the wiring diagram
# by copying the file input and adding in nodes not already represented
G1 = copy.deepcopy(G0)
for node in new_vertices:
    G1[node] = {}
    for key in G0_keys:
        for n in G0[key]:
            if n == node:
                G1[key][n] = {"capacity":1}

for key in G1:
    for k in G1[key]:
        print(f"Key {key}: ", " ".join(G1[k]))



G = nx.Graph(G1)
nx.draw(G, with_labels=True, font_weight='bold')
plt.show()
plt.savefig("./data/wiring_diagram.png")

#cut_value, partition = nx.minimum_cut(G,'jqt','frs')
cut_value, partition = nx.minimum_cut(G,'dlk','zxm')
print(G)

# jqt: rhn xhk nvd
# rsh: frs pzl lsr
# xhk: hfx
# cmg: qnr nvd lhk bvb
# rhn: xhk bvb hfx
# bvb: xhk hfx
# pzl: lsr hfx nvd
# qnr: nvd
# ntq: jqt hfx bvb xhk
# nvd: lhk
# lsr: lhk
# rzs: qnr cmg lsr rsh
# frs: qnr lhk lsr



# Part 1
start_time_p1 = time.time()

print(f"Min cut {cut_value}")
print(f"Partition 1 {partition[0]}")
print(f"Partition 2 {partition[1]}")

answerP1 = len(partition[0]) * len(partition[1])

print("Answer part 1:", answerP1)
print("Part 1:  %s  milliseconds" % ((time.time() - start_time_p1)*1000))
