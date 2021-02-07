#!/usr/bin/perl
#This is the MountainCar makeJars.pl

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
		if($line =~ /MCPMDP(\d+)/ && $1 != $i){
			unlink("MCPMDP$1/MCPMDP$1.class");		
			rmdir "MCPMDP$1";
		}
	}
 
	system "jar -cmf META-INF/MANIFEST.MF MCPMDP$i.jar *";
	system "cp MCPMDP$i.jar ../provingJars/";
	system "rm -rf *";
}