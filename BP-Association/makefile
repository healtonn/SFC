JCC = javac

default: target

target:
	$(JCC) -g src/xjochl00_backPropagation/*.java -d bin/

run:
	java -cp "bin/" xjochl00_backPropagation.Main

clean:
	$(RM) bin/xjochl00_backPropagation/*.class
	