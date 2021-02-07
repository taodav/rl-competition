import random
import time
import math

 
##Remember to force everything to be in bounds in makeObservation
##p.addDoubleParam("GoalPos", 1.0d); //should be  0.8 <= x < 1.9
##p.addDoubleParam("dt", 0.05d); //should be between 0.01 and 0.09		

##p.addDoubleParam("t1dotscale", 1.0d); //should be between 0.1 and 1.0
##p.addDoubleParam("t2dotscale", 1.0d); //should be between 0.1 and 1.0

##p.addDoubleParam("t1offset", 0.0d); //should be between -PI and PI, remember to adjust task spec
##p.addDoubleParam("t2offset", 0.0d); //should be between -PI and PI, remember to adjust task spec
##p.addDoubleParam("t1dotoffset", 0.0d); //should be between -1.0 and 1.0, remember to adjust task spec
##p.addDoubleParam("t2dotoffset", 0.0d); //should be between -1.0 and 1.0, remember to adjust task spec


random.seed(time.time())

#GoalPos
def sampleP1(num):
	return round(random.uniform(0.8,1.9),1)

#dt
def sampleP2(num):
		return round(random.uniform(0.01,0.09),2)

#t1dotscale
def sampleP3(num):
	return random.uniform(0.1,1.01)

#t2dotscale
def sampleP4(num):
	return random.uniform(0.1,1.01)
	
	
#t1offset
def sampleP5(num):
	return random.uniform(-math.pi,math.pi)	

#t2offset
def sampleP6(num):
	return random.uniform(-math.pi,math.pi)

#t1dotoffset
def sampleP7(num):
	return random.uniform(-1.0,1.01)	

#t2dotoffset
def sampleP8(num):
	return random.uniform(-1.0,1.01)


def main():
	for i in range(100):
		print "%f,%f" % (sampleP1(i),sampleP2(i))

if __name__ == "__main__":
	main()
