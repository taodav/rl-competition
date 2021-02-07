#!/usr/bin/perl

#This is the Tetris makeJars.pl
mkdir("provingJars");
chdir("tmp-unjar");
 
for my $i (0..99){
	system "cp ../JarWithAllClasses.jar .";
	@output = `unzip JarWithAllClasses.jar`;
	system "rm -f JarWithAllClasses.jar";
 
	foreach my $line (@output) {
		if($line =~ /creating: (.*)/) {
			$line = $1;
		}
		if($line =~ /TPMDP(\d+)/ && $1 != $i){
			unlink("TPMDP$1/TPMDP$1.class");		
			rmdir "TPMDP$1";
		}
	}
 
	system "jar -cmf META-INF/MANIFEST.MF TPMDP$i.jar *";
	system "cp TPMDP$i.jar ../provingJars/";
	system "rm -rf *";
}
