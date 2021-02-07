import random
import time

# p.addDoubleParam("scaleP",1.0d);//should be between 0 and 1.0, probably not too close to 0
#  p.addDoubleParam("scaleV",1.0d);//should be between 0 and 1.0, probably not too close to 0
# 
#  
# 
#  p.addDoubleParam("pOffset",0.0d); //should be between -1.0 and 1.0, remember to adjust task spec
#  p.addDoubleParam("vOffset",0.0d); //should be between -1.0 and 1.0, remember to adjust task spec
# 
# 
#  p.addDoubleParam("pNoiseDivider",20.0d); //should be between 5.0 and 20.0
#  p.addDoubleParam("vNoiseDivider",20.0d); //should be between 5.0 and 20.0
# 
#  p.addDoubleParam("AccelBiasMean",1.0d); //should be between .8 and whatever larger, say 2.0
# 


random.seed(time.time())

#scaleP
def sampleP1(num):
	return random.uniform(.25,1.0)

#scaleV
def sampleP2(num):
		return random.uniform(.25,1.0)

#pOffset
def sampleP3(num):
	return random.uniform(-1.0,1.0)

#vOffset
def sampleP4(num):
	return random.uniform(-1.0,1.0)
	
	
#pNoiseDivider
def sampleP5(num):
	return random.uniform(5.0,20.0)	

#vNoiseDivider
def sampleP6(num):
	return random.uniform(5.0,20.0)	

#AccelBiasMean
def sampleP7(num):
	return random.uniform(.8,1.2)	



def main():
	for i in range(10000):
		print "%f,%f" % (sampleP1(i),sampleP2(i))

if __name__ == "__main__":
	main()