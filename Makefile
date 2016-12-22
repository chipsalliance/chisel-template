SBT=sbt

default compile:
	$(SBT) compile

clean:
	$(SBT) clean
	find . -depth -type d \( -name target -o -name test_run_dir \) -execdir echo rm -rf {}"/*" \;

.DEFAULT:
	$(SBT) $@
