import random
import time
import os

random.seed(time.time())
def sampleP1(num):
	return random.randint(6, 12)
def sampleP2(num):
		return random.randint(10, 16)
def sampleP4(num):
		weights = [random.uniform(0.5,1.0),random.uniform(0.5,1.0) ,random.uniform(0.5,1.0),random.uniform(0.5,1.0),random.uniform(0.5,1.0),random.uniform(0.5,1.0),random.uniform(0.5,1.0)]
		return weights
def sampleP3(num):
		return random.uniform(1.3, 2.0)
def sampleP5(num):
		return random.uniform(0.0, 0.6)
def sampleP6(num):
		return random.randint(0,3)
		

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
	paramfile = open('params.txt','w')
	for i in range(500):
		pieceWeights = sampleP4(i)
		print "%d,%d,%f, %f, %f, %f, %f, %f, %f, %f, %f, %d" % (sampleP1(i),sampleP2(i), sampleP3(i),pieceWeights[0],pieceWeights[1],pieceWeights[2],pieceWeights[3],pieceWeights[4],pieceWeights[5],pieceWeights[6], sampleP5(i),  sampleP6(i))
		paramstring = "%d %d %f %f %f %f %f %f %f %f %f %d" % (sampleP1(i),sampleP2(i), sampleP3(i),pieceWeights[0],pieceWeights[1],pieceWeights[2],pieceWeights[3],pieceWeights[4],pieceWeights[5],pieceWeights[6], sampleP5(i),  sampleP6(i))
		os.system( "java -classpath dist/Tetrlais.jar TestExperiment.CalcScoreForParams %s" % (paramstring))
		inf = open('output.txt', 'r')
		score = inf.readline()
		inf.close()
		paramfile.write("%s %s \n" % (paramstring,score))
	paramfile.close()	

if __name__ == "__main__":
	main()