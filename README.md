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
 
#### Clear out the old git stuff 
```sh
rm -rf .git
git init
git add .gitignore *
```

#### Rename project in build.sbt file
Use your favorite text editor to change the first line of the **build.sbt** file
(it ships as ```name := "chisel-module-template"```) to correspond 
to your project.<br/>
Perhaps as ```name := "my-chisel-project"```

#### Clean up the README.md file
Again use you editor of choice to make the README specific to your project

#### Commit your changes
```
git commit -m 'Starting MyChiselProject'
```
Connecting this up to github or some other remote host is an exercise left to the reader.

### Did it work?
You should now have a project based on Chisel3 that can be run.<br/>
So go for it, at the command line in the project root.
```sh
sbt 'testOnly gcd.GCDTester -- -z Basic'
```
>This tells the test harness to only run the test in GCDTester that contains the word Basic
There are a number of other examples of ways to run tests in there, but we just want to see that
one works.

You should see a whole bunch of output that ends with something like the following lines
```
[info] [0.001] SEED 1506028591907
test GCD Success: 168 tests passed in 1107 cycles taking 0.203969 seconds
[info] [0.191] RAN 1102 CYCLES PASSED[info] GCDTester:
[info] GCD
[info] GCD
[info] Basic test using Driver.execute
[info] - should be an alternative way to run specification
[info] using --backend-name verilator
[info] running with --is-verbose creats a lot
[info] using --help
[info] ScalaTest
[info] Run completed in 1 second, 642 milliseconds.
[info] Total number of tests run: 1
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 1, Failed 0, Errors 0, Passed 1
[success] Total time: 2 s, completed Sep 21, 2017 9:12:47 PM
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
