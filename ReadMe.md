# Take Home Lab 2 

### CS-4532: Concurrent-Programming

## Challenge 

This problem was originally based on the Senate bus at 
Wellesley College. This problem is taken from the book 
_“Little book of Semaphores”_, page 211.

Riders come to a bus stop and wait for a bus. 
When the bus arrives, all the **waiting** riders invoke `boardBus()`, 
but anyone who _arrives while the bus is boarding has to wait_ 
for the next bus. 

The capacity of the bus is **50** people; 
if there are **more than 50** people waiting, 
some will have to **wait** for the next bus. 

When all the waiting riders have boarded, the bus can 
invoke `depart()`. If the bus arrives when there are **no** riders, 
it should `depart()` immediately.

## How to run ?

To execute the program, 
1. download the zip file from releases.
2. then execute the batch file to run the code in command line

Tested on JRE v1.8

I have hardcoded the hyper parameters into the code. 
So you might have to rebuild from the source if you want to adjust them.

The tests are run with following parameters (small values because the output would become very long if large values are used):
1. AVAILABLE_SEATS = 5
2. RIDER_MEAN_SPAWN_RATE = 300
3. RIDERS = 15
4. BUS_MEAN_SPAWN_RATE = 12000
5. BUSES = 3