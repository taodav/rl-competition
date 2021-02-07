import random
import time

random.seed(time.time())
def sampleP1(num):
	return random.randint(6, 12)
def sampleP2(num):
		return random.randint(10, 16)
def sampleP4(num):
		weights = [random.uniform(0,1.0),random.uniform(0,1.0) ,random.uniform(0,1.0),random.uniform(0,1.0),random.uniform(0,1.0),random.uniform(0,1.0),random.uniform(0,1.0)]
		return weights
def sampleP3(num):
		return random.uniform(1.0, 2.0)
		

# def sampleP1(num):
# 	whichGauss = random.random()
# 	val = -2.0
# 	while val < -1.0 or val > 1.0:
# 		if whichGauss < 1.0/3.0:
# 			val = random.gauss(0.0,0.5)
# 		elif whichGauss < 2.0/3.0:
# 			val = random.gauss(-0.7,0.1)
# 		else:
# 			val = random.gauss(0.8,0.2)
# 	return val
# 	
# def sampleP2(num):
# 	return random.uniform(-1.0,1.0)

def main():
	for i in range(10000):
		print "%f,%f" % (sampleP1(i),sampleP2(i))

if __name__ == "__main__":
	main()