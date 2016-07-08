Chisel Project Template
=======================

You've done the chisel [tutorials](https://github.com/ucb-bar/chisel-tutorial.git), and now you 
are ready to start your own chisel project.  The following procedure should get you started
with a clean running [Chisel3](https://github.com/ucb-bar/chisel3.git) project.

## Make your own Chisel3 project
### How to get started
The first thing you want to do is clone this repo into a directory of your own.  I'd recommend creating a chisel projects directory somewhere
```sh
mkdir ~/ChiselProjects
cd ~/ChiselProjects

git clone https://github.com/ucb-bar/chisel-template.git MyChiselProject
cd MyChiselProject
```
### Make your project into a fresh git repo
There may be more elegant way to do it, but the following works for me. **Note:** this project comes with a magnificent 339 line (at this writing) .gitignore file.
 You may want to edit that first in case we missed something, whack away at it, or start it from scratch.
```sh
rm -rf .git
git init
git add .gitignore *
git commit -m 'Starting MyChiselProject'
```
Connecting this up to github or some other remote host is an exercise left to the reader.
### Did it work?
You should now have a project based on Chisel3 that can be run.  **Note:** With a nod to cargo cult thinking, some believe 
it is best to execute the following sbt before opening up this directory in your IDE. I have no formal proof of this assertion.
So go for it, at the command line in the project root.
```sh
sbt test
```
You should see a whole bunch of output that ends with the following lines
```
STEP 49996 -> 50000
  EXPECT io_z -> 0x10 == 0x10 PASS
  EXPECT io_v -> 0x1 == 0x1 PASS
RAN 50000 CYCLES PASSED
[info] GCDTester:
[info] GCD
[info] - should calculate proper greatest common denominator
[info] ScalaCheck
[info] Passed: Total 0, Failed 0, Errors 0, Passed 0
[info] ScalaTest
[info] Run completed in 3 seconds, 294 milliseconds.
[info] Total number of tests run: 1
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 1, Failed 0, Errors 0, Passed 1
[success] Total time: 8 s, completed Jun 3, 2016 5:50:46 AM
```
If you see the above then...
### It worked!
You are ready to go. We have a few recommended practices and things to do.
* Use packages and following conventions for [structure](http://www.scala-sbt.org/0.13/docs/Directories.html) and [naming](http://docs.scala-lang.org/style/naming-conventions.html)
* Package names should be clearly reflected in the testing hierarchy
* Build tests for all your work.
 * This template includes a dependency on the Chisel3 IOTesters, this is a reasonable starting point for most tests
 * You can remove this dependency in the build.sbt file if necessary
* Change the name of your project in the build.sbt
* Change your README.md
