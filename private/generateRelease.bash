cd ..
svn up

cd private
ant clean
ant build-public

cd Proving
ant

cd ../..
svn commit

svn copy http://svn.2009.rl-competition.org/svn/rlcomp/trunk/public http://svn.2009.rl-competition.org/svn/rlcomp/branches/$1-rl-competition-2009 -m "Release $1"

svn export http://svn.2009.rl-competition.org/svn/rlcomp/branches/$1-rl-competition-2009 ../my_releases/$1-rl-competition-2009/

cd ../my_releases
tar -cvf $1-rl-competition-2009.tar $1-rl-competition-2009
gzip $1-rl-competition-2009.tar
