SBT=sbt

default compile:
	$(SBT) compile

clean:
	$(SBT) clean
	find . -depth -type d \( -name target -o -name test_run_dir \) -execdir echo rm -rf {}"/*" \;

test:
	$(SBT) test

publish-local:
	$(SBT) publish-local

.DEFAULT:
	$(SBT) $@

.PHONY: clean compile default test publish-local
